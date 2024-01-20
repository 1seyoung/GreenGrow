package com.greengrow.plantdiary.controller;

import com.greengrow.plantdiary.model.User;
import com.greengrow.plantdiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String username,
                               @RequestParam("password") String password) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // 로그인 성공, 사용자 프로필 페이지로 리다이렉트
            return "redirect:/user/" + username + "/profile"; // URL에 실제 username 값을 포함
        } else {
            // 로그인 실패, 로그인 폼으로 다시 리다이렉트
            return "redirect:/user/login?error";
        }
    }

}
