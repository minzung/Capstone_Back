package hansung.capstone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "freecomment")
public class FreeCommentDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // PK

    @Column(name = "studentId")
    private String studentId; // 작성자

    private String nickname; // 닉네임

    @Column(name = "boardId")
    private int boardId; // 게시판 ID

    private String content; // 댓글 내용

    private boolean anonymous; // 익명여부

    @Column(name = "createdAt")
    private Timestamp createdAt; // 동록일

    @Column(name = "updatedAt")
    private Timestamp updatedAt; // 수정일

    @Column(name = "parentId")
    private int parentId;

    @PrePersist
    public void onCreate() {
        this.createdAt = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    public boolean getAnonymous() {
        return anonymous;
    }

}