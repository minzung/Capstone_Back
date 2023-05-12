package hansung.capstone.controller;

import hansung.capstone.dto.MemberDTO;
import hansung.capstone.dto.request.UpdateEmailRequest;
import hansung.capstone.dto.request.UpdateNicknameRequest;
import hansung.capstone.exception.NicknameExistsException;
import hansung.capstone.exception.PasswordNotFoundException;
import hansung.capstone.exception.StudentIdNotFoundException;
import hansung.capstone.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {

    private final MemberService memberService;

    /**
     * studentId로 정보 얻기
     * @param studentId
     * @return MemberDTO
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable("studentId") String studentId) {
        try {
            return ResponseEntity.ok(memberService.getMemberByStudentId(studentId));
        } catch (StudentIdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * 닉네임 수정
     * @param studentId, newNickname
     * @return ?
     */
    @PatchMapping("/{studentId}/nickname")
    public ResponseEntity<?> updateNickname(@PathVariable("studentId") String studentId, @RequestBody UpdateNicknameRequest updateNicknameRequest) {
        try {
            memberService.updateNickname(studentId, updateNicknameRequest);
            return ResponseEntity.ok(memberService.getMemberByStudentId(studentId));
        } catch (StudentIdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NicknameExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * 이메일 변경
     * @param studentId, updateEmailRequest
     * @return ?
     */
    @PatchMapping("/{studentId}/email")
    public ResponseEntity<?> updateEmail(@PathVariable("studentId") String studentId, @RequestBody UpdateEmailRequest updateEmailRequest) {
        try {
            memberService.updateEmail(studentId, updateEmailRequest);
            return ResponseEntity.ok(memberService.getMemberByStudentId(studentId));
        } catch (StudentIdNotFoundException | PasswordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * 회원탈퇴
     * @param studentId
     * @return ?
     */
    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteMember(@PathVariable("studentId") String studentId, @RequestParam String password) {
        try {
            memberService.deleteMember(studentId, password);
            return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
        } catch (StudentIdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PasswordNotFoundException e) {
            return ResponseEntity.status(402).body(e.getMessage());
        }
    }

    /**
     * 학생증 업로드
     * @param studentId
     * @return ?
     */
    @PostMapping("/{studentId}/uploadStudentCard")
    public ResponseEntity<?> uploadStudentCard(@PathVariable("studentId") String studentId, @RequestParam("file") MultipartFile file) {
        try {
            memberService.uploadStudentCard(studentId, file);
            return ResponseEntity.ok("학생증 사진이 업로드되었습니다.");
        } catch (StudentIdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        }
    }

}