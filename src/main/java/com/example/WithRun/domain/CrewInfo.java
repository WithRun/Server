package com.example.WithRun.domain;

import com.example.WithRun.controller.CrewInfoController;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "json_id")
public class CrewInfo extends BaseTimeDomain{

    @Id
    @GeneratedValue
    @Column(name = "crewinfo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User crewInfoUser;

    @OneToMany(mappedBy = "crewInfoFromComment")
    @Builder.Default
    private List<CrewInfoComment> crewInfoCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "crewInfoFromImage")
    @Builder.Default
    private List<CrewInfoImage> crewInfoImageList = new ArrayList<>();

    private String title;
    private String author;
    private String content;

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

}
