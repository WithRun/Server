package com.example.WithRun.controller;


import com.example.WithRun.domain.User;
import com.example.WithRun.domain.UserImage;
import com.example.WithRun.domain.UserRating;
import com.example.WithRun.dto.ImageDTO;
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
import java.util.List;

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

//        return ResponseEntity.ok().body(user);
        return ResponseEntity.ok().body(user);
    }


    @PutMapping("/mypage/{id}")
    public ResponseEntity<?> changeMyName(@PathVariable("id") @AuthenticationPrincipal String id,
                                          @RequestHeader String newName){
        User user = userService.getUserById(id);
        user = userService.changeUsername(user, newName);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/mypage/upload/image/{id}")
    public ResponseEntity<?> uploadProfileImage(@PathVariable("id") @AuthenticationPrincipal String id,
                                                @RequestPart List<MultipartFile> images) throws IOException {
        User user = userService.getUserById(id);
        List<ImageDTO> imageDTOList = imageService.upload(images, id);
        UserImage userImage = userService.saveUserImage(imageDTOList,user);

        return ResponseEntity.ok().body(userImage);
    }

    @DeleteMapping("/mypage/delete/image/{id}")
    public ResponseEntity<?> deleteProfileImage(@PathVariable("id") @AuthenticationPrincipal String id,
                                                @RequestBody List<String> filenames){
        imageService.delete(filenames, id);
        User user = userService.getUserById(id);
        for(String filename : filenames){
            userService.deleteUserImage(filename,user);
        }

        return ResponseEntity.ok().body("deleted successfully");
    }

    @GetMapping("/mypage/following")
    public ResponseEntity<?> getFollowingList(@AuthenticationPrincipal String id){
        return ResponseEntity.ok().body(userService.getFollowingUserListById(id));
    }

    @GetMapping("/mypage/follower")
    public ResponseEntity<?> getFollowerList(@AuthenticationPrincipal String id){
        return ResponseEntity.ok().body(userService.getFollowerUserListById(id));
    }

    @PostMapping("/userpage/follow")
    public ResponseEntity<?> followUser(@RequestHeader String targetId,
                                        @AuthenticationPrincipal String id){
        userService.followUser(id,targetId);
        return ResponseEntity.ok().body("follow succeed.");
    }

    @DeleteMapping("/userpage/unfollow")
    public ResponseEntity<?> unfollowUser(@RequestHeader String targetId,
                                          @AuthenticationPrincipal String id){
        userService.unfollowUser(id, targetId);
        return ResponseEntity.ok().body("unfollow succeed.");
    }

    @PostMapping("/userpage/rating")
    public ResponseEntity<?> ratingUser(@AuthenticationPrincipal String id,
                                        @RequestBody RatingDTO ratingDTO) throws Exception {
        User user = userService.getUserById(id);
        UserRating userRating = userService.rateUser(id, ratingDTO);
        return ResponseEntity.ok().body(ratingDTO);
    }

    @GetMapping("/userpage/{targetId}")
    public ResponseEntity<?> getUserPage(@AuthenticationPrincipal String id,
                                         @RequestHeader @PathVariable String targetId){
        User targetUser = userService.getUserById(targetId);

        return ResponseEntity.ok().body(targetUser);
//        return ResponseEntity.ok().body("success");
    }


}
