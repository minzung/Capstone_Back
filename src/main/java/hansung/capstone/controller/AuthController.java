package hansung.capstone.controller;

import hansung.capstone.dto.MemberDTO;
import hansung.capstone.dto.request.LoginRequest;
import hansung.capstone.dto.request.UpdatePasswordRequest;
import hansung.capstone.exception.EmailExistsException;
import hansung.capstone.exception.NicknameExistsException;
import hansung.capstone.exception.StudentIdExistsException;
import hansung.capstone.exception.StudentIdNotFoundException;
import hansung.capstone.dto.item.AuthResponse;
import hansung.capstone.service.AuthService;
import hansung.capstone.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberDTO memberDTO) {
        try {
            authService.register(memberDTO);
            return ResponseEntity.status(200).body("회원가입 성공!");
        } catch (StudentIdExistsException | NicknameExistsException | EmailExistsException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse token = authService.login(loginRequest);
            return ResponseEntity.ok(new AuthResponse(token.getAccessToken(), token.getRefreshToken()));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestParam("refresh_token") String refreshToken) {
        try {
            String newAccessToken = authService.refreshAccessToken(refreshToken);
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/email")
    public ResponseEntity<?> findIdByEmail(@RequestParam("email") String email) {
        try {
            return ResponseEntity.ok(authService.findIdByEmail(email));
        } catch (StudentIdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * 비밀번호 변경
     * @param studentId, newPassword
     * @return ?
     */
    @PatchMapping("/{studentId}/password")
    public ResponseEntity<?> updatePassword(@PathVariable("studentId") String studentId, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        try {
            memberService.updatePassword(studentId, updatePasswordRequest.getPassword());
            return ResponseEntity.ok(memberService.getMemberByStudentId(studentId));
        } catch (StudentIdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * 인증 성공
     * @param studentId
     * @return ?
     */
    @PatchMapping("/{studentId}/certification/success")
    public ResponseEntity<?> updateSuccessCertification(@PathVariable("studentId") String studentId) {
        try {
            memberService.updateSuccessCertification(studentId);
            return ResponseEntity.ok(memberService.getMemberByStudentId(studentId));
        } catch (StudentIdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * 인증 실패
     * @param studentId
     * @return ?
     */
    @PatchMapping("/{studentId}/certification/fail")
    public ResponseEntity<?> updateCertification(@PathVariable("studentId") String studentId) {
        try {
            memberService.updateFailCertification(studentId);
            return ResponseEntity.ok(memberService.updateFailCertification(studentId));
        } catch (StudentIdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

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

    @GetMapping("/all")
    public ResponseEntity<List<MemberDTO>> getMemberAll() {
        return ResponseEntity.ok(memberService.getMemberAll());
    }

}
