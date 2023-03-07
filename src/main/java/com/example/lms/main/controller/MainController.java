package com.example.lms.main.controller;

import com.example.lms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final MailComponents mailComponents;
    @RequestMapping("/")
    public String index() {
//        mailComponents.sendMailTest();

        String email = "chenj7498@gmail.com";
        String subject = " 안녕하세요 제로베이스 입니다. ";
        String text = "<p>안녕하세요.</p><p>반갑습니다.</p>";
        mailComponents.sendMail(email, subject, text);

        return "index";
    }
}
