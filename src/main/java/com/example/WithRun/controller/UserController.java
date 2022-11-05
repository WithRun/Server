package com.example.WithRun.controller;


import com.example.WithRun.domain.User;
import com.example.WithRun.domain.UserRating;
import com.example.WithRun.dto.RatingDTO;
import com.example.WithRun.service.AuthService;
import com.example.WithRun.service.ImageService;
import com.example.WithRun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    AuthService authService;

    @Autowired
    ImageService imageService;

    @Autowired
    UserService userService;


    @GetMapping("/mypage")
    public ResponseEntity<?> getMyPage(@AuthenticationPrincipal String id){
        User user = userService.getUserById(id);

        return ResponseEntity.ok().body(user);

    }


    @PostMapping("/mypage/upload/image/{id}")
    public ResponseEntity<?> uploadProfileImage(@PathVariable("id") @AuthenticationPrincipal String id,
                                                @RequestPart MultipartFile image) throws IOException {
        //서비스
        String StringId = id;
        User user = userService.getUserById(id);

        String path = imageService.upload(image,StringId);
        // userId에 맞는 엔티티 불러오기 , 엔티티의 user image 불러오기 , 값넣기 , 저장하기 , DTO 형태로 리턴하기

        return ResponseEntity.ok().body("path :" + path);
    }

    @PutMapping("/mypage/{id}")
    public ResponseEntity<?> changeMyName(@PathVariable("id") @AuthenticationPrincipal String id,
                                          @RequestParam String newName){
        User user = userService.getUserById(id);
        user = userService.changeUsername(user, newName);
        return ResponseEntity.ok().body(user);
    }


    @DeleteMapping("/mypage/delete/image/{id}")
    public ResponseEntity<?> deleteProfileImage(@PathVariable("id") @AuthenticationPrincipal String id,
                                                @RequestParam String filename){
        imageService.delete(filename, id);
        return ResponseEntity.ok().body("deleted successfully");
    }

    @GetMapping("/mypage/following")
    public ResponseEntity<?> getFollowingList(@AuthenticationPrincipal String id){
        return ResponseEntity.ok().body(userService.getUserById(id).getFollowingList());
    }

    @GetMapping("/mypage/follower")
    public ResponseEntity<?> getFollowerList(@AuthenticationPrincipal String id){
        return ResponseEntity.ok().body(userService.getUserById(id).getFollowerList());
    }

    @PostMapping("/userpage/follow")
    public ResponseEntity<?> followUser(@RequestParam String targetId,
                                        @AuthenticationPrincipal String id){
        userService.followUser(id,targetId);
        return ResponseEntity.ok().body("follow successed.");
    }

    @DeleteMapping("/userpage/unfollow")
    public ResponseEntity<?> unfollowUser(@RequestParam String targetId,
                                          @AuthenticationPrincipal String id){
        userService.unfollowUser(id, targetId);
        return ResponseEntity.ok().body("unfollow successed.");
    }

    @PostMapping("/userpage/rating")
    public ResponseEntity<?> ratingUser(@AuthenticationPrincipal String id,
                                        @RequestBody RatingDTO ratingDTO) throws Exception {
        User user = userService.getUserById(id);
        UserRating userRating = userService.rateUser(id, ratingDTO);
        return ResponseEntity.ok().body(userRating);
    }


}
