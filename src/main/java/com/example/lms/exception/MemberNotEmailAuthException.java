package com.example.lms.exception;

public class MemberNotEmailAuthException extends RuntimeException {
    public MemberNotEmailAuthException(String error){
        super(error);
    }
}
