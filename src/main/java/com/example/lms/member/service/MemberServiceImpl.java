package com.example.lms.member.service;

import com.example.lms.components.MailComponents;
import com.example.lms.exception.MemberNotEmailAuthException;
import com.example.lms.member.entity.Member;
import com.example.lms.member.model.MemberInput;
import com.example.lms.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
private final MemberRepository memberRepository;
private final MailComponents mailComponents;
    @Override
    public boolean register(MemberInput memberInput) {

        memberRepository.findById(memberInput.getUserId());

        Optional<Member> optionalMember = memberRepository.findById(memberInput.getUserId());
        if(optionalMember.isPresent()) {
            //현재 userId에 해당하는 데이터 존재
            return false;
        }

        String encPassword = BCrypt.hashpw(memberInput.getPassword(), BCrypt.gensalt());
        String uuid= UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(memberInput.getUserId())
                .userName(memberInput.getUserName())
                .phone(memberInput.getPhone())
                .password(encPassword)
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .build();
        memberRepository.save(member);
//아래의 내용을 빌더 패턴으로 바꿈
//        Member member = new Member();
//        member.setUserId(memberInput.getUserId());
//        member.setUserName(memberInput.getUserName());
//        member.setPassword(memberInput.getPassword());
//        member.setPhone(memberInput.getPhone());
//        member.setRegDt(LocalDateTime.now());
//
//        member.setEmailAuthYn(false); //최초에는 인증 안했을거니까
//        member.setEmailAuthKey(UUID.randomUUID().toString());
//        memberRepository.save(member);

        String email = memberInput.getUserId();
        String subject = "lms 사이트 가입을 축하드립니다.";
        String text = "<p>lms 사이트에 가입을 축하드립니다.</p>"
                + "<p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
                + "<div><a target='_blank' href='http://localhost:8080/member/email-auth?id=" + uuid + "'>가입 완료</a></div>";
        mailComponents.sendMail(email, subject, text);

        return true; // 여기 return 값이 true로 나와야 ${result} 값이 true, 회원가입 성공
    }

    @Override
    public boolean emailAuth(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if (!optionalMember.isPresent()) {
            return false;
        }
        Member member = optionalMember.get();
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //username = email

        Optional<Member> optionalMember = memberRepository.findById(username); //memberRepository에서 username 찾아주고 Optional<Member>타입으로 반환
        if(!optionalMember.isPresent())
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        Member member = optionalMember.get();

        if (!member.isEmailAuthYn())
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인 해주세요.");

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(member.getUserId(), member.getUserName(), grantedAuthorities); //시큐리티한테 이거 세개만 일단 알려줘
    }
}

