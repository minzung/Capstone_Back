package hansung.capstone.service;

import hansung.capstone.dao.MemberDAO;
import hansung.capstone.dto.MemberDTO;
import hansung.capstone.dto.request.UpdateEmailRequest;
import hansung.capstone.dto.request.UpdateNicknameRequest;
import hansung.capstone.exception.NicknameExistsException;
import hansung.capstone.exception.PasswordNotFoundException;
import hansung.capstone.exception.StudentIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDAO dao;

    private final PasswordEncoder passwordEncoder;

    public MemberDTO getMemberByStudentId(String studentId) throws StudentIdNotFoundException {
        MemberDTO member = dao.findByStudentId(studentId);

        if(member == null)
            throw new StudentIdNotFoundException("존재하지 않는 학번입니다.");

        return dao.findByStudentId(studentId);
    }

    /**
     * 닉네임 수정
     * @param studentId, newNickname
     * @return ?
     */
    public void updateNickname(String studentId, UpdateNicknameRequest updateNicknameRequest) throws NicknameExistsException, StudentIdNotFoundException {
        MemberDTO member = dao.findByStudentId(studentId);
        String newNickname = updateNicknameRequest.getNickname();

        if(member == null)
            throw new StudentIdNotFoundException("존재하지 않는 학번입니다.");

        if(Objects.equals(member.getNickname(), newNickname))
            throw new NicknameExistsException("이미 사용중인 닉네임입니다.");

        member.setNickname(newNickname);
        dao.save(member);
    }

    /**
     * 비밀번호 변경
     * @param studentId, newPassword
     * @return ?
     */
    public void updatePassword(String studentId, String newPassword) throws StudentIdNotFoundException {
        MemberDTO member = dao.findByStudentId(studentId);

        if(member == null)
            throw new StudentIdNotFoundException("존재하지 않는 학번입니다.");

        // 비밀번호 암호화
        member.setPassword(passwordEncoder.encode(newPassword));
        dao.save(member);
    }

    /**
     * 이메일 변경
     * @param studentId, updateEmailRequest
     * @return ?
     */
    public void updateEmail(String studentId, UpdateEmailRequest updateEmailRequest) throws PasswordNotFoundException, StudentIdNotFoundException {
        MemberDTO member = dao.findByStudentId(studentId);

        if(member == null)
            throw new StudentIdNotFoundException("존재하지 않는 학번입니다.");

        // 비밀번호 검증
        if (!passwordEncoder.matches(updateEmailRequest.getPassword(), member.getPassword()))
            throw new PasswordNotFoundException("비밀번호를 다시 확인해주세요");

        member.setEmail(updateEmailRequest.getEmail());
        dao.save(member);
    }

    /**
     * 회원탈퇴
     * @param studentId
     * @return ?
     */
    public void deleteMember(String studentId, String password) throws StudentIdNotFoundException, PasswordNotFoundException {
        MemberDTO member = dao.findByStudentId(studentId);

        if (member == null)
            throw new StudentIdNotFoundException("존재하지 않는 학번입니다.");

        if (!passwordEncoder.matches(password, member.getPassword()))
            throw new PasswordNotFoundException("비밀번호를 다시 확인해주세요");

        dao.delete(member);
    }

}