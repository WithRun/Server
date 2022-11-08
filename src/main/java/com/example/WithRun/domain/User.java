package com.example.WithRun.domain;


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
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String username;

//    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "ratedUser")
    @Builder.Default
    private List<UserRating> ratedUserList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "following_username")
    @Builder.Default
    private List<Long> followingList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "follower_username")
    @Builder.Default
    private List<Long> followerList = new ArrayList<>();

    @OneToMany(mappedBy = "freePostUser")
    @Builder.Default
    private List<FreePost> myFreePostList = new ArrayList<>();

    @OneToMany(mappedBy = "freePostCommentUser")
    @Builder.Default
    private List<FreePostComment> myFreePostCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "crewInfoUser")
    @Builder.Default
    private List<CrewInfo> myCrewInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "crewInfoCommentUser")
    @Builder.Default
    private List<CrewInfoComment> myCrewInfoCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "recruitMemberUser")
    @Builder.Default
    private List<RecruitMember> recruitMemberList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_image_id")
    private UserImage myImage;

    private String userID;

    private String userPassword;
    private String email;

    private String role;

    private String provider;
    private String providerId;
    private int userLatitude;
    private int userLongitude;

    private String token;
    private double avgRating;

    private int level;


    // ==============================//
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
