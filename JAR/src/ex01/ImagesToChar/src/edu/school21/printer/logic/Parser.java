package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Parser {
    private static final String standardPath = "src/resources/images/it.bmp";

    public static byte[][] parseFile() {
        File file = new File(standardPath);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            byte[][] imageInByte = new byte[bufferedImage.getHeight()][bufferedImage.getWidth()];
            for (int i = 0; i < imageInByte.length; ++i) {
                for (int j = 0; j < imageInByte[i].length; ++j) {
                    imageInByte[i][j] = (byte) bufferedImage.getRGB(j, i);
                }
            }
            return imageInByte;
        } catch (IOException e) {
            e.getMessage();
        }
        return null;
    }
}
