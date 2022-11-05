package com.example.WithRun.service;


import com.example.WithRun.domain.User;
import com.example.WithRun.domain.UserRating;
import com.example.WithRun.dto.RatingDTO;
import com.example.WithRun.dto.SignInDTO;
import com.example.WithRun.repository.UserRatingRepository;
import com.example.WithRun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRatingRepository userRatingRepository;

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
        else return null;
    }

    public User getUserById(String userId){
        return userRepository.findById(Long.parseLong(userId)).orElseThrow(); //NoSuchElementException
    }

    public void followUser(String userId, String targetId){
        User user = getUserById(userId);
        User targetUser = getUserById(targetId);
        user.addFollowingList(targetUser);
        userRepository.save(user);
    }

    public void unfollowUser(String userId, String targetId){
        User user = getUserById(userId);
        User targetUser = getUserById(targetId);
        user.deleteFollowingList(targetUser);
        userRepository.save(user);
    }

    public UserRating rateUser(String userId, RatingDTO ratingDTO) throws Exception {
        User targetUser = getUserById(ratingDTO.getTargetUserId());
        User user = getUserById(userId);
        for(UserRating userRating : targetUser.getRatedUserList()){
            userRating.duplicateRating(user);
        }

        UserRating userRating = UserRating.builder()
                .rating(Double.parseDouble(ratingDTO.getRate()))
                .ratingUserId(Long.parseLong(userId)).ratedUser(targetUser)
                .userComment(ratingDTO.getComment()).build();

        userRatingRepository.save(userRating);
        userRepository.save(targetUser);
        return userRating;
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
