package no.mesan.akka.client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public final class ImageToAsciiConverter {

    public static final String ASCII_GRADIENT = "@@@@@##888&&&ooo**::...   ";

    public static String convert(BufferedImage image) {
        return convert(image, 80, false);
    }

    public static String convert(BufferedImage image, int width, boolean invert) {
        double aspectRatio = (double) image.getHeight() / (double) image.getWidth();

        int scaledWidth = width;
        int scaledHeight = (int) (scaledWidth * aspectRatio * 0.5); // Compensate for characters being about twice as high

        image = scaleImage(image, scaledWidth, scaledHeight);

        StringBuilder sb = new StringBuilder((image.getWidth() + 1) * image.getHeight());
        for (int y = 0; y < image.getHeight(); y++) {
            if (y != 0) {
                sb.append("\n");
            }
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                double greyscaleValue = colorToLuminance(pixelColor);
                sb.append(grayscaleToCharacter(greyscaleValue, invert));
            }
        }
        return sb.toString();
    }

    private static double colorToLuminance(Color color) {
        return ((double) color.getRed() * 0.2216 + (double) color.getGreen() * 0.7152 + (double) color.getBlue() * 0.0722) / 255.0;
    }

    private static char grayscaleToCharacter(double greyscaleValue, boolean invert)
    {
        String asciiGradient = ASCII_GRADIENT;
        if (invert) {
            asciiGradient = new StringBuilder(asciiGradient).reverse().toString();
        }
        double valueScaledToGradient = greyscaleValue * (double)(asciiGradient.length() - 1);
        int gradientIndex = (int) Math.round(valueScaledToGradient);
        return asciiGradient.charAt(gradientIndex);
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
            {
                BufferedImage image = ImageIO.read(new File("C:\\Users\\perl\\Downloads\\gradient.jpg"));
                final String ascii = ImageToAsciiConverter.convert(image);
                System.out.println(ascii);
            }
            {
                BufferedImage image = ImageIO.read(new File("C:\\Users\\perl\\Downloads\\perl.png"));
                final String ascii = ImageToAsciiConverter.convert(image);
                System.out.println(ascii);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
