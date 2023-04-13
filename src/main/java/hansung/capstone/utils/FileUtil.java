package hansung.capstone.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class FileUtil {

    public static void copy(InputStream inputStream, Path path, StandardCopyOption replaceExisting) {
        try {
            Files.copy(inputStream, path, replaceExisting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
