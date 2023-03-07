package com.example.lms.member.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResetPasswordInput {
    private String userId;
    private String userName;
}
