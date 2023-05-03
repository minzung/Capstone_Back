package hansung.capstone.service;

import hansung.capstone.dao.MemberDAO;
import hansung.capstone.dto.MemberDTO;
import hansung.capstone.dto.item.Files;
import hansung.capstone.dto.request.UpdateEmailRequest;
import hansung.capstone.dto.request.UpdateNicknameRequest;
import hansung.capstone.exception.NicknameExistsException;
import hansung.capstone.exception.PasswordNotFoundException;
import hansung.capstone.exception.StudentIdNotFoundException;
import hansung.capstone.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDAO dao;

    private final FileUtil fileUtil;

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
     * @return void
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
     * @return void
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
     * @return void
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
     * @return void
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

        // 파일 저장 경로를 지정합니다.
        String uploadDirectory = "C:/Users/KMJ/Desktop/capstone/src/main/resources/static/certification/";
        String filename = studentId + ".png";
        String fileOriName = file.getOriginalFilename();
        String filePath = uploadDirectory + filename;

        InputStream inputStream = null;
        try {
            // 파일을 지정된 경로에 저장합니다.
            inputStream = file.getInputStream();
            fileUtil.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileUploadException("파일 업로드 중 오류가 발생했습니다.", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 파일 정보를 MemberDTO 객체에 설정하고 저장합니다.
        member.setFiles(new Files(filename, fileOriName, filePath));
        member.setFile(true);
        dao.save(member);
    }


}
