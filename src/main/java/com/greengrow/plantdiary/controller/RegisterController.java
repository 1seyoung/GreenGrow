package com.greengrow.plantdiary.controller;

import com.greengrow.plantdiary.model.User;
import com.greengrow.plantdiary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 페이지를 보여주는 메소드
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    // 회원가입 폼 제출 시 호출되는 메소드
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("dob") String dob,
                               @RequestParam("password") String password,
                               RedirectAttributes redirectAttributes) {
        // 데이터베이스에서 username이 존재하는지 확인
        Optional<User> existingUser = Optional.ofNullable(userService.findByUsername(username));
        if (existingUser.isPresent()) {
            // username이 이미 존재하는 경우, 에러 메시지를 전달
            redirectAttributes.addFlashAttribute("registrationError", "Username already exists.");
            return "redirect:/user/register"; // 회원가입 폼으로 리다이렉트
        }

        // 새 유저 객체 생성
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setDob(dob);
        newUser.setPassword(password); // 실제 애플리케이션에서는 비밀번호를 해싱 처리하는 것이 중요

        // UserService를 통해 유저 정보 저장
        userService.saveUser(newUser);

        // 회원가입 성공 시 로그인 페이지로 이동
        return "redirect:/user/login";
    }
}
