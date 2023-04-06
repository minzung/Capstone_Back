package hansung.capstone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@Entity(name = "member")
public class MemberDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private int memberId; // PK

    @Column(name = "studentId")
    private String studentId; // 학번

    private String password; // 비밀번호

    @Email
    private String email; // 이메일

    private String username; // 이름

    private String nickname; // 닉네임

    @Column(name = "isCertification")
    private boolean isCertification; // 재학생 인증

    @Override
    public String toString() {
        return "MemberDTO{" +
                "memberId=" + memberId +
                ", studentId='" + studentId + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", isCertification='" + isCertification +
                '}';
    }

}
