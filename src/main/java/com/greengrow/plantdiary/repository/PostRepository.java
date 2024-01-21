package com.greengrow.plantdiary.repository;

import com.greengrow.plantdiary.model.Post;
import com.greengrow.plantdiary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 사용자를 기반으로 글을 조회하는 메소드
    List<Post> findByUser(User user);

    List<Post> findByUser_Username(String username);
}
