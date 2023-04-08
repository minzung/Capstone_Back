package hansung.capstone.controller;

import hansung.capstone.dto.MemberDTO;
import hansung.capstone.exception.NicknameExistsException;
import hansung.capstone.jwt.JwtUtil;
import hansung.capstone.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/member")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {

    private final MemberService memberService;

    private final JwtUtil jwtUtil;

    @GetMapping("/{studentId}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable("studentId") String studentId, @RequestHeader(value = "Authorization") String accessToken) {
        if (!jwtUtil.validateToken(accessToken)) { // access token 유효성 검증
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        try {
            return ResponseEntity.ok(memberService.getMemberByStudentId(studentId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/{studentId}/nickname")
    public ResponseEntity<String> updateNickname(@PathVariable("studentId") String studentId, @RequestBody String newNickname, @RequestHeader(value = "Authorization") String accessToken) {
        if (!jwtUtil.validateToken(accessToken)) { // access token 유효성 검증
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        try {
            memberService.updateNickname(studentId, newNickname);
            return ResponseEntity.status(HttpStatus.OK).body("닉네임 수정 성공!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NicknameExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PatchMapping("/{studentId}/password")
    public ResponseEntity<String> updatePassword(@PathVariable("studentId") String studentId, @RequestBody String newPassword, @RequestHeader(value = "Authorization") String accessToken) {
        if (!jwtUtil.validateToken(accessToken)) { // access token 유효성 검증
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        try {
            memberService.updatePassword(studentId, newPassword);
            return ResponseEntity.status(HttpStatus.OK).body("비밀번호 수정 성공!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{studentId}/email")
    public ResponseEntity<String> updateEmail(@PathVariable("studentId") String studentId, @RequestBody String newEmail, @RequestHeader(value = "Authorization") String accessToken) {
        if (!jwtUtil.validateToken(accessToken)) { // access token 유효성 검증
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        try {
            memberService.updateEmail(studentId, newEmail);
            return ResponseEntity.status(HttpStatus.OK).body("이메일 수정 성공!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}