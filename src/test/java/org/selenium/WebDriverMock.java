package org.selenium;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class WebDriverMock extends HtmlUnitDriver implements TakesScreenshot {

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        try {
            // create a simple png file
            BufferedImage image = createImage();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            return target.convertFromPngBytes(outputStream.toByteArray());
        } catch (IOException e) {
            throw new WebDriverException(e);
        }
    }

    private BufferedImage createImage() throws IOException {
        BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
        return image;
    }
}
