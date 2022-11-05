package com.example.WithRun.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitPost {

    @Id
    @GeneratedValue
    @Column(name = "recruit_post_id")
    private Long id;

    @OneToMany(mappedBy = "recruitMemberRecruitPost")
    private List<RecruitMember> recruitMemberList;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name="recruit_post_id"))
    private List<Tag> tagList = new ArrayList<>();

    private String title;
    private String content;
    private String author;
    private boolean isClosed;
    private int limitNum;
    private boolean isApplied;
    private LocalDateTime created_at;
    private String province;
    private String city;
    private String district;

    public void addTag(Tag tag){
        this.tagList.add(tag);
    }
}
