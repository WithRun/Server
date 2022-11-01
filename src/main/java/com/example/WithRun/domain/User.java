package com.example.WithRun.domain;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "ratedUser")
    private List<UserRating> ratedUserList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "following_username")
    private List<String> followingList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "follower_username")
    private List<String> followerList = new ArrayList<>();

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
}
