package hansung.capstone.service;

import hansung.capstone.dao.MemberDAO;
import hansung.capstone.dto.MemberDTO;
import hansung.capstone.dto.item.ImageData;
import hansung.capstone.dto.request.UpdateEmailRequest;
import hansung.capstone.dto.request.UpdateNicknameRequest;
import hansung.capstone.exception.NicknameExistsException;
import hansung.capstone.exception.PasswordNotFoundException;
import hansung.capstone.exception.StudentIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDAO dao;

    private final ResourceLoader resourceLoader;

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
     * 인증 성공
     * @param studentId
     */
    public void updateSuccessCertification(String studentId) {
        MemberDTO member = dao.findByStudentId(studentId);

        member.setCertification(true);
        dao.save(member);
    }

    /**
     * 인증 실패
     * @param studentId
     */
    public void updateFailCertification(String studentId) {
        MemberDTO member = dao.findByStudentId(studentId);

        member.setFile(false);
        member.setFileDir(null);
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
    public void uploadStudentCard(String studentId, ImageData imageData) {
        MemberDTO member = dao.findByStudentId(studentId);

        System.out.println("========" + imageData.getImageFile());

        String base64Image = imageData.getImageFile();

        if (base64Image != null && !base64Image.isEmpty()) {
            // 저장할 디렉토리 지정
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/member/";
            // 고유한 파일 이름 생성
            String fileName = member.getStudentId() + ".png";
            Path path = Paths.get(uploadDir + fileName);

            // Base64를 디코딩하여 이미지를 저장
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image.split(",")[1]);

            try {
                Files.write(path, decodedBytes);
                // 파일 저장
                member.setFileDir(path.toString());
                member.setFile(true);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("File saving failed", e);
            }
        }
        dao.save(member);
    }

    public List<MemberDTO> getMemberAll() {
        List<MemberDTO> members = dao.findAll();

        for (MemberDTO member : members) {
            Resource imageResource = getImage(member.getId());

            if (imageResource != null && imageResource.exists()) {
                try {
                    byte[] imageData = StreamUtils.copyToByteArray(imageResource.getInputStream());
                    String base64Image = Base64.getEncoder().encodeToString(imageData);
                    member.setImageFile(base64Image);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to read image data", e);
                }
            }
        }

        return members;
    }

    public Resource getImage(int id) {
        MemberDTO member = dao.findById(id);

        String fileDir = member.getFileDir();

        if (fileDir == null || fileDir.isEmpty()) {
            return null;
        }

        Resource imageResource = resourceLoader.getResource("file:" + fileDir);

        if (!imageResource.exists()) {
            return null;
        }

        return imageResource;
    }

}
