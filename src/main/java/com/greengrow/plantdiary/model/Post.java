package com.greengrow.plantdiary.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDate date;

    // 이미지 파일의 경로를 저장하기 위한 필드 (옵션)
    // 파일 시스템에 저장된 이미지에 대한 참조로 사용됩니다.
    private String imageUrl;

    // 기본 생성자
    public Post() {
    }

    // 모든 필드를 파라미터로 받는 생성자 (옵션)
    public Post(String title, String content, LocalDate date, String imagePath) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.imageUrl = imagePath;
    }

    // 게터와 세터는 Lombok으로 자동 생성됩니다.
    // 만약 Lombok을 사용하지 않는다면, 각 필드에 대한 게터와 세터를 수동으로 추가해야 합니다.


}
