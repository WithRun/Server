package com.example.WithRun.crewinfo.service;


import com.example.WithRun.crewinfo.domain.CrewInfo;
import com.example.WithRun.crewinfo.domain.CrewInfoComment;
import com.example.WithRun.crewinfo.domain.CrewInfoImage;
import com.example.WithRun.common.service.ImageService;
import com.example.WithRun.user.domain.User;
import com.example.WithRun.crewinfo.dto.CrewInfoDTO;
import com.example.WithRun.common.dto.ImageDTO;
import com.example.WithRun.crewinfo.repository.CrewInfoCommentRepository;
import com.example.WithRun.crewinfo.repository.CrewInfoImageRepository;
import com.example.WithRun.crewinfo.repository.CrewInfoRepository;
import com.example.WithRun.user.repository.UserRepository;
import com.example.WithRun.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CrewInfoService {


    @Autowired
    CrewInfoRepository crewInfoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CrewInfoImageRepository crewInfoImageRepository;

    @Autowired
    CrewInfoCommentRepository crewInfoCommentRepository;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;


    public List<CrewInfoDTO> getAllPosts(){
        List<CrewInfo> crewInfoList = crewInfoRepository.findAll();
        List<CrewInfoDTO> crewInfoDTOList = new ArrayList<>();
        for(CrewInfo crewInfo : crewInfoList)
            crewInfoDTOList.add(crewInfo.toDto());
        return crewInfoDTOList;
    }

    public CrewInfo getCrewInfo(String crewInfoId){
        return crewInfoRepository.findById(Long.parseLong(crewInfoId)).orElseThrow();
    }

    public CrewInfoImage getCrewInfoImage(String crewInfoImageId){
        return crewInfoImageRepository.findById(Long.parseLong(crewInfoImageId)).orElseThrow();
    }

    public CrewInfoDTO savePost(String id, CrewInfoDTO crewInfoDTO, List<ImageDTO> imageDTOList){
        User user = userService.getUserById(id);
        CrewInfo crewInfo = CrewInfoDTO.toEntity(crewInfoDTO);
        setDTOtoCrewInfo(crewInfo,crewInfoDTO,imageDTOList,user);
        return crewInfoDTO;
    }

    public CrewInfoDTO savePostWithoutImages(String id, CrewInfoDTO crewInfoDTO){
        User user = userService.getUserById(id);
        CrewInfo crewInfo = CrewInfoDTO.toEntity(crewInfoDTO);
        setDTOtoCrewInfoWithoutImages(crewInfo,crewInfoDTO,user);
        return crewInfoDTO;
    }


    public CrewInfoDTO updatePost(String id, String post_id, CrewInfoDTO crewInfoDTO, List<ImageDTO> imageDTOList){
        User user = userService.getUserById(id);
        CrewInfo crewInfo = getCrewInfo(post_id);

        deleteCrewInfoImage(id, crewInfo.getCrewInfoImageList()); //s3
        crewInfoImageRepository.deleteAll(crewInfo.getCrewInfoImageList()); //repo
        crewInfo.deleteCrewInfoImageList();

        setDTOtoCrewInfo(crewInfo,crewInfoDTO,imageDTOList,user);
        return crewInfoDTO;
    }
    public CrewInfoDTO updatePostWithoutImages(String id, String post_id, CrewInfoDTO crewInfoDTO){
        User user = userService.getUserById(id);
        CrewInfo crewInfo = getCrewInfo(post_id);

//        deleteCrewInfoImage(id, crewInfo.getCrewInfoImageList()); //s3
//        crewInfoImageRepository.deleteAll(crewInfo.getCrewInfoImageList()); //repo
//        crewInfo.deleteCrewInfoImageList();

        setDTOtoCrewInfoWithoutImages(crewInfo,crewInfoDTO,user);
        return crewInfoDTO;
    }

    public void deleteCrewInfo(String id,String post_id){
        CrewInfo crewInfo = getCrewInfo(post_id);
        List<CrewInfoImage> crewInfoImageList = crewInfo.getCrewInfoImageList();
        List<CrewInfoComment> crewInfoCommentList = crewInfo.getCrewInfoCommentList();
        deleteCrewInfoImage(id, crewInfoImageList);

        crewInfoCommentRepository.deleteAll(crewInfoCommentList);
        crewInfoImageRepository.deleteAll(crewInfoImageList);
        crewInfoRepository.delete(crewInfo);
    } // domain에서?


    public List<CrewInfoDTO> searchPost(String keyword){
//        List<CrewInfo> crewInfoList = crewInfoRepository.findAll();
        List<CrewInfo> crewInfoList = crewInfoRepository.findByTitleContainingIgnoreCase(keyword);
//        List<CrewInfo> includeKeywordList = new ArrayList<>();
//        for(CrewInfo crewInfo : crewInfoList){
//            if(crewInfo.getTitle().contains(keyword)){
//                includeKeywordList.add(crewInfo);
//            }
//        }
        List<CrewInfoDTO> crewInfoDTOList = new ArrayList<>();
        for(CrewInfo crewInfo : crewInfoList){
            crewInfoDTOList.add(crewInfo.toDto());
        }
        return crewInfoDTOList;
    }

    public List<CrewInfoImage> createCrewInfoImages(List<ImageDTO> imageDTOList,CrewInfo crewInfo){
        List<CrewInfoImage> list = new ArrayList<>();
        if(imageDTOList.isEmpty())
            return list;
        for(ImageDTO imageDTO : imageDTOList){
            CrewInfoImage crewInfoImage = CrewInfoImage.builder()
                    .url(imageDTO.getUrl())
                    .name(imageDTO.getFilename()).build();
//            crewinfo.addCrewInfoImageList(crewInfoImage);
            crewInfoImageRepository.save(crewInfoImage);
            list.add(crewInfoImage);
        }
        return list;
    }

    public void deleteCrewInfoImage(String id, List<CrewInfoImage> crewInfoImageList){
        List<String> filenameList = new ArrayList<>();
        for(CrewInfoImage crewInfoImage : crewInfoImageList){
            filenameList.add(crewInfoImage.getName());
        }
        imageService.delete(filenameList,id);
    }

    public void setDTOtoCrewInfo(CrewInfo crewInfo, CrewInfoDTO crewInfoDTO, List<ImageDTO> imageDTOList,
                                 User user){

        List<CrewInfoImage> crewInfoImageList = createCrewInfoImages(imageDTOList, crewInfo);


        for(CrewInfoImage crewInfoImage : crewInfoImageList){
            crewInfo.addCrewInfoImageList(crewInfoImage);
        }
        crewInfo.setTitle(crewInfoDTO.getTitle());
        crewInfo.setAuthor(user.getUsername());
        crewInfo.setContent(crewInfoDTO.getContent());
        user.addCrewInfo(crewInfo);

        crewInfoRepository.save(crewInfo);
        userRepository.save(user);
    }
    public void setDTOtoCrewInfoWithoutImages(CrewInfo crewInfo, CrewInfoDTO crewInfoDTO, User user){

        crewInfo.setTitle(crewInfoDTO.getTitle());
        crewInfo.setAuthor(user.getUsername());
        crewInfo.setContent(crewInfoDTO.getContent());
        user.addCrewInfo(crewInfo);

        crewInfoRepository.save(crewInfo);
        userRepository.save(user);
    }


}
