package hansung.capstone.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Getter
@Setter
@Entity(name = "freeboard")
public class FreeBoardDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // PK

    @Column(name = "studentId")
    private String studentId; // 작성자

    private String nickname; // 작성자 닉네임

    private String title; // 제목

    private String content; // 내용

    private boolean anonymous; // 익명여부

    @Column(name = "createdAt")
    private Timestamp createdAt; // 동록일

    @Column(name = "updatedAt")
    private Timestamp updatedAt; // 수정일

    @ColumnDefault("0")
    @Column(name = "countLike")
    private int countLike; // 좋아요 수

    @ColumnDefault("0")
    @Column(name = "countComment")
    private int countComment; // 댓글 수

    @Column(name = "fileDir")
    private String fileDir;

    @Transient
    private String imageFile;

    @PrePersist
    public void onCreate() {
        this.createdAt = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

}
