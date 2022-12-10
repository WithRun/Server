package com.example.WithRun.common.controller;


import com.example.WithRun.config.auth.PrincipalDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {
    @GetMapping("/index")
    public ResponseEntity<?> test(Authentication authentication){
        System.out.println("===================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication : " +oAuth2User.getAttributes());

        return ResponseEntity.ok().body(oAuth2User);
    }

    @GetMapping("/test/oauth")
    public ResponseEntity<?> oauthTest(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return ResponseEntity.ok().body(principalDetails.getUser());
    }

    @GetMapping("/login")
    public String testLogin(@AuthenticationPrincipal String userId){
        System.out.println("===================================");
        System.out.println("userID : "+userId);
        return "gogo";
    }
}
