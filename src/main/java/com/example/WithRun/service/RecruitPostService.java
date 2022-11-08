package com.example.WithRun.service;

import com.example.WithRun.domain.RecruitPost;
import com.example.WithRun.repository.RecruitPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class RecruitPostService {

    @Autowired
    private RecruitPostRepository recruitPostRepository;

    public void createPost(RecruitPost recruitPost){
        validate(recruitPost);
        recruitPostRepository.save(recruitPost);
    }

    private void validate(RecruitPost recruitPost){
        if(recruitPost ==null){
            throw new RuntimeException("recruitpost is null");
        }
    }
}
