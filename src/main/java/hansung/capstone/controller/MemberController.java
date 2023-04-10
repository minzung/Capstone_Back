package hansung.capstone.controller;

import hansung.capstone.dto.MemberDTO;
import hansung.capstone.dto.request.UpdateEmailRequest;
import hansung.capstone.dto.request.UpdateNicknameRequest;
import hansung.capstone.dto.request.UpdatePasswordRequest;
import hansung.capstone.exception.NicknameExistsException;
import hansung.capstone.exception.PasswordNotFoundException;
import hansung.capstone.exception.StudentIdNotFoundException;
import hansung.capstone.jwt.JwtUtil;
import hansung.capstone.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {

    private final MemberService memberService;

    private final JwtUtil jwtUtil;

    /**
     * studentId로 정보 얻기
     * @param studentId
     * @return MemberDTO
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable("studentId") String studentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 인증된 사용자의 정보를 가져오기
        if (authentication == null || !studentId.equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

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

}