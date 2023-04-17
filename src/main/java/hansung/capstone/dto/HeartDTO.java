package hansung.capstone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity(name = "heart")
public class HeartDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heartId")
    private int heartId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "memberId")
    private MemberDTO member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "freeboardId")
    private FreeBoardDTO board;

    @Column(name = "isFilled")
    private boolean isFilled;

    public boolean getIsFilled() {
        return isFilled;
    }

    public void setIsFilled(boolean filled) {
        isFilled = filled;
    }

}
