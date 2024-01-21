package com.greengrow.plantdiary.controller;

import com.greengrow.plantdiary.model.User;
import com.greengrow.plantdiary.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import com.greengrow.plantdiary.model.Post;
import com.greengrow.plantdiary.repository.PostRepository;
import com.greengrow.plantdiary.service.ImageService;
import com.greengrow.plantdiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;


@Controller
public class PostController {

    private final ImageService imageService;

    private final UserService userService;

    @Autowired // This annotation is optional if you have a single constructor
    public PostController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService; // Initialize userService through constructor
    }
    // 글 작성 페이지를 보여주는 메소드
    @GetMapping("/write-post")
    public String showPostForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        // 쿠키에서 username을 가져와서 모델에 추가
        Cookie[] cookies = request.getCookies();
        String username = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                    break;
                }
            }
        }

        // 쿠키에서 username을 가져올 수 없으면 세션에서 확인
        if (username == null || username.isEmpty()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                username = authentication.getName();
            }
        }

        // username을 모델에 추가
        model.addAttribute("username", username);

        return "write-post";
    }


    @Autowired
    private PostRepository postRepository;

    @PostMapping("/submit-post")
    public String handlePostUpload(
            @RequestParam("title") String title,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("content") String content,
            @RequestParam("image") MultipartFile image,
            HttpServletResponse response,
            HttpServletRequest request) {

        String username = null;
        // 쿠키에서 username 가져오기
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                    break;
                }
            }
        }

        // username 쿠키가 없으면 에러 페이지로 리다이렉트
        if (username == null || username.isEmpty()) {
            return "redirect:/user/login"; // 로그인 페이지 또는 에러 페이지로 리다이렉트
        }

        // 이미지 처리 로직
        String imageUrl = imageService.saveImage(image); // 이미지를 저장하고 URL을 반환하는 메소드


        // 글 정보를 데이터베이스에 저장
        Post post = new Post();
        post.setTitle(title);
        post.setDate(date);
        post.setContent(content);
        post.setImageUrl(imageUrl); // 이미지 URL 설정


        // 여기에서 UserService를 통해 User 객체를 찾고 Post 객체에 연결합니다.
        User user = userService.findByUsername(username);
        if (user == null) {
            // 사용자가 존재하지 않으면 에러 처리
            return "redirect:/error"; // 에러 페이지로 리다이렉트
        }
        post.setUser(user); // Post 객체에 User 설정

        postRepository.save(post);

        // 사용자 프로필 페이지로 리다이렉트
        return "redirect:/user/" + username + "/profile";
    }



    @GetMapping("/user/{username}/posts")
    public String getUserPosts(@PathVariable String username, Model model) {
        List<Post> userPosts = postRepository.findByUser_Username(username);
        model.addAttribute("userPosts", userPosts);
        return "user-posts"; // 렌더링할 템플릿 이름
    }


    @GetMapping("/post/{postId}")
    public String getPostDetails(@PathVariable Long postId, Model model) {
        PostService postService = null;
        Post post = postService.findPostById(postId);
        model.addAttribute("post", post);
        return "post-details";
    }
}
