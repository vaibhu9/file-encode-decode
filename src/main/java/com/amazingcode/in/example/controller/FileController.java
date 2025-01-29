package com.amazingcode.in.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/encode-file")
    public ResponseEntity<String> encodeFileToBase64(@RequestParam("file") MultipartFile file) {
        try {
            logger.info("Received file for encoding: {}", file.getOriginalFilename());
            byte[] fileBytes = file.getBytes();
            String base64Encoded = Base64.getEncoder().encodeToString(fileBytes);
            logger.info("File successfully encoded to Base64");
            return ResponseEntity.ok(base64Encoded);
        } catch (Exception e) {
            logger.error("Error encoding file: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error encoding file: " + e.getMessage());
        }
    }

    @PostMapping("/decode-file")
    public ResponseEntity<byte[]> decodeFileFromBase64(@RequestParam("base64") String base64) {
        try {
            logger.info("Received Base64 string for decoding");
            byte[] decodedBytes = Base64.getDecoder().decode(base64);
            String contentType = detectImageType(decodedBytes);
            logger.info("Detected content type: {}", contentType);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));

            logger.info("File successfully decoded and ready for response");
            return ResponseEntity.ok().headers(headers).body(decodedBytes);
        } catch (Exception e) {
            logger.error("Error decoding file: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    private String detectImageType(byte[] bytes) {
        logger.info("Detecting image type");
        if (bytes.length >= 2) {
            if (bytes[0] == (byte) 0xFF && bytes[1] == (byte) 0xD8) {
                logger.info("Image type detected: JPEG");
                return "image/jpeg";
            } else if (bytes[0] == (byte) 0x89 && bytes[1] == (byte) 0x50) {
                logger.info("Image type detected: PNG");
                return "image/png";
            } else if (bytes[0] == (byte) 0x47 && bytes[1] == (byte) 0x49) {
                logger.info("Image type detected: GIF");
                return "image/gif";
            }
        }
        logger.warn("Unknown image type, defaulting to application/octet-stream");
        return "application/octet-stream";
    }
}
