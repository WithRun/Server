package com.example.WithRun.auth.dto;

import com.example.WithRun.user.domain.Gender;
import com.sun.istack.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {

    @NotNull
    private String userId;
    @NotNull
    private String password;
    @NotNull
    private String username;
    private Gender gender;
    private String email;
}
