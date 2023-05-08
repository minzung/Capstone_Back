package hansung.capstone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "book")
public class BookDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // PK

    @Column(name = "studentId")
    private String studentId; // 판매자

    @Column(name = "lectureName")
    private String lectureName; // 강의명

    @Column(name = "bookName")
    private String bookName; // 책이름

    private String author; // 작가

    private String publisher; // 출판사

    private String semester; // 학기

    private String writing; // 필기 흔적

    private String state; // 책 상태

    private String broken; // 책 훼손

    private String fileDir; // 이미지 저장 경로

    @Transient
    private String imageFile; // 책 사진

    private String content; // 책정보

}
