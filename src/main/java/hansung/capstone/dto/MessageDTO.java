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

    @ManyToOne
    @JoinColumn(name = "room")
    private MessageRoomDTO room; // 메세지 방번호

    private String sender; // 보내는 사람

    private String receiver; // 받는 사람

    private String content; // 내용

    @Column(name = "sendTime")
    private Timestamp sendTime; // 보낸 시간

    @Column(name = "readTime")
    private Timestamp readTime; // 읽은 시간

    @Column(name = "readCheck")
    private boolean readCheck;

    @PrePersist
    public void onCreate() {
        this.sendTime = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

}
