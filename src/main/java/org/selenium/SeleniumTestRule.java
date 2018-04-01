package org.selenium;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.MethodRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeleniumTestRule implements MethodRule {
    public static final String DEFAULT_SCREENSHOT_DIRECTORY = "target/failsafe-reports/";
    private WebDriver webDriver;
    private String screenshotDirectory;
    private Object testCase;
    private boolean createSubdirectoryForTestCase;
    protected Annotation webDriverAnnotation;

    public SeleniumTestRule() {
        this(DEFAULT_SCREENSHOT_DIRECTORY, false);
    }

    public SeleniumTestRule(Object testCase) {
        this.testCase = testCase;
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
                    finished();
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
        this.webDriver = WebDriverAnnotations.initializeWebDriver(testCase);
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
    // TODO improve error handling
    public void failed(Throwable throwable, Description description) {
        log.debug("{} failed with error {}", description.getDisplayName(), throwable.getMessage());
        // if webDriver was created by the test case, the test rule does not have a
        // handle to it
        if (this.webDriver == null) {
            this.webDriver = AnnotationHelper.getWebDriver(testCase);
        }
        if (this.webDriver == null) {
            throw new IllegalArgumentException(AnnotationHelper.createErrorText());
        }
        try {
            ErrorWriter errorWriter = new ErrorWriter(webDriver, screenshotDirectory, createSubdirectoryForTestCase);
            errorWriter.writeBrowserSource(description);
            errorWriter.writeScreenshot(description);
        } catch (NoSuchWindowException e) {
            throw new NoSuchWindowException(
                    "Unable to create screenshot, WebDriver was closed? Do not close or quit WebDriver in @After method.",
                    e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Invoked when a test method finishes (whether passing or failing)
     */
    public void finished() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    protected WebDriver setWebDriverToTest(Object testCase) {
        if (this.webDriver == null) {
            try {
                webDriverAnnotation = AnnotationHelper.getWebDriverAnnotation(testCase);
                Field webDriverField = AnnotationHelper.getFieldWithAnnotation(testCase, webDriverAnnotation);
                webDriver = AnnotationHelper.getWebDriver(testCase, webDriverField);
                AnnotationHelper.setWebDriverToField(testCase, webDriverField, webDriver);
            } catch (Exception e) {
                if (webDriver != null) {
                    webDriver.quit();
                }
                throw e;
            }
        }
        return this.webDriver;
    }
}
