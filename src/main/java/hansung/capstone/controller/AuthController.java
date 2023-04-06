package hansung.capstone.controller;


import hansung.capstone.dao.MemberDAO;
import hansung.capstone.dto.MemberDTO;
import hansung.capstone.dto.request.LoginRequest;
import hansung.capstone.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    private final MemberDAO dao;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<MemberDTO> signUp(@RequestBody MemberDTO memberDTO) {
        // Check if studentId already exists
        if (dao.existsByStudentId(memberDTO.getStudentId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Encode password and save the user
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        MemberDTO savedMember = dao.save(memberDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String jwtToken = authService.login(loginRequest.getStudentId(), loginRequest.getPassword());

        if (jwtToken != null) {
            return ResponseEntity.ok(jwtToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
