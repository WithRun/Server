package com.example.WithRun.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    private String title;
    private String content;
    private String author;
    private Tag tag1;
    private Tag tag2;
    private Tag tag3;
    private boolean isClosed;
    private int limitNum;
    private boolean isApplied;
    private LocalDateTime created_at;
    private String province;
    private String city;
    private String district;
}
