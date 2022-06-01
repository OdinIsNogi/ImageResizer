import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Resizer implements Runnable {

    private final File[] files;
    private final int newWidth;
    private final String destFolder;
    private final long start;
    private final int threadNumber;

    public Resizer(File[] files, int newWidth, String destFolder, long start, int threadNumber) {
        this.files = files;
        this.newWidth = newWidth;
        this.destFolder = destFolder;
        this.start = start;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        try {
            for (File f : files) {
                BufferedImage image = ImageIO.read(f);
                if (image == null) {
                    continue;
                }
                int newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth));
                BufferedImage newImage = resize(image, newWidth, newHeight);
                File newFile = new File(destFolder + "/" + f.getName());
                ImageIO.write(newImage, "png", newFile);
                System.out.println("Thread number - " + threadNumber + " - " + f.getName());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Thread number: " + threadNumber + " end work in: " + (System.currentTimeMillis() - start) + " ms");
    }

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        return Scalr.resize(
                image,
                Scalr.Method.ULTRA_QUALITY,
                Scalr.Mode.FIT_EXACT,
                width,
                height,
                Scalr.OP_ANTIALIAS
        );
    }
}
