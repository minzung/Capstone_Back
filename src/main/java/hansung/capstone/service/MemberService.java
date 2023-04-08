package hansung.capstone.service;

import hansung.capstone.dao.MemberDAO;
import hansung.capstone.dto.MemberDTO;
import hansung.capstone.exception.NicknameExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDAO dao;

    private final PasswordEncoder passwordEncoder;

    public MemberDTO getMemberByStudentId(String studentId) {
        MemberDTO member = dao.findByStudentId(studentId);

        if(member == null)
            throw new IllegalArgumentException("존재하지 않는 학번입니다.");

        return dao.findByStudentId(studentId);
    }

    public void updateNickname(String studentId, String newNickname) throws NicknameExistsException {
        MemberDTO member = dao.findByStudentId(studentId);

        if(member == null)
            throw new IllegalArgumentException("존재하지 않는 학번입니다.");

        if(Objects.equals(member.getNickname(), newNickname))
            throw new NicknameExistsException("이미 사용중인 닉네임입니다.");

        member.setNickname(newNickname);
        dao.save(member);
    }

    public void updatePassword(String studentId, String newPassword) {
        MemberDTO member = dao.findByStudentId(studentId);

        if(member == null)
            throw new IllegalArgumentException("존재하지 않는 학번입니다.");

        // 비밀번호 암호화
        member.setPassword(passwordEncoder.encode(newPassword));
        dao.save(member);
    }

    public void updateEmail(String studentId, String newEmail) {
        MemberDTO member = dao.findByStudentId(studentId);

        if(member == null)
            throw new IllegalArgumentException("존재하지 않는 학번입니다.");

        // 비밀번호 암호화
        member.setEmail(newEmail);
        dao.save(member);
    }

}