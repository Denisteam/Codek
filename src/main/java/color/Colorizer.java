package color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Colorizer {
    BufferedImage image;
    Path path;

    public Colorizer(Path path) throws IOException {
        image = ImageIO.read(path.toFile());
        this.path = path;
    }

    public static void main(String[] args) {
        Path path = Paths.get("/home/denis/Downloads", "im.jpg");

        try {
           Colorizer colorizer = new Colorizer(Paths.get("/home/denis/Downloads", "im.jpg"));
            colorizer.colorize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void colorize() {
        Path newImage = path.getParent().resolve("newImg");

        try {
            Files.createFile(newImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                image.setRGB(i, j, image.getRGB(i, j) & 0xFF);
            }
        }

        try {
            ImageIO.write(image, "jpg", Files.newOutputStream(newImage, StandardOpenOption.WRITE));
        } catch (IOException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}
