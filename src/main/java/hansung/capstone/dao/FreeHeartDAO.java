package hansung.capstone.dao;

import hansung.capstone.dto.FreeHeartDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeHeartDAO extends JpaRepository<FreeHeartDTO, Integer> {

    @Query("SELECT h FROM freeheart h WHERE h.studentId = :studentId AND h.boardId = :boardId")
    FreeHeartDTO findByMemberAndBoard(String studentId, int boardId);

}
