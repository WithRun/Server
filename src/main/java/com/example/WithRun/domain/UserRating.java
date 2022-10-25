package com.example.WithRun.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRating {

    @Id
    @GeneratedValue
    @Column(name = "user_rating_id")
    private Long id;

    private String userComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User ratedUser;

    private int rating;
    private LocalDateTime created_at;

}
