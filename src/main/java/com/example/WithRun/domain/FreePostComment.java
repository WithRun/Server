package com.example.WithRun.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreePostComment {

    @Id
    @GeneratedValue
    @Column(name = "freepost_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User freePostCommentUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freepost_id")
    private FreePost freePostFromComment;

    private String content;
    private LocalDateTime created_at;

}
