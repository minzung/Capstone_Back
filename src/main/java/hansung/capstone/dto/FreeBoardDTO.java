package hansung.capstone.dto;

import hansung.capstone.dto.item.Files;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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

    private String studentId; // 작성자

    private String title; // 제목

    private String content; // 내용

    @Column(name = "isAnonymous")
    private boolean isAnonymous; // 익명여부

    @Column(name = "createdAt")
    private Timestamp createdAt; // 동록일

    @Column(name = "updatedAt")
    private Timestamp updatedAt; // 수정일

    @Embedded
    private Files files; // 파일

    @Transient // 데이터베이스에 저장되지 않도록 설정
    private MultipartFile imageFile;

    @PrePersist
    public void onCreate() {
        this.createdAt = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    public boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

}
