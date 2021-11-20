package indi.rui.study.unittest.task;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-07-01
 */
public class CopyPicture {

    public static final String TARGET_PATH = "C:\\Users\\yaowr\\Pictures\\Saved Pictures";
    public static final String IMAGE_SUFFIX = ".jpg";

    public static void main(String[] args) throws IOException {
        List<String> sourcePaths = Arrays.asList(
                "C:\\Users\\yaowr\\AppData\\Local\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets");
        for (String sourcePath : sourcePaths) {
            File dir = new File(sourcePath);
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                System.out.println("find " + files.length + " files.");
                for (int i = 0; i < files.length; i++) {
                    String filename = files[i].getName();
                    if (!filename.endsWith(IMAGE_SUFFIX)) {
                        filename = filename + IMAGE_SUFFIX;
                    }
                    File dest = new File(TARGET_PATH, filename);
                    FileUtils.copyFile(files[i], dest);
                }
            }
        }

    }
}
