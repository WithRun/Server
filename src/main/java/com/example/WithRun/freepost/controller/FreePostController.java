package com.example.WithRun.freepost.controller;

import com.example.WithRun.freepost.domain.FreePost;
import com.example.WithRun.freepost.domain.FreePostComment;
import com.example.WithRun.user.domain.User;
import com.example.WithRun.freepost.dto.FreePostCommentDTO;
import com.example.WithRun.freepost.dto.FreePostDTO;
import com.example.WithRun.common.dto.PostingDTO;
import com.example.WithRun.freepost.service.FreePostService;
import com.example.WithRun.common.service.ImageService;
import com.example.WithRun.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/freepost")
public class FreePostController {

    @Autowired
    ImageService imageService;

    @Autowired
    FreePostService freePostService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllFreePost(@AuthenticationPrincipal String id){
        User user = userService.getUserById(id);

        List<FreePostDTO> freePostDTOList = freePostService.getAllPosts();

        return ResponseEntity.ok().body(freePostDTOList);
    }


//    @PostMapping("/post")
//    public ResponseEntity<?> createFreePost(@AuthenticationPrincipal String id,
//                                            @RequestParam(required = false) MultipartFile image
//                                            ) throws IOException {
//
//        User user = userService.getUserById(id);
//
//        System.out.println("image @@@@@@@@@@@@@@@@   " + image.getOriginalFilename());
//        System.out.println("image @@@@@@@@@@@@@@@@   " + image.getContentType());
//        System.out.println("image @@@@@@@@@@@@@@@@   " + image.getName());
//
//        PostingDTO postingDTO = PostingDTO.builder().title("title").content("content").build();
//
//        FreePostDTO freePostDTO = freePostService.createFreePost(user,image,postingDTO, "Server");
//
//        return ResponseEntity.ok().body(freePostDTO);
//
//    }

    @PostMapping("/post/text")
    public ResponseEntity<?> createFreePostText(@AuthenticationPrincipal String id,
                                                @RequestBody PostingDTO postingDTO) throws IOException {
        User user = userService.getUserById(id);
        FreePostDTO freePostDTO = freePostService.createFreePostText(user,postingDTO);

        return ResponseEntity.ok().body(freePostDTO);
    }

    @PostMapping("/post/image")
    public ResponseEntity<?> createFreePostImage(@AuthenticationPrincipal String id,
                                            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        User user = userService.getUserById(id);

        FreePostDTO freePostDTO = freePostService.createFreePostImage(user,image, "Server");

        return ResponseEntity.ok().body(freePostDTO);
    }


    @DeleteMapping("/post/{freepost_id}")
    public ResponseEntity<?> deleteFreePost(@AuthenticationPrincipal String id,
                                            @PathVariable String freepost_id){

        User user = userService.getUserById(id);
        FreePost freePost = freePostService.getFreePost(freepost_id);

        return ResponseEntity.ok().body(freePostService.deleteFreePost(user,freePost,"Server"));
    }

    @PostMapping("/{freepost_id}/comment")
    public ResponseEntity<?> createComment(@AuthenticationPrincipal String id,
                                           @PathVariable String freepost_id,
                                           @RequestParam String comment){

        User user = userService.getUserById(id);
        FreePost freePost = freePostService.getFreePost(freepost_id);

        FreePostCommentDTO commentDTO = freePostService.createFreePostComment(freePost,user,comment);

        return ResponseEntity.ok().body(commentDTO);

    }

    @DeleteMapping("/{freepostcomment_id}/comment")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal String id,
                                           @PathVariable String freepostcomment_id){

        User user = userService.getUserById(id);
        FreePostComment freePostComment = freePostService.getFreePostComment(freepostcomment_id);

        return ResponseEntity.ok().body(freePostService.deleteFreePostComment(user,freePostComment));

    }

    @PostMapping("/post/search")
    public ResponseEntity<?> searchPost(@AuthenticationPrincipal String id,
                                        @RequestParam String keyword){

        return ResponseEntity.ok().body(freePostService.searchPost(keyword));
    }


}
