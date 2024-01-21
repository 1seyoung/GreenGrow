package com.greengrow.plantdiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // 홈 페이지의 뷰 이름을 반환합니다. 예: "home" 또는 "index"
        return "home";
    }
}
