package com.example.WithRun.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "json_id")
public class UserRating {

    @Id
    @GeneratedValue
    @Column(name = "user_rating_id")
    private Long id;

    private String userComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User ratedUser;

    private Long ratingUserId;

    private double rating;
    private LocalDateTime created_at;

    //====================//

    public void setUser(User targetUser) {
//        UserRating userRating = new UserRating();
        if (this.ratedUser != null) {
            this.ratedUser.getRatedUserList().remove(this);
        }
        this.ratedUser = targetUser;
        if (!targetUser.getRatedUserList().contains(this)) {
            targetUser.addRatedUserList(this);
        }
    }

    public Boolean duplicateRating(User user){
        if (Objects.equals(this.getRatingUserId(), user.getId())){
            return true;
        }
        else return false;
    }
}
