package hansung.capstone.dto;

import hansung.capstone.dto.item.Files;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "member")
public class MemberDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // PK

    @Column(name = "studentId")
    private String studentId; // 학번

    private String password; // 비밀번호

    private String email; // 이메일

    private String username; // 이름

    private String nickname; // 닉네임

    @Column(name = "refreshToken")
    private String refreshToken; // refresh 토큰

    @Column(name = "accessToken")
    private String accessToken; // access 토큰

    @Embedded
    private Files files; // 인증

    @Column(name = "isFile")
    private boolean isFile; // 재학생 인증 파일 제출 여부

    @Column(name = "isCertification")
    private boolean isCertification; // 재학생 인증

}
