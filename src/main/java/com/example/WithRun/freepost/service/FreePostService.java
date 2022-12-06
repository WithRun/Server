package com.example.WithRun.freepost.service;


import com.example.WithRun.common.dto.ImageDTO;
import com.example.WithRun.common.dto.PostingDTO;
import com.example.WithRun.freepost.domain.FreePost;
import com.example.WithRun.freepost.domain.FreePostComment;
import com.example.WithRun.freepost.domain.FreePostImage;
import com.example.WithRun.freepost.dto.FreePostCommentDTO;
import com.example.WithRun.freepost.dto.FreePostDTO;
import com.example.WithRun.freepost.dto.FreePostImageDTO;
import com.example.WithRun.common.service.ImageService;
import com.example.WithRun.user.domain.User;
import com.example.WithRun.freepost.repository.FreePostCommentRepository;
import com.example.WithRun.freepost.repository.FreePostImageRepository;
import com.example.WithRun.freepost.repository.FreePostRepository;
import com.example.WithRun.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FreePostService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FreePostRepository freePostRepository;

    @Autowired
    FreePostImageRepository freePostImageRepository;

    @Autowired
    FreePostCommentRepository freePostCommentRepository;


    @Autowired
    ImageService imageService;

    public FreePost getFreePost(String freePostId) {
        return freePostRepository.findById(Long.parseLong(freePostId)).orElseThrow();
    }

    public FreePostComment getFreePostComment(String freePostCommentId){
        return freePostCommentRepository.findById(Long.parseLong(freePostCommentId)).orElseThrow();
    }


    public List<FreePostDTO> convertToFreePostDTO(List<FreePost> freePostList){
        List<FreePostDTO> freePostDTOList = new ArrayList<>();
        for (FreePost tmp : freePostList) {
            FreePostImageDTO imageDTO = tmp.getFreePostImage().toDto(); // ImageDTO생성

            List<FreePostCommentDTO> commentDTOList = new ArrayList<>(); //commentListDTO생성
            for(FreePostComment comment : tmp.getFreePostCommentList()){
                commentDTOList.add(comment.toDto());
            }

            FreePostDTO dto = tmp.toDTO();
            dto.add(imageDTO,commentDTOList);  // ImageDTO, CommentsDTO 추가
            freePostDTOList.add(dto);
        }

        return freePostDTOList;
    }

    public List<FreePostDTO> getAllPosts() {
        List<FreePost> freePostList  = freePostRepository.findAll();
        return convertToFreePostDTO(freePostList);
    }

    public FreePostDTO createFreePost(User user, MultipartFile imageFile, PostingDTO postingDTO, String localOrServer) throws IOException {

        FreePostImage freePostImage = imageService.uploadFreePostImage(imageFile, user.getId().toString(),localOrServer);

        FreePost freePost = FreePost.builder().freePostUser(user).author(user.getUsername())
                .title(postingDTO.getTitle()).content(postingDTO.getContent())
                .build();
        freePost.addFreePostImage(freePostImage);

        freePostRepository.save(freePost);
        freePostImageRepository.save(freePostImage);


        FreePostImageDTO freePostImageDTO = freePostImage.toDto();
        FreePostDTO freePostDTO = freePost.toDTO();
        freePostDTO.setFreePostImageDTO(freePostImageDTO);

        return freePostDTO;
    }


    public String deleteFreePost(User user, FreePost freePost, String localServer) {

        if(!freePost.getFreePostUser().equals(user))
            return "You can only delete your own post.";

        imageService.deleteFreePostImage(freePost.getFreePostImage().getName(), user.getId().toString(), localServer);
        freePostCommentRepository.deleteAll(freePost.getFreePostCommentList());
        freePostImageRepository.delete(freePost.getFreePostImage());
        freePostRepository.delete(freePost);

        //freepost, freepostcomment, freepostimage delete
        // repo save

        return "Successfully deleted.";
    }

    public FreePostCommentDTO createFreePostComment(FreePost freePost, User user, String comment) {
        FreePostComment freePostComment = FreePostComment.builder()
                .freePostCommentUser(user).freePostCommentFreePost(freePost)
                .content(comment).build();

        freePostCommentRepository.save(freePostComment);

        freePost.addFreePostComment(freePostComment);

        // freepost save?

        return FreePostCommentDTO.builder().id(freePostComment.getId())
                .author(freePostComment.getFreePostCommentUser().getUsername())
                .content(freePostComment.getContent()).build();
    }

    public String deleteFreePostComment(User user, FreePostComment freePostComment) {
        if(!freePostComment.getFreePostCommentUser().equals(user)){
            return "you can only delete your own comment.";
        }
        freePostCommentRepository.delete(freePostComment);

        return "Successfully deleted.";
    }



    public List<FreePostDTO> searchPost(String keyword){
        List<FreePost> freePostList =freePostRepository.findByTitleContainingIgnoreCase(keyword);
        return convertToFreePostDTO(freePostList);
    }







}
