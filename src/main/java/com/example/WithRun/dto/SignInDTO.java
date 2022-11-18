package com.example.WithRun.dto;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInDTO {

    private String userId;
    private String password;
    private String token;
    private Boolean loginChecked;
}
