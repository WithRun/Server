package com.example.WithRun.auth.controller;

import com.example.WithRun.user.domain.User;
import com.example.WithRun.common.dto.CommonResponseDTO;
import com.example.WithRun.auth.dto.SignInDTO;
import com.example.WithRun.auth.dto.SignUpDTO;
import com.example.WithRun.auth.service.AuthService;
import com.example.WithRun.auth.service.EmailService;
import com.example.WithRun.user.service.UserService;
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
            CommonResponseDTO responseDTO = CommonResponseDTO.builder().message("이미 존재하는 이메일입니다.").build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody SignUpDTO signUpDTO){
        User user = authService.createUser(signUpDTO);
//        return ResponseEntity.ok().body(user);
        return ResponseEntity.ok().body(signUpDTO);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody SignInDTO signInDTO){
        User getUserInRepo = userService.getUserInRepo(signInDTO);
        if(getUserInRepo.getRole() != null){
            String token = authService.createToken(getUserInRepo);
            signInDTO.setToken(token);
            signInDTO.setUsername(getUserInRepo.getUsername());
            signInDTO.setLoginChecked(true);
//            return ResponseEntity.ok().body(getUserInRepo);
            return ResponseEntity.ok().body(signInDTO);
        }
        else return ResponseEntity.badRequest().body("WRONG PASSWORD.");
    }
}
