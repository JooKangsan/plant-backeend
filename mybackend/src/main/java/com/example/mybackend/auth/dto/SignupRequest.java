package com.example.mybackend.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignupRequest {
    private String email;
    private String password;
    private String nickname;
    private String profileImage;
    private List<String> interests;
}
