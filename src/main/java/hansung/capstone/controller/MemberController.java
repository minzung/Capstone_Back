package hansung.capstone.controller;

import hansung.capstone.dto.MemberDTO;
import hansung.capstone.exception.NicknameExistsException;
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
@CrossOrigin(origins = "*", allowedHeaders = "Authorization")
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
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * 닉네임 수정
     * @param studentId, newNickname
     * @return ?
     */
    @PatchMapping("/{studentId}/nickname")
    public ResponseEntity<?> updateNickname(@PathVariable("studentId") String studentId, @RequestBody String newNickname) {
        try {
            memberService.updateNickname(studentId, newNickname);
            return ResponseEntity.ok(memberService.getMemberByStudentId(studentId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NicknameExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * 비밀번호 변경
     * @param studentId, newPassword
     * @return ?
     */
    @PatchMapping("/{studentId}/password")
    public ResponseEntity<?> updatePassword(@PathVariable("studentId") String studentId, @RequestBody String newPassword) {
        try {
            memberService.updatePassword(studentId, newPassword);
            return ResponseEntity.ok(memberService.getMemberByStudentId(studentId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * 이메일 변경
     * @param studentId, newEmail
     * @return ?
     */
    @PatchMapping("/{studentId}/email")
    public ResponseEntity<?> updateEmail(@PathVariable("studentId") String studentId, @RequestBody String newEmail) {
        try {
            memberService.updateEmail(studentId, newEmail);
            return ResponseEntity.ok(memberService.getMemberByStudentId(studentId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}