package hansung.capstone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "comment")
public class CommentDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // PK

    @Column(name = "studentId")
    private String studentId; // 작성자

    private String nickname; // 닉네임

    @Column(name = "boardId")
    private int boardId; // 게시판 ID

    private String content; // 댓글 내용

    @Column(name = "isAnonymous")
    private boolean isAnonymous; // 익명여부

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CommentDTO parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentDTO> replies = new ArrayList<>();

}