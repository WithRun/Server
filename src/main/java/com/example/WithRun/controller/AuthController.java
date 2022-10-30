package com.example.WithRun.controller;

import com.example.WithRun.domain.User;
import com.example.WithRun.dto.SignInDTO;
import com.example.WithRun.dto.SignUpDTO;
import com.example.WithRun.service.AuthService;
import com.example.WithRun.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    EmailService emailService;

    @Autowired
    AuthService authService;

    @GetMapping("/signup/check/ID")
    public ResponseEntity<?> checkId(@RequestParam String userId){
        if(!authService.isExistByUserId(userId))
            return ResponseEntity.ok().body("사용가능한 ID입니다.");
        else return ResponseEntity.badRequest().body("이미 존재하는 ID입니다.");
    }

    @GetMapping("signup/check/username")
    public ResponseEntity<?> checkUsername(@RequestParam String username){
        if(!authService.isExistByUsername(username))
            return ResponseEntity.ok().body("사용가능한 닉네임입니다.");
        else return ResponseEntity.badRequest().body("이미 존재하는 닉네임입니다.");
    }

    @GetMapping("signup/check/email")
    public ResponseEntity<?> checkEmail(@RequestParam String email){
        if(!authService.isExistByEmail(email))
            return ResponseEntity.ok().body("사용가능한 이메일입니다.");
        else return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다.");
    }

    @PostMapping("/signup/create")
    public ResponseEntity<?> createUser(@RequestBody SignUpDTO signUpDTO){
        User user = authService.createUser(signUpDTO);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody SignInDTO signInDTO){
        //signinDTO를 검증한다 User가 진짜 있는지 쳌크
        // 그리고 password겁증한다. Authservice에서 bool값으로 true 나오면 검증완료
        // Tokne 주기
        // 준 토큰을 SigninDTO 넣어서가 아니고 User에 넣어야되는데 User에 저장
        // User리턴하면 되나?

        User getUserInRepo = authService.getUserInRepo(signInDTO);
        String token = authService.createToken(getUserInRepo);

        return ResponseEntity.ok().body(getUserInRepo);
    }

}
