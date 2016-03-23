package org.selenium;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.MethodRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.ScreenshotException;

import com.thoughtworks.selenium.SeleniumException;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SeleniumTestRule implements MethodRule {
    /** system property which is compatible with phantomjs-maven-plugin */
    private static final String PHANTOMJS_BINARY = "phantomjs.binary";
    @Setter
    private WebDriver webDriver;
    private String screenshotDirectory = "target/failsafe-reports/";
    private Object testCase;
    private boolean createSubdirectoryForTestCase;
    private Annotation webDriverAnnotation;
    private Field webDriverField;

    public SeleniumTestRule() {
        this("target/failsafe-reports/", true);
    }

    public SeleniumTestRule(String screenshotDirectory, boolean createSubdirectoryForTestCase) {
        this(null, screenshotDirectory, createSubdirectoryForTestCase);
    }

    public SeleniumTestRule(WebDriver webDriver, String screenshotDirectory, boolean createSubdirectoryForTestCase) {
        this.webDriver = webDriver;
        this.screenshotDirectory = screenshotDirectory;
        this.createSubdirectoryForTestCase = createSubdirectoryForTestCase;
    }

    @Override
    public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
        testCase = target;
        final Description description = Description.createTestDescription(target.getClass(), method.getName());
        log.debug("apply");
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                starting(description);
                try {
                    base.evaluate();
                    succeeded(description);
                } catch (AssumptionViolatedException e) {
                    throw e;
                } catch (Throwable e) {
                    failed(e, description);
                    throw e;
                } finally {
                    finished(description);
                }
            }
        };
    }

    /**
     * Invoked when a test method is about to start
     * 
     * @throws IllegalAccessException
     */
    public void starting(Description description) throws IllegalAccessException {
        log.debug("starting {}", description.getDisplayName());
        getWebDriver(testCase);
        if (this.webDriverAnnotation instanceof PhantomJsDriver) {
            String phantomJsPath = ((PhantomJsDriver) this.webDriverAnnotation).phantomJsPath();
            if (StringUtils.isEmpty(phantomJsPath)) {
                // TODO do not use default
                phantomJsPath = System.getProperty(PHANTOMJS_BINARY);
            }
            if (StringUtils.isEmpty(phantomJsPath)) {
                log.error("PhantomJsDriver requires either path to PhantomJs binary or system property " + PHANTOMJS_BINARY);
                throw new IllegalArgumentException("PhantomJsDriver requires either path to PhantomJs binary or system property " + PHANTOMJS_BINARY);
            }
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomJsPath);
            this.webDriver = new PhantomJSDriver(capabilities);
            this.webDriver.manage().window().setSize(new Dimension(800, 600));
            setWebDriver(testCase);
        }
    }

    /**
     * Invoked when a test method succeeds
     */
    public void succeeded(Description description) {
        log.debug("{} succeeded", description.getDisplayName());
    }

    /**
     * Invoked when a test method fails
     * 
     * @throws IOException
     */
    public void failed(Throwable throwable, Description description) throws IOException {
        log.debug("{} failed", description.getDisplayName());
        getWebDriver(testCase);
        if (this.webDriver == null) {
            throw new IllegalArgumentException(createErrorText());
        }
        try {
            writeBrowserSource(description);
            writeScreenshot(description);
            // } catch (IOException e) {
        } catch (NoSuchWindowException e) {
            throw new NoSuchWindowException(
                    "Unable to create screenshot, WebDriver was closed? Do not close or quit WebDriver in @After method.",
                    e);
        } catch (SeleniumException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // throw e;
            e.printStackTrace();
        }
    }

    /**
     * Invoked when a test method finishes (whether passing or failing)
     */
    public void finished(Description description) {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    private void writeBrowserSource(Description description) throws FileNotFoundException, IOException {
        byte[] source = webDriver.getPageSource().getBytes();
        File outputFile = getOutputFile(description.getClassName(), description.getMethodName(), ".html");
        FileUtils.writeByteArrayToFile(outputFile, source);
        System.out.println("[[ATTACHMENT|" + outputFile.getPath() + "]]");
    }

    private void writeScreenshot(Description description) throws FileNotFoundException, IOException {
        byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
        File outputFile = getOutputFile(description.getClassName(), description.getMethodName(), ".png");
        FileUtils.writeByteArrayToFile(outputFile, screenshot);
        System.out.println("[[ATTACHMENT|" + outputFile.getPath() + "]]");
    }

    private File getOutputFile(String className, String methodName, String fileExtension) throws IOException {
        File outputDir = new File(screenshotDirectory + "/" + (createSubdirectoryForTestCase ? className : ""));
        outputDir.mkdirs();
        File outputFile = new File(outputDir, className + "-" + methodName + fileExtension);
        return outputFile;
    }

    public WebDriver getWebDriver(Object testCase) {
        if (this.webDriver == null) {
            getWebDriverFromAnnotation(testCase);
        }
        return this.webDriver;
    }

    protected void getWebDriverFromAnnotation(Object testCase) {
        Class[] annotations = { SeleniumWebDriver.class, PhantomJsDriver.class };
        for (Class annotation : annotations) {
            for (Field field : FieldUtils.getFieldsWithAnnotation(testCase.getClass(), annotation)) {
                this.webDriver = (WebDriver) getFieldValue(testCase, field);
                this.webDriverField = field;
                this.webDriverAnnotation = field.getAnnotation(annotation);
            }
        }
        if (this.webDriverAnnotation == null) {
            throw new IllegalArgumentException(createErrorText());
        }
    }

    private Object getFieldValue(Object testCase, Field field) {
        try {
            Object object = field.get(testCase);
            if (object != null) {
                if (object instanceof WebDriver) {
                    return object;
                } else {
                    throw new IllegalArgumentException(createErrorText());
                }
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(createErrorText(), e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(createErrorText(), e);
        }
        return null;
    }

    private void setWebDriver(Object testCase) throws IllegalAccessException {
        FieldUtils.writeField(webDriverField, testCase, webDriver);
    }

    private String createErrorText() {
        String annotationName = org.selenium.SeleniumWebDriver.class.getSimpleName();
        return String.format("Annotate public attribute of type WebDriver with @%s annotation.", annotationName);
    }
}
