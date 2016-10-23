package org.selenium;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ErrorWriter {
    private String screenshotDirectory;
    private boolean createSubdirectoryForTestCase;
    private WebDriver webDriver;
    public ErrorWriter(WebDriver webDriver, String screenshotDirectory, boolean createSubdirectoryForTestCase) {
        this.webDriver = webDriver;
        this.screenshotDirectory = screenshotDirectory;
        this.createSubdirectoryForTestCase = createSubdirectoryForTestCase;
    }

    public void writeBrowserSource(Description description) throws IOException {
        byte[] source = webDriver.getPageSource().getBytes();
        File outputFile = getOutputFile(description.getClassName(), description.getMethodName(), ".html");
        FileUtils.writeByteArrayToFile(outputFile, source);
        System.out.println("[[ATTACHMENT|" + outputFile.getPath() + "]]");
    }

    public void writeScreenshot(Description description) throws IOException {
        byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
        File outputFile = getOutputFile(description.getClassName(), description.getMethodName(), ".png");
        FileUtils.writeByteArrayToFile(outputFile, screenshot);
        System.out.println("[[ATTACHMENT|" + outputFile.getPath() + "]]");
    }

    private File getOutputFile(String className, String methodName, String fileExtension) throws IOException {
        File outputDir = new File(screenshotDirectory + "/" + (createSubdirectoryForTestCase ? className : ""));
        outputDir.mkdirs();
        return new File(outputDir, className + "-" + methodName + fileExtension);
    }

}
