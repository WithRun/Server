package com.example.WithRun.controller;

import com.example.WithRun.dto.CommonResponseDTO;
import com.example.WithRun.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/confirm")
    public ResponseEntity<?> emailConfirm(@RequestHeader String email) throws Exception {
        String confirm = emailService.sendSimpleMessage(email);
        CommonResponseDTO responseDTO = CommonResponseDTO.builder().message(confirm).build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> emailCodeVerify(@RequestHeader String code){
        if(emailService.ePw.equals(code)){
            CommonResponseDTO responseDTO = CommonResponseDTO.builder().message("Code verification success.").build();
            return ResponseEntity.ok(responseDTO);
        }
        CommonResponseDTO responseDTO = CommonResponseDTO.builder().message("Failed verification.").build();
        return ResponseEntity.badRequest().body(responseDTO);
    }


}
