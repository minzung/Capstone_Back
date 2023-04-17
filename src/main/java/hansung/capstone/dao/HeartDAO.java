package hansung.capstone.dao;

import hansung.capstone.dto.HeartDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartDAO extends JpaRepository<HeartDTO, Integer> {

    @Query("SELECT h " + "FROM heart h " + "WHERE h.studentId = :studentId " + "AND h.boardId = :boardId")
    HeartDTO findByMemberAndBoard(String studentId, int boardId);

}
