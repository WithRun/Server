package com.example.WithRun.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitMember {

    @Id
    @GeneratedValue
    @Column(name = "recruit_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User recruitMemberUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private RecruitPost recruitMemberRecruitPost;


}
