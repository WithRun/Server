package com.example.WithRun.user.domain;


import com.example.WithRun.crewinfo.domain.CrewInfo;
import com.example.WithRun.crewinfo.domain.CrewInfoComment;
import com.example.WithRun.food.domain.FoodImage;
import com.example.WithRun.freepost.domain.FreePost;
import com.example.WithRun.freepost.domain.FreePostComment;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String username;

//    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "ratedUser")
    private List<UserRating> ratedUserList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "following_username")
    private List<Long> followingList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "follower_username")
    private List<Long> followerList = new ArrayList<>();

    @OneToMany(mappedBy = "freePostUser")
    private List<FreePost> myFreePostList = new ArrayList<>();

    @OneToMany(mappedBy = "freePostCommentUser")
    private List<FreePostComment> myFreePostCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "crewInfoUser")
    private List<CrewInfo> myCrewInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "crewInfoCommentUser")
    private List<CrewInfoComment> myCrewInfoCommentList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_image_id")
    private UserImage myImage;

    @OneToMany(mappedBy = "foodImageUser", cascade = CascadeType.ALL)
    private List<FoodImage> myFoodImageList = new ArrayList<>();




    private String userID;

    private String userPassword;
    private String email;

    private String role;

    private String provider;
    private String providerId;
    private String userLatitude;
    private String userLongitude;

    private String token;
    private double avgRating;

    private int level;


    // ==========================================//
    public void addFollowingList(User followUser){
        getFollowingList().add(followUser.id);
        followUser.getFollowerList().add(this.id);
    }

    public void addFollowerList(User followUser){
        getFollowerList().add(followUser.id);
        followUser.getFollowingList().add(this.id);
    }

    public void deleteFollowingList(User unfollowUser){
        getFollowingList().remove(unfollowUser.id);
        unfollowUser.getFollowerList().remove(this.id);
    }

    public void addRatedUserList(UserRating userRating){
        this.ratedUserList.add(userRating);
        if(userRating.getRatedUser() != this)
            userRating.setRatedUser(this);
//        targetUser.getRatedUserList().add(userRating);
    }

    public void addUserImage(UserImage userImage){
        this.myImage = userImage;
        userImage.setUser(this);
    }

    public void addCrewInfo(CrewInfo crewInfo){
        this.myCrewInfoList.add(crewInfo);
        crewInfo.setCrewInfoUser(this);
    }


    //==============================================//
    public double MyAvgRating(){
        double AvgRating =0;
        int count =0;
        if(this.ratedUserList.size()==0)
            return 0;
        for(UserRating myrating : this.ratedUserList){
            AvgRating += myrating.getRating();
            count++;
        }
        AvgRating /= count;
        return AvgRating;
    }

    public int countMyRatedList(){
        int count = 0;

        if(this.ratedUserList.size()==0)
            return 0;
        for(UserRating myrating : this.ratedUserList){
            count++;
        }
        return count;
    }

    public void setMyLevel(){
        double myRatingScore = MyAvgRating();
        int myRatedList = countMyRatedList();
        if(myRatingScore >= 4.0){
            this.level = myRatedList/10;
        }
        this.avgRating = myRatingScore;
    }

}
