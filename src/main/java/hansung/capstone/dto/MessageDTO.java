package hansung.capstone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@Entity(name = "message")
public class MessageDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // PK

    private String sender; // 보내는 사람

    private String receiver; // 받는 사람

    private String content; // 내용

    @Column(name = "createdAt")
    private Timestamp timestamp; // 보낸 시간

    @PrePersist
    public void onCreate() {
        this.timestamp = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

}
