package com.greengrow.plantdiary.controller;

import com.greengrow.plantdiary.model.User;
import com.greengrow.plantdiary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user") // 이 컨트롤러의 URL은 "/user"로 시작합니다.
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 페이지를 보여주는 메소드
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // templates 폴더 내의 register.html 템플릿을 렌더링합니다.
    }

    // 회원가입 폼 제출 시 호출되는 메소드
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("dob") String dob,
                               @RequestParam("password") String password) {
        // 유저 객체 생성
        User user = new User();
        user.setUsername(username);
        user.setDob(dob);
        user.setPassword(password);

        // 회원가입 처리
        userService.saveUser(user);

        // 회원가입 성공 시 로그인 페이지로 이동
        return "redirect:/user/login";
    }
}
