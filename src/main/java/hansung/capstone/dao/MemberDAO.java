package hansung.capstone.dao;

import hansung.capstone.dto.MemberDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDAO extends JpaRepository<MemberDTO, Integer> {

    MemberDTO findByStudentId(String studentId);

    boolean existsByStudentId(String studentId);
}
