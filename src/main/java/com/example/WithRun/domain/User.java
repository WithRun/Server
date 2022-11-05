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
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
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

    @OneToMany(mappedBy = "recruitMemberUser")
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

}
