package com.greengrow.plantdiary.controller;

import com.greengrow.plantdiary.model.User;
import com.greengrow.plantdiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserProfileController {

    private final UserService userService;

    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{username}/profile")
    public String showUserProfile(@PathVariable("username") String username, Model model) {
        // 사용자 정보와 글을 데이터베이스에서 로드
        User user = userService.findByUsername(username);
        if (user == null) {
            return "redirect:/error"; // 사용자가 없을 경우 에러 페이지로 리다이렉트
        }

        model.addAttribute("username", user.getUsername());
        // 예시로 user.getPosts()는 사용자가 작성한 글 목록을 가져오는 메소드라고 가정
        // 실제로 이러한 메소드를 구현해야 함
        model.addAttribute("posts", user.getPosts());

        return "user-profile"; // user-profile.html 뷰로 이동
    }
}
