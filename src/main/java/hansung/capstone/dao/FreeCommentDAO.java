package hansung.capstone.dao;

import hansung.capstone.dto.FreeCommentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeCommentDAO extends JpaRepository<FreeCommentDTO, Integer> {

    @Query("SELECT c FROM freecomment c WHERE c.boardId = :boardId")
    List<FreeCommentDTO> findAllByBoardId(@Param("boardId") int boardId);

    @Query("SELECT c FROM freecomment c WHERE c.parentId = :parentId")
    List<FreeCommentDTO> findAllByParentId(int parentId);

    @Query("DELETE FROM freecomment c WHERE c.id = :id OR c.parentId = :id")
    void deleteAllCommentById(int id);

    void deleteByParentId(int id);
}