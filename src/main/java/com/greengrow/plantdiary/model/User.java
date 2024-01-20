package com.greengrow.plantdiary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter // 모든 필드에 대한 Getter 메소드를 자동 생성합니다.
@Setter // Setter 어노테이션은 이미 각 필드에 적용되어 있습니다.
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String dob; // Date of Birth
    private String password;

    public Object getPosts() {
        return null;
    }
}
