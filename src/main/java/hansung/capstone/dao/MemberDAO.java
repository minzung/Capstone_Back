package hansung.capstone.dao;

import hansung.capstone.dto.MemberDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDAO extends JpaRepository<MemberDTO, Integer> {

    MemberDTO findByStudentId(String studentId);

    MemberDTO findByNickname(String nickname);

    MemberDTO findIdByEmail(String email);

    @Modifying
    @Query("UPDATE member m SET m.studentCardPath = :filePath WHERE m.studentId = :studentId")
    void updateStudentCardPath(@Param("studentId") String studentId, @Param("filePath") String filePath);

}
