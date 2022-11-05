package com.example.WithRun.domain;

import lombok.*;
import net.bytebuddy.matcher.FilterableList;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrewInfo {

    @Id
    @GeneratedValue
    @Column(name = "crewinfo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User crewInfoUser;

    @OneToMany(mappedBy = "crewInfoFromComment")
    private List<CrewInfoComment> crewInfoCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "crewInfoFromImage")
    private List<CrewInfoImage> crewInfoImageList = new ArrayList<>();

    private String title;
    private String author;
    private String content;
    private LocalDateTime created_at;
}
