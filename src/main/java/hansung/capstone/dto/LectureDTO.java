package hansung.capstone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "lecture")
public class LectureDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // PK

    @Column(name = "lectureName")
    private String lectureName; // 강의명

    private String professor; // 교수

    @Column(name = "selectedStars")
    private int selectedStars; // 별점

    private String semester; // 학기

    private String homework; // 과제

    private String score; // 학점

    private String test; // 시험 횟수

    private String team; // 팀 수업

    private String content; // 내용

}
