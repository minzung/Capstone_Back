package hansung.capstone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "freeheart")
public class FreeHeartDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name= "studentId")
    private String studentId;

    @Column(name= "boardId")
    private int boardId;

    @Column(name = "isFilled")
    private boolean isFilled;

    public boolean getIsFilled() {
        return isFilled;
    }

    public void setIsFilled(boolean filled) {
        isFilled = filled;
    }

}
