package com.example.mybackend.user.dto;

import com.example.mybackend.user.domain.User;
import lombok.Getter;

import java.util.List; // ✅ 필수 import

@Getter
public class UserResponse {
    private final String email;
    private final String nickname;
    private final String profileImage;
    private final List<String> interests; // ✅ final 추가해도 무방함

    public UserResponse(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.interests = user.getInterests();
    }
}
