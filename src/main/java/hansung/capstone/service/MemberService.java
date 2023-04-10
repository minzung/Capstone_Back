package hansung.capstone.service;

import hansung.capstone.dao.MemberDAO;
import hansung.capstone.dto.MemberDTO;
import hansung.capstone.dto.request.UpdateEmailRequest;
import hansung.capstone.dto.request.UpdateNicknameRequest;
import hansung.capstone.exception.NicknameExistsException;
import hansung.capstone.exception.PasswordNotFoundException;
import hansung.capstone.exception.StudentIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDAO dao;

    private final PasswordEncoder passwordEncoder;

    @Value("${file.upload-dir}")
    private String fileUploadDir;

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

    /**
     * 학생증 사진 업로드
     * @param studentId, file
     * @return void
     */
    @Transactional
    public void uploadStudentCard(String studentId, MultipartFile file) throws StudentIdNotFoundException, FileUploadException {
        MemberDTO member = dao.findByStudentId(studentId);

        if (member == null)
            throw new StudentIdNotFoundException("존재하지 않는 학번입니다.");

        String filePath = saveFile(file, studentId);

        dao.updateStudentCardPath(studentId, filePath);
    }

    private String saveFile(MultipartFile file, String studentId) throws FileUploadException {
        // 파일 저장 경로와 파일 이름 지정
        Path targetLocation = Paths.get(fileUploadDir, studentId+".png");

        try {
            Files.createDirectories(targetLocation.getParent()); // 디렉토리가 없으면 생성
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return targetLocation.toString();
        } catch (IOException e) {
            throw new FileUploadException("파일 업로드에 실패했습니다.", e);
        }
    }

}
