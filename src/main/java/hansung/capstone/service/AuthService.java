package hansung.capstone.service;

import hansung.capstone.dao.MemberDAO;
import hansung.capstone.dto.MemberDTO;
import hansung.capstone.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberDAO dao;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public String login(String studentId, String password) {
        MemberDTO member = dao.findByStudentId(studentId);

        if (member != null && passwordEncoder.matches(password, member.getPassword())) {
            return jwtUtil.createToken(studentId);
        } else {
            return null;
        }
    }
}

