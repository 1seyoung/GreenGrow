package com.greengrow.plantdiary.service;

import com.greengrow.plantdiary.model.Post;
import com.greengrow.plantdiary.model.User;
import com.greengrow.plantdiary.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 사용자가 작성한 글 목록을 조회하는 메소드
    public List<Post> findPostsByUser(User user) {
        return postRepository.findByUser(user);
    }

    // 글 ID를 사용하여 글을 조회하는 메소드
    public Post findPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }
}
