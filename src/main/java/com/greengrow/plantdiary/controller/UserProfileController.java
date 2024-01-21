package com.greengrow.plantdiary.controller;

import com.greengrow.plantdiary.model.Comment;
import com.greengrow.plantdiary.model.Post;
import com.greengrow.plantdiary.model.User;
import com.greengrow.plantdiary.service.CommentService;
import com.greengrow.plantdiary.service.PostService; // PostService를 추가해야 함
import com.greengrow.plantdiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserProfileController {

    private final UserService userService;
    private final PostService postService; // PostService를 주입 받음

    @Autowired
    private CommentService commentService;


    @Autowired
    public UserProfileController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService; // PostService를 초기화
    }

    @GetMapping("/user/{username}/profile")
    public String showUserProfile(@PathVariable("username") String username, Model model) {
        // 사용자 정보와 글을 데이터베이스에서 로드
        User user = userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("username", username); // PathVariable에서 받은 username을 모델에 추가
            model.addAttribute("posts", postService.findPostsByUser(user)); // 사용자가 작성한 글 목록을 가져와 모델에 추가
        } else {
            // 사용자가 데이터베이스에 없을 경우 에러 처리
            return "redirect:/error"; // 에러 페이지로 리다이렉트
        }

        return "user-profile"; // user-profile.html 뷰로 이동
    }
    @GetMapping("/user/post/{postId}")
    public String showPostDetail(@PathVariable("postId") Long postId, Model model) {
        Post post = postService.findPostById(postId);
        if (post != null) {
            model.addAttribute("post", post);
            List<Comment> comments = commentService.getCommentsByPostId(postId);
            model.addAttribute("comments", comments);
        } else {
            return "redirect:/error";
        }
        return "post-detail";
    }
}
