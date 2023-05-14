package hansung.capstone.dao;

import hansung.capstone.dto.MemberDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDAO extends JpaRepository<MemberDTO, Integer> {

    MemberDTO findById(int id);

    MemberDTO findByStudentId(String studentId);

    MemberDTO findByNickname(String nickname);

    MemberDTO findIdByEmail(String email);

    String findByEmail(String email);

}
