package hansung.capstone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "time")
public class TimeDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // PK

    @Column(name = "studentId")
    private String studentId; // 작성자

    private String subject; // 과목명

    private String professor; // 교수명

    private String room; // 강의실

    private String day; // 요일

    @Column(name = "startTime")
    private Timestamp startTime; // 시작시간

    @Column(name = "endTime")
    private Timestamp endTime; // 종료시간

}
