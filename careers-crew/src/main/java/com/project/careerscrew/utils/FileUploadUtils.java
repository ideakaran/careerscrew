package com.project.careerscrew.utils;

import com.project.careerscrew.utils.exceptions.AppExceptionConstants;
import com.project.careerscrew.utils.exceptions.CustomAppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileUploadUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);
    public static String candidateResumeDir = "docs/candidateResume/";

    public static Path getFileUploadDirectory() {
        Path path = Paths.get(candidateResumeDir);
        if (!Files.exists(path)) {
            new File(candidateResumeDir).mkdirs();
        }
        return path;
    }

    public static boolean uploadFile(MultipartFile multipartFile, String fileName) {
        Map<String, Object> returnValue = new HashMap<>();
        if (multipartFile.isEmpty()) {
//            return new ResponseEntity<>("Select Resume", HttpStatus.BAD_REQUEST);
            throw new CustomAppException(AppExceptionConstants.CANNOT_UPLOAD_EMPTY_MULTIPART_FILE);
        }
        try {
            final byte[] bytes = multipartFile.getBytes();

            Path uploadDirectoryPath = getFileUploadDirectory();

            Path targetLocation = uploadDirectoryPath.resolve(fileName);
            Files.write(targetLocation, bytes);
//            returnValue.put("path", fileName);
//            returnValue.put("fileName", fileName);
//            return new Response().success(returnValue);
            return true;
        } catch (IOException e) {
            logger.error("Error wile writing file {}", e);
            throw new CustomAppException(AppExceptionConstants.INTERNAL_IO_EXCEPTION);
//            return new ResponseEntity<>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static String createCleanFileNameForMultipartFile(MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        fileName = System.currentTimeMillis() + "-" + StringUtils.cleanPath(fileName);
        return fileName;
    }

}
