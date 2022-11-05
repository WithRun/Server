package com.example.WithRun.domain;


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
    @GeneratedValue
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
