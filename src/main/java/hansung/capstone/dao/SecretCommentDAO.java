package hansung.capstone.dao;

import hansung.capstone.dto.FreeCommentDTO;
import hansung.capstone.dto.SecretCommentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecretCommentDAO extends JpaRepository<SecretCommentDTO, Integer> {

    @Query("SELECT c FROM secretcomment c WHERE c.boardId = :boardId")
    List<SecretCommentDTO> findAllByBoardId(@Param("boardId") int boardId);

    @Query("SELECT c FROM secretcomment c WHERE c.parentId = :parentId")
    List<SecretCommentDTO> findAllByParentId(int parentId);

    void deleteByParentId(int id);

    void deleteByBoardId(int id);

    int countByParentId(int id);

}