package com.example.lms.member.controller;

import com.example.lms.member.entity.Member;
import com.example.lms.member.model.MemberInput;
import com.example.lms.member.repository.MemberRepository;
import com.example.lms.member.service.MemberService;
import lombok.*;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Data
@Controller
public class MemberController {
    private final MemberService memberService;
//        public MemberController(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//   }
    @RequestMapping("/member/login")
    public String login() {
        return "member/login";
    }
    @GetMapping("/member/find/password")
    public String findPassword() {
        return "member/find_password";
    }

    @GetMapping("/member/register")
    //public String register(HttpServletRequest request, HttpServletResponse response, MemberInput memberInput) {
    public String register() {
    return "member/register";
    }

    @PostMapping("/member/register")
    public String registerSubmit(Model model, HttpServletRequest request, MemberInput memberInput) {
//        Member member = new Member();
//
//        member.setUserId(memberInput.getUserId());
//        member.setUserName(memberInput.getUserName());
//        member.setPassword(memberInput.getPassword());
//        member.setPhone(memberInput.getPhone());
//        member.setRegDt(LocalDateTime.now());

//        memberRepository.save(member);  서비스로 이동

       boolean result =  memberService.register(memberInput);
       model.addAttribute("result", result);

        return "member/register_complete";
    }

    @GetMapping("/member/email-auth")
    public String emailAuth(Model model, HttpServletRequest request) {

        String uuid = request.getParameter("id");
//        System.out.println(uuid);

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "member/email-auth";
    }
}
