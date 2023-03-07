package com.example.lms.member.service;

import com.example.lms.member.entity.Member;
import com.example.lms.member.model.MemberInput;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MemberService extends UserDetailsService {

    boolean register(MemberInput memberInput);

    boolean emailAuth(String uuid);

}
