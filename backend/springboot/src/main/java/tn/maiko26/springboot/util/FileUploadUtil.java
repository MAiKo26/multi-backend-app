package tn.maiko26.springboot.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadUtil {

    private static final String UPLOAD_DIR = "C:/Users/MSI/Workstation/multi-backend-app/backend/uploads";

    public static String saveFile(MultipartFile file) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = System.currentTimeMillis() + "-" + originalFilename.replaceAll("\\s", "_").replaceAll("\\.[^/.]+$", "") + ".webp";

        Path filePath = Paths.get(UPLOAD_DIR, uniqueFilename);
        Files.write(filePath, file.getBytes());

        return filePath.toString();
    }
}
