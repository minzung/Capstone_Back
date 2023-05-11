package hansung.capstone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "room")
public class MessageRoomDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // PK

    private String sender; // 보내는 사람

    private String receiver; // 받는 사람

}
