package com.example.WithRun.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreePost {

    @Id
    @GeneratedValue
    @Column(name = "freepost_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User freePostUser;

    @OneToMany(mappedBy = "freePostFromComment")
    private List<FreePostComment> freePostCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "freePostFromImage")
    private List<FreePostImage> freePostImageList = new ArrayList<>();

    private String title;
    private String author;
    private String content;
    private LocalDateTime created_at;
}
