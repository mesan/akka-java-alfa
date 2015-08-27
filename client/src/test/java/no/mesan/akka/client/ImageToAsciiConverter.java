package no.mesan.akka.client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public final class ImageToAsciiConverter {

    public static String convert(BufferedImage image) {
        double aspectratio = (double) image.getHeight() / (double) image.getWidth();
        int scaledWidth = 80;
        int scaledHeight = (int) (scaledWidth * aspectratio * 0.5);

        image = scaleImage(image, scaledWidth, scaledHeight);

        StringBuilder sb = new StringBuilder((image.getWidth() + 1) * image.getHeight());
        for (int y = 0; y < image.getHeight(); y++) {
            if (y != 0) {
                sb.append("\n");
            }
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                double greyscaleValue = (double) pixelColor.getRed() * 0.2216 + (double) pixelColor.getGreen() * 0.7152 + (double) pixelColor.getBlue() * 0.0722;
                final char s = returnStrPos(greyscaleValue);
                sb.append(s);
            }
        }
        return sb.toString();
    }

    private static char returnStrPos(double greyscaleValue)
    {
        final char character;

        if (greyscaleValue >= 230.0) {
            character = ' ';
        } else if (greyscaleValue >= 200.0) {
            character = '.';
        } else if (greyscaleValue >= 180.0) {
            character = '*';
        } else if (greyscaleValue >= 160.0) {
            character = ':';
        } else if (greyscaleValue >= 130.0) {
            character = 'o';
        } else if (greyscaleValue >= 100.0) {
            character = '&';
        } else if (greyscaleValue >= 70.0) {
            character = '8';
        } else if (greyscaleValue >= 50.0) {
            character = '#';
        } else {
            character = '@';
        }
        return character;
    }

    private static BufferedImage scaleImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("C:\\Users\\perl\\Downloads\\perl.png"));
            final String ascii = ImageToAsciiConverter.convert(image);
            System.out.println(ascii);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
