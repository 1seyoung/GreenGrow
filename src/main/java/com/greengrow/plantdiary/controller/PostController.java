package com.greengrow.plantdiary.controller;

import com.greengrow.plantdiary.model.Post;
import com.greengrow.plantdiary.repository.PostRepository;
import com.greengrow.plantdiary.service.ImageService;
import com.greengrow.plantdiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
public class PostController {

    private final ImageService imageService;

    public PostController(ImageService imageService) {
        this.imageService = imageService;
    }

    // 글 작성 페이지를 보여주는 메소드
    @GetMapping("/write-post")
    public String showPostForm() {
        return "write-post"; // write-post.html 뷰를 반환합니다.
    }


    @Autowired
    private PostRepository postRepository;

    @PostMapping("/submit-post")
    public String handlePostUpload(@RequestParam("title") String title,
                                   @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                   @RequestParam("content") String content,
                                   @RequestParam("image") MultipartFile image) {
        // 이미지 처리 로직
        String imageUrl = imageService.saveImage(image); // 이미지를 저장하고 URL을 반환하는 메소드

        // 글 내용에 이미지 URL 삽입
        String updatedContent = content + "\n![Image](" + imageUrl + ")";

        // 글 정보를 데이터베이스에 저장
        Post post = new Post();
        post.setTitle(title);
        post.setDate(date);
        post.setContent(updatedContent);
        post.setImageUrl(imageUrl); // 이미지 URL 설정

        postRepository.save(post);

        return "redirect:/user-profile";
    }


}
