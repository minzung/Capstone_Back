package hansung.capstone.dao;

import hansung.capstone.dto.CommentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDAO extends JpaRepository<CommentDTO, Integer> {

    List<CommentDTO> findAllByBoardId(int boardId);

}