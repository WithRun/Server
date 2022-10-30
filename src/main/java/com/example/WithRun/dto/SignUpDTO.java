package com.example.WithRun.dto;

import com.example.WithRun.domain.Gender;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NotNull
    private String email;
}
