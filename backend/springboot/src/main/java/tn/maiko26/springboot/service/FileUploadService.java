package tn.maiko26.springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.maiko26.springboot.exception.CustomException;
import tn.maiko26.springboot.util.FileUploadUtil;

@Service
public class FileUploadService {

    public String uploadFile(MultipartFile file) {
        try {
            return FileUploadUtil.saveFile(file);
        } catch (Exception e) {
            throw new CustomException("File upload failed: " + e.getMessage(), 400);
        }
    }
}

