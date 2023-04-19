package hansung.capstone.dao;

import hansung.capstone.dto.CommentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDAO extends JpaRepository<CommentDTO, Integer> {

    @Query("SELECT c FROM comment c WHERE c.boardId = :boardId")
    List<CommentDTO> findAllByBoardId(@Param("boardId") int boardId);

}