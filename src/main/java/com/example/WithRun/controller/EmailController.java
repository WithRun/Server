package com.example.WithRun.controller;

import com.example.WithRun.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/emailConfirm")
    public ResponseEntity<?> emailConfirm(@RequestParam String email) throws Exception {

        String confirm = emailService.sendSimpleMessage(email);
        return ResponseEntity.ok().body(confirm);
    }

    @PostMapping("/emailcode/verify")
    public ResponseEntity<?> emailCodeVerify(@RequestParam String code){
        if(emailService.ePw.equals(code)){
            return ResponseEntity.ok("Code verification success.");
        }
        return ResponseEntity.badRequest().body("Failed verification.");
    }


}
