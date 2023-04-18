package hansung.capstone.service;

import hansung.capstone.dao.CommentDAO;
import hansung.capstone.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDAO dao;

    public CommentDTO saveComment(CommentDTO comment) {
        return dao.save(comment);
    }

    public CommentDTO getCommentById(int id) {
        return dao.findById(id).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
    }

    public CommentDTO updateComment(int id, CommentDTO newComment) {
        CommentDTO oldComment = getCommentById(id);
        oldComment.setContent(newComment.getContent());
        return dao.save(oldComment);
    }

    public List<CommentDTO> getAllCommentsByBoardId(int boardId) {
        return dao.findAllByBoardId(boardId);
    }

    public void deleteComment(int id) {
        dao.deleteById(id);
    }

}
