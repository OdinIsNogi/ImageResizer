import java.io.File;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        String srcFolder = "C:\\Users\\user\\Desktop\\Src";
        String dstFolder = "C:\\Users\\user\\Desktop\\Dst";
        int newWidth = 100;

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        int step = Objects.requireNonNull(files).length / Runtime.getRuntime().availableProcessors();
        if (files.length % step != 0) {
            step = Objects.requireNonNull(files).length / Runtime.getRuntime().availableProcessors() + files.length % step;
        }

        int var = 0;
        File[] temp = new File[step];
        int threadNumber = 1;

        for (File file : files) {
            temp[var++] = file;
            if (var == step) {
                new Thread(new Resizer(temp, newWidth, dstFolder, start, threadNumber)).start();
                step = Objects.requireNonNull(files).length / Runtime.getRuntime().availableProcessors();
                temp = new File[step];
                var = 0;
                threadNumber++;
            }
        }
    }
}
