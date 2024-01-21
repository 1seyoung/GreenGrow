package com.greengrow.plantdiary.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${upload.path}") // application.properties에 설정한 디렉토리 경로를 가져옵니다.
    private String uploadPath;

    public String saveImage(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                // 이미지 파일의 고유한 파일 이름 생성 (UUID 사용)
                String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                // 이미지를 저장할 경로 생성
                Path imagePath = Paths.get(uploadPath, uniqueFileName);
                // 이미지 파일을 저장
                image.transferTo(imagePath.toFile());

                // 이미지 URL 반환
                return "/uploads/" + uniqueFileName;
            } catch (IOException e) {
                e.printStackTrace();
                // 이미지 저장 실패 시 예외 처리
                return null;
            }
        }
        return null;
    }
}
