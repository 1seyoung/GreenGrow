package com.greengrow.plantdiary.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter // 모든 필드에 대한 Getter 메소드를 자동 생성합니다.
@Setter // 모든 필드에 대한 Setter 메소드를 자동 생성합니다.
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String dob; // Date of Birth 형식에 맞게 변경 필요 (예: LocalDate 타입)
    private String password;

    // 사용자(User)와 게시글(Post) 사이의 일대다 관계 설정
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    // 기본 생성자
    public User() {
    }

    // 게터와 세터는 Lombok으로 자동 생성됩니다.
    // getPosts 메소드는 Lombok에 의해 이미 생성되어 있으므로, 아래 메소드는 제거 가능
}
