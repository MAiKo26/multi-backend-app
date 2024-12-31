package tn.maiko26.springboot.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUploadUtil {

    private static final String UPLOAD_DIR = "C:/Users/MSI/Workstation/multi-backend-app/backend/uploads";

    public static String saveFile(MultipartFile file) throws IOException {
        // Ensure upload directory exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Generate unique file name
        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "-" + originalFilename.replaceAll("\\s", "_").replaceAll("\\.[^/.]+$", "") + ".webp";

        // Save the file
        Path filePath = Paths.get(UPLOAD_DIR, uniqueFilename);
        Files.write(filePath, file.getBytes());

        return uniqueFilename;
    }
}
