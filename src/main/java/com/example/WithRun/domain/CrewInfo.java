package com.example.WithRun.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @OneToMany(mappedBy = "crewInfoComment")
    private List<CrewInfoComment> crewInfoCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "crewInfoImage")
    private List<CrewInfoImage> crewInfoImageList = new ArrayList<>();

    private String title;
    private String author;
    private String content;
    private LocalDateTime created_at;
}
