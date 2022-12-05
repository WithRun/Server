package com.example.WithRun.domain;

import com.example.WithRun.dto.FreePostCommentDTO;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "freepost_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User freePostCommentUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freepost_id")
    private FreePost freePostCommentFreePost;

    private String content;
    private LocalDateTime created_at;

    public FreePostCommentDTO toDto(){

        return FreePostCommentDTO.builder()
                .id(id).author(this.getFreePostCommentUser().getUsername())
                .content(content).created_at(created_at).build();
    }

}
