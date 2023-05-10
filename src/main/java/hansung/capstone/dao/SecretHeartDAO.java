package hansung.capstone.dao;

import hansung.capstone.dto.SecretHeartDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretHeartDAO extends JpaRepository<SecretHeartDTO, Integer> {

    @Query("SELECT h FROM secretheart h WHERE h.studentId = :studentId AND h.boardId = :boardId")
    SecretHeartDTO findByMemberAndBoard(String studentId, int boardId);

}
