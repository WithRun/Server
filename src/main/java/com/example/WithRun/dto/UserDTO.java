package com.example.WithRun.dto;

import com.example.WithRun.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long Id;
    private String username;
    private String userId;
    private String password;
    private List<Long> followingList;
    private List<Long> followerList;
    private String email;
    private double avgRating;
    private int level;
    private int userLatitude;
    private int userLongitude;


    public static UserDTO fromEntity(User user){
        return UserDTO.builder().Id(user.getId()).userId(user.getUserID())
                .username(user.getUsername()).password(user.getUserPassword())
                .followingList(user.getFollowingList()).followerList(user.getFollowerList())
                .email(user.getEmail()).avgRating(user.getAvgRating()).level(user.getLevel())
                .userLatitude(user.getUserLatitude()).userLongitude(user.getUserLongitude())
                .build();
    }

}
