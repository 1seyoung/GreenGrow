package com.greengrow.plantdiary.controller;

import com.greengrow.plantdiary.model.User;
import com.greengrow.plantdiary.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public String processLogin(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // 세션에 사용자 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // 쿠키에 username 저장
            Cookie usernameCookie = new Cookie("username", username);
            usernameCookie.setMaxAge(7 * 24 * 60 * 60); // 7일 유효 기간
            usernameCookie.setPath("/"); // 경로를 루트로 설정
            response.addCookie(usernameCookie);

            // 로그인 성공, 사용자 프로필 페이지로 리다이렉트
            return "redirect:/user/" + username + "/profile";
        } else {
            // 로그인 실패, 로그인 폼으로 다시 리다이렉트
            return "redirect:/user/login?error";
        }
    }


    @GetMapping("/logout")
    public String processLogout(HttpServletRequest request, HttpServletResponse response) {
        // 세션 파기 (로그아웃)
        HttpSession session = request.getSession();
        session.invalidate();

        // 쿠키 제거
        Cookie usernameCookie = new Cookie("username", "");
        usernameCookie.setMaxAge(0); // 즉시 만료
        response.addCookie(usernameCookie);

        // 로그아웃 후 홈페이지로 리다이렉트
        return "redirect:/user/login";
    }
}
