package hansung.capstone.controller;


import hansung.capstone.dto.MemberDTO;
import hansung.capstone.dto.request.LoginRequest;
import hansung.capstone.exception.NicknameExistsException;
import hansung.capstone.exception.StudentIdExistsException;
import hansung.capstone.jwt.AuthResponse;
import hansung.capstone.jwt.JwtUtil;
import hansung.capstone.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final JwtUtil jwtUtil;

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberDTO memberDTO) {
        try {
            authService.register(memberDTO);
            return ResponseEntity.status(200).body("회원가입 성공!");
        } catch (StudentIdExistsException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (NicknameExistsException e) {
            return ResponseEntity.status(402).body(e.getMessage());
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
            return ResponseEntity.ok(newAccessToken);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
