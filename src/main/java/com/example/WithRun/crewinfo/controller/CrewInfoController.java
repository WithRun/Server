package com.example.WithRun.crewinfo.controller;

import com.example.WithRun.crewinfo.domain.CrewInfo;
import com.example.WithRun.user.domain.User;
import com.example.WithRun.crewinfo.dto.CrewInfoDTO;
import com.example.WithRun.common.dto.ImageDTO;
import com.example.WithRun.user.dto.UserDTO;
import com.example.WithRun.crewinfo.service.CrewInfoService;
import com.example.WithRun.common.service.ImageService;
import com.example.WithRun.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("crewinfo")
public class CrewInfoController {

    @Autowired
    CrewInfoService crewInfoService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @GetMapping
    public ResponseEntity<?> crewInfoMainPage(){

        Map<String, List> results = new HashMap<>();
        results.put("results", crewInfoService.getAllPosts());
//        return ResponseEntity.ok().body(crewInfoService.getAllPosts());
        return ResponseEntity.ok().body(results);
}

//    @PostMapping("/post")
//    public ResponseEntity<?> postCrewInfo(@AuthenticationPrincipal String id,
//                                                 @RequestPart List<MultipartFile> images,
//                                                 @RequestPart CrewInfoDTO crewInfoDTO) throws IOException {
//        User user = userService.getUserById(id);
//        List<ImageDTO> imageDTOList = imageService.upload(images,id);
//        return ResponseEntity.ok().body(crewInfoService.savePost(id, crewInfoDTO,imageDTOList));
//    }

//    @PutMapping("/post/{post_id}")
//    public ResponseEntity<?> modifyCrewInfo(@AuthenticationPrincipal String id,
//                                            @PathVariable @RequestHeader String post_id,
//                                            @RequestPart CrewInfoDTO crewInfoDTO,
//                                            @RequestPart List<MultipartFile> images) throws IOException {
//        User user = userService.getUserById(id);
//        CrewInfo crewInfo = crewInfoService.getCrewInfo(post_id);
//        if(Objects.equals(crewInfo.getCrewInfoUser().getId(), user.getId())){
//            List<ImageDTO> imageDTOList = imageService.upload(images,id);
//            crewInfoService.updatePost(id,post_id,crewInfoDTO,imageDTOList);
//        }
//        return ResponseEntity.ok().body(crewInfoDTO);
//    }

    @DeleteMapping("/post/{post_id}")
    public ResponseEntity<?> deleteCrewInfo(@AuthenticationPrincipal String id,
                                            @PathVariable @RequestHeader String post_id){
        User user = userService.getUserById(id);
        CrewInfo crewinfo = crewInfoService.getCrewInfo(post_id);
        if (Objects.equals(crewinfo.getCrewInfoUser().getId(), user.getId())){
            crewInfoService.deleteCrewInfo(id, post_id);
            return ResponseEntity.ok().body("deleteCrewInfo Succeed.");
        }
        else
            return ResponseEntity.badRequest().body("deleteCrewInfo failed.");
    }

//    @PostMapping("/post/image")
//    public ResponseEntity<?> postCrewInfoImage(@AuthenticationPrincipal String id,
//                                               @RequestPart List<MultipartFile> images) throws IOException {
//        return ResponseEntity.ok().body(imageService.upload(images,id));
//    }

    @GetMapping("/post")
    public ResponseEntity<?> getPostPage(@AuthenticationPrincipal String id){
        return ResponseEntity.ok().body(UserDTO.fromEntity(userService.getUserById(id)));
    }

    @PostMapping("/post/search")
    public ResponseEntity<?> searchPost(@AuthenticationPrincipal String id,
                                        @RequestParam String keyword){
        
        return ResponseEntity.ok().body(crewInfoService.searchPost(keyword));
    }

    @PostMapping("/posting")
    ResponseEntity<?> postingCrewInfo(@AuthenticationPrincipal String id,
                                                   @RequestBody CrewInfoDTO crewInfoDTO){
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(crewInfoService.savePostWithoutImages(id, crewInfoDTO));
    }

    @PutMapping("/posting/{post_id}")
    public ResponseEntity<?> modifyingCrewInfo(@AuthenticationPrincipal String id,
                                            @PathVariable @RequestHeader String post_id,
                                            @RequestBody CrewInfoDTO crewInfoDTO
                                            ){
        User user = userService.getUserById(id);
        CrewInfo crewInfo = crewInfoService.getCrewInfo(post_id);
        if(Objects.equals(crewInfo.getCrewInfoUser().getId(), user.getId())){
            crewInfoService.updatePostWithoutImages(id,post_id,crewInfoDTO);
        }
        return ResponseEntity.ok().body(crewInfoDTO);
    }



}
