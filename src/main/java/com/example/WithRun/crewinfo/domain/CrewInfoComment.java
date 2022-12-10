package com.example.WithRun.crewinfo.domain;


import com.example.WithRun.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrewInfoComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crewinfo_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User crewInfoCommentUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crewinfo_id")
    private CrewInfo crewInfoFromComment;

    private String content;
    private LocalDateTime created_at;

}
