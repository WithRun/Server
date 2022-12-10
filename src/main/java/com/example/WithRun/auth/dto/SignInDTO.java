package com.example.WithRun.auth.dto;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInDTO {

    private long id;
    private String userId;
    private String password;
    private String token;
    private String username;
    private Boolean loginChecked;
}
