package com.example.WithRun.crewinfo.domain;

import com.example.WithRun.common.domain.BaseTimeDomain;
import com.example.WithRun.crewinfo.dto.CrewInfoDTO;
import com.example.WithRun.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrewInfo extends BaseTimeDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String latitude;
    private String longitude;

    //================================================//

    public void addCrewInfoImageList(CrewInfoImage crewInfoImage){
        crewInfoImageList.add(crewInfoImage);
        crewInfoImage.setCrewInfoFromImage(this);
    }

    public void deleteCrewInfoImageList(){
        for(CrewInfoImage crewInfoImage : crewInfoImageList){
            crewInfoImageList.remove(crewInfoImage);
        }
    }

    public void addCrewInfoCommentList(CrewInfoComment crewInfoComment){
        crewInfoCommentList.add(crewInfoComment);
        crewInfoComment.setCrewInfoFromComment(this);
    }


    public CrewInfoDTO toDto(){
        return CrewInfoDTO.builder()
                .id(id).title(title)
                .author(author)
                .content(content)
                .latitude(latitude)
                .longitude(longitude).build();
    }

}
