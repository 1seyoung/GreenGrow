package com.greengrow.plantdiary.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private String imageUrl;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    // Post와 User 엔티티 사이의 관계를 정의합니다.
    // User 엔티티에도 이와 상응하는 관계 설정이 있어야 합니다 (예: @OneToMany).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 기본 생성자
    public Post() {
    }

    // 모든 필드를 파라미터로 받는 생성자 (imageUrl 필드명을 imagePath에서 imageUrl로 수정함)
    public Post(String title, String content, LocalDate date, String imageUrl, User user) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.imageUrl = imageUrl;
        this.user = user; // User 엔티티 인스턴스를 직접 할당
    }

    // 게터와 세터는 Lombok으로 자동 생성됩니다.
    // 만약 Lombok을 사용하지 않는다면, 각 필드에 대한 게터와 세터를 수동으로 추가해야 합니다.

}
