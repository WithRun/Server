package com.example.WithRun.user.service;


import com.example.WithRun.auth.dto.SignInDTO;
import com.example.WithRun.common.dto.ImageDTO;
import com.example.WithRun.user.domain.User;
import com.example.WithRun.user.domain.UserImage;
import com.example.WithRun.user.domain.UserRating;
import com.example.WithRun.user.dto.LocationDTO;
import com.example.WithRun.user.dto.RatingDTO;
import com.example.WithRun.user.dto.UserDTO;
import com.example.WithRun.user.repository.UserImageRepository;
import com.example.WithRun.user.repository.UserRatingRepository;
import com.example.WithRun.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRatingRepository userRatingRepository;

    @Autowired
    UserImageRepository userImageRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public Boolean validateIdAndPassword(SignInDTO signInDTO){
        User getUserById = userRepository.findByUserID(signInDTO.getUserId());

        return getUserById != null && passwordEncoder
                .matches(signInDTO.getPassword(), getUserById.getUserPassword());
    }


    public User changeUsername(User user, String newName){
        if(isExistByUsername(newName)){
            return user;
        }
        user.setUsername(newName);
        userRepository.save(user);
        return user;
    }

    public User getUserInRepo(SignInDTO signInDTO){
        if(validateIdAndPassword(signInDTO)){
            return userRepository.findByUserID(signInDTO.getUserId());
        }
        else return new User();
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getUserById(String userId){
        return userRepository.findById(Long.parseLong(userId)).orElseThrow(); //NoSuchElementException
    }

    public void followUser(String userId, String targetId){
        User user = getUserById(userId);
        User targetUser = getUserById(targetId);
        if(!targetUser.getFollowerList().contains(Long.parseLong(userId)) && !user.equals(targetUser))
            user.addFollowingList(targetUser);
        userRepository.save(user);
    }

    public void unfollowUser(String userId, String targetId){
        User user = getUserById(userId);
        User targetUser = getUserById(targetId);
        user.deleteFollowingList(targetUser);
        userRepository.save(user);
    }

    public UserRating rateUser(String userId, RatingDTO ratingDTO){
        User targetUser = getUserById(ratingDTO.getTargetId());
        User user = getUserById(userId);
        for(UserRating userRating : targetUser.getRatedUserList()) {
            if(userRating.duplicateRating(user) && user.equals(targetUser)){
                return null;
            }
        }
        UserRating userRating = UserRating.builder()
                .rating(Double.parseDouble(ratingDTO.getRate()))
                .ratingUserId(Long.parseLong(userId)).ratedUser(targetUser)
                .userComment(ratingDTO.getComment()).build();

        targetUser.getRatedUserList().add(userRating);
        targetUser.setMyLevel();

        userRatingRepository.save(userRating);
        userRepository.save(targetUser);
        return userRating;
    }

    public List<UserDTO> getFollowingUserListById(String userId){
        List<UserDTO> FollowingList = new ArrayList<>();
        List<Long> userFollowingList = getUserById(userId).getFollowingList();
        for(Long followingId : userFollowingList){
            UserDTO userDTO = UserDTO.fromEntity(getUserById(Long.toString(followingId)));
            FollowingList.add(userDTO);
        }
        return FollowingList;
    }

    public List<UserDTO> getFollowerUserListById(String userId){
        List<UserDTO> FollowerList = new ArrayList<>();
        List<Long> userFollowerList = getUserById(userId).getFollowerList();
        for(Long followerId : userFollowerList){
            UserDTO userDTO = UserDTO.fromEntity(getUserById(Long.toString(followerId)));
            FollowerList.add(userDTO);
        }
        return FollowerList;
    }

    public UserImage saveUserImage(List<ImageDTO> imageDTOList, User user){
        UserImage userImage = new UserImage();
        for(ImageDTO imageDTO : imageDTOList){
            userImage.setUrl(imageDTO.getUrl());
            userImage.setName(user.getId()+"_"+imageDTO.getFilename());
        }
        user.addUserImage(userImage);
        userImageRepository.save(userImage);
        userRepository.save(user);

        return userImage;
    }

    public void deleteUserImage(String filename, User user){
        UserImage userImage = userImageRepository.findByName(user.getId()+"_"+filename);
        userImageRepository.delete(userImage);

        user.setMyImage(null);
        userRepository.save(user);
    }

    public void setUserLocation(LocationDTO locationDTO, User user){

        user.setUserLongitude(locationDTO.getLng());
        user.setUserLatitude(locationDTO.getLat());



    }



    public Boolean isExistByUserId(String userId) {
        return userRepository.existsByUserID(userId);
    }

    public Boolean isExistByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean isExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}
