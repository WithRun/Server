package com.example.WithRun.controller;

import com.example.WithRun.domain.User;
import com.example.WithRun.dto.CommonResponseDTO;
import com.example.WithRun.dto.SignInDTO;
import com.example.WithRun.dto.SignUpDTO;
import com.example.WithRun.service.AuthService;
import com.example.WithRun.service.EmailService;
import com.example.WithRun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    EmailService emailService;

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @GetMapping("/signup/check/ID")
    public ResponseEntity<?> checkId(@RequestHeader String userId){
        if(!userService.isExistByUserId(userId)){
            CommonResponseDTO responseDTO = CommonResponseDTO.builder().message("사용가능한 ID입니다.").build();
            return ResponseEntity.ok().body(responseDTO);
        }
        else{
            CommonResponseDTO responseDTO = CommonResponseDTO.builder().message("이미 존재하는 ID입니다.").build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("signup/check/username")
    public ResponseEntity<?> checkUsername(@RequestHeader String username){
        if(!userService.isExistByUsername(username)){
            CommonResponseDTO responseDTO = CommonResponseDTO.builder().message("사용 가능한 닉네임입니다.").build();
            return ResponseEntity.ok().body(responseDTO);
        }
        else{
            CommonResponseDTO responseDTO = CommonResponseDTO.builder().message("이미 존재하는 닉네임입니다.").build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("signup/check/email")
    public ResponseEntity<?> checkEmail(@RequestHeader String email){
        if(!userService.isExistByEmail(email)) {
            CommonResponseDTO responseDTO = CommonResponseDTO.builder().message("사용 가능한 이메일입니다.").build();
            return ResponseEntity.ok().body(responseDTO);
        }
        else{
            CommonResponseDTO responseDTO = CommonResponseDTO.builder().message("이미 존재하는 닉네임입니다.").build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody SignUpDTO signUpDTO){
        User user = authService.createUser(signUpDTO);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody SignInDTO signInDTO){
        User getUserInRepo = userService.getUserInRepo(signInDTO);
        if(getUserInRepo.getRole() != null){
            String token = authService.createToken(getUserInRepo);
            return ResponseEntity.ok().body(getUserInRepo);
        }
        else return ResponseEntity.badRequest().body("WRONG PASSWORD.");
    }
}
