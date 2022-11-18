package com.example.WithRun.controller;

import com.amazonaws.Response;
import com.example.WithRun.domain.CrewInfo;
import com.example.WithRun.domain.CrewInfoImage;
import com.example.WithRun.domain.User;
import com.example.WithRun.dto.CrewInfoDTO;
import com.example.WithRun.dto.ImageDTO;
import com.example.WithRun.dto.UserDTO;
import com.example.WithRun.service.CrewInfoService;
import com.example.WithRun.service.ImageService;
import com.example.WithRun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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
        return ResponseEntity.ok().body(crewInfoService.getAllPosts());
}

    @PostMapping("/post")
    public ResponseEntity<?> postCrewInfo(@AuthenticationPrincipal String id,
                                                 @RequestPart List<MultipartFile> images,
                                                 @RequestPart CrewInfoDTO crewInfoDTO) throws IOException {
        User user = userService.getUserById(id);
        List<ImageDTO> imageDTOList = imageService.upload(images,id);
        return ResponseEntity.ok().body(crewInfoService.savePost(id, crewInfoDTO,imageDTOList));
    }

    @PutMapping("/post/{post_id}")
    public ResponseEntity<?> modifyCrewInfo(@AuthenticationPrincipal String id,
                                            @PathVariable @RequestHeader String post_id,
                                            @RequestPart CrewInfoDTO crewInfoDTO,
                                            @RequestPart List<MultipartFile> images) throws IOException {
        User user = userService.getUserById(id);
        CrewInfo crewInfo = crewInfoService.getCrewInfo(post_id);
        if(Objects.equals(crewInfo.getCrewInfoUser().getId(), user.getId())){
            List<ImageDTO> imageDTOList = imageService.upload(images,id);
            crewInfoService.updatePost(id,post_id,crewInfoDTO,imageDTOList);
        }
        return ResponseEntity.ok().body(crewInfoDTO);
    }

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


}
