package com.example.lms.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Member {
    @Id
    private String userId;

    private String userName;
    private String phone;
    private String password;

    private LocalDateTime regDt; //화면에는 없지만 내부적으로 쓰니까 만들어준다

    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;
}
