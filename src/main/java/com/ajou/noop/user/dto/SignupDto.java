package com.ajou.noop.user.dto;

import com.ajou.noop.domain.User;
import com.ajou.noop.domain.UserStatus;
import lombok.Data;

@Data
public class SignupDto {
    private String email;

    private String password;

    private String username;

    private String description;

    public User toEntity(){
        return User.builder()
                .email(this.email)
                .password(this.password)
                .username(this.username)
                .description(this.description)
                .status(UserStatus.ACTIVE)
                .build();
    }

}
