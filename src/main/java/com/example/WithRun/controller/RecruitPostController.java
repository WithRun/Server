package com.example.WithRun.controller;

import com.example.WithRun.domain.RecruitPost;
import com.example.WithRun.domain.Tag;
import com.example.WithRun.service.RecruitPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("recruitpost")
public class RecruitPostController {

    @Autowired
    private RecruitPostService recruitPostService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody RecruitPost recruitPost,@RequestParam Tag... tags){

        //param으로 들어온 userID를 받아와서 user를 검색하고 아니면
        //그냥 파라미터로 들어올때 userId로 auth한다.

        for(Tag inputTag : tags){
            //유저 엔티티 tags에 넣어준다. user
            recruitPost.addTag(inputTag);
        }

        recruitPostService.createPost(recruitPost);
        // save를 해야지 다
        return ResponseEntity.ok().body(recruitPost);
    }

//    @GetMapping
//    public ResponseEntity<?> getPostByTags(@RequestParam Tag... tags){
//        // tag를 가진 게시글들을 서치해서 순서대로 가져온다
//
//    }


}
