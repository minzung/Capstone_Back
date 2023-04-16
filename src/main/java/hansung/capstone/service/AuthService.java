package hansung.capstone.service;

import hansung.capstone.dao.MemberDAO;
import hansung.capstone.dto.MemberDTO;
import hansung.capstone.dto.request.LoginRequest;
import hansung.capstone.exception.EmailExistsException;
import hansung.capstone.exception.NicknameExistsException;
import hansung.capstone.exception.StudentIdExistsException;
import hansung.capstone.exception.StudentIdNotFoundException;
import hansung.capstone.dto.item.AuthResponse;
import hansung.capstone.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberDAO dao;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public MemberDTO register(MemberDTO memberDTO) throws StudentIdExistsException, NicknameExistsException, EmailExistsException {
        MemberDTO member = dao.findByStudentId(memberDTO.getStudentId());

        if (member != null) {
            throw new StudentIdExistsException("이미 가입된 학번입니다.");
        }

        if (dao.findByNickname(memberDTO.getNickname()) != null) {
            throw new NicknameExistsException("이미 사용중인 닉네임입니다.");
        }

        if (dao.findByEmail(memberDTO.getEmail()) != null) {
            throw new EmailExistsException("이미 가입된 이메일입니다.");
        }

        // 비밀번호 암호화
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));

        return dao.save(memberDTO);
    }

    public AuthResponse login(LoginRequest loginRequest) throws AuthenticationException {
        String studentId = loginRequest.getStudentId();
        String password = loginRequest.getPassword();

        MemberDTO member = dao.findByStudentId(studentId);
        if (member == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("비밀번호를 다시 확인해주세요");
        }

        String accessToken = jwtUtil.createAccessToken(studentId);
        String refreshToken = jwtUtil.createRefreshToken(studentId);

        member.setAccessToken(accessToken);
        member.setRefreshToken(refreshToken);
        dao.save(member);

        return new AuthResponse(accessToken, refreshToken);
    }

    public String refreshAccessToken(String refreshToken) {
        return jwtUtil.refreshToken(refreshToken);
    }

    public String findIdByEmail(String email) throws StudentIdNotFoundException {
        MemberDTO member = dao.findIdByEmail(email);

        if(member == null)
            throw new StudentIdNotFoundException("존재하지 않는 학번입니다.");

        return member.getStudentId();
    }

}
