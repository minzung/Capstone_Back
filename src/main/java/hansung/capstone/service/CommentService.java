package hansung.capstone.service;

import hansung.capstone.dao.CommentDAO;
import hansung.capstone.dao.FreeBoardDAO;
import hansung.capstone.dao.MemberDAO;
import hansung.capstone.dto.CommentDTO;
import hansung.capstone.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberDAO memberDAO;

    private final FreeBoardDAO freeBoardDAO;

    private final CommentDAO commentDAO;

    public CommentDTO saveComment(int id ,CommentDTO comment) {
        MemberDTO student = memberDAO.findByStudentId(comment.getStudentId());

        comment.setBoardId(id);
        comment.setNickname(student.getNickname());
        comment.setIsAnonymous(comment.getIsAnonymous());

        return commentDAO.save(comment);
    }

    public CommentDTO getCommentById(int id) {
        return commentDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
    }

    public CommentDTO updateComment(int id, CommentDTO newComment) {
        CommentDTO oldComment = getCommentById(id);
        oldComment.setContent(newComment.getContent());
        return commentDAO.save(oldComment);
    }

    public void deleteComment(int id) {
        commentDAO.deleteById(id);
    }

    public List<CommentDTO> getAllCommentsByBoardId(int boardId) {
        return commentDAO.findAllByBoardId(boardId);
    }

    public CommentDTO createReply(int parentId, CommentDTO reply) {
        Optional<CommentDTO> parentComment = commentDAO.findById(parentId);
        if (parentComment.isPresent()) {
            CommentDTO parent = parentComment.get();
            reply.setParent(parent);
            return commentDAO.save(reply);
        } else {
            return null;
        }
    }

    public CommentDTO updateReply(int commentId, CommentDTO updatedComment) {
        Optional<CommentDTO> existingComment = commentDAO.findById(commentId);
        if (existingComment.isPresent()) {
            CommentDTO comment = existingComment.get();
            comment.setStudentId(updatedComment.getStudentId());
            comment.setNickname(updatedComment.getNickname());
            comment.setContent(updatedComment.getContent());
            comment.setIsAnonymous(updatedComment.getIsAnonymous());
            return commentDAO.save(comment);
        } else {
            return null;
        }
    }

    public boolean deleteReply(int commentId) {
        Optional<CommentDTO> existingComment = commentDAO.findById(commentId);
        if (existingComment.isPresent()) {
            commentDAO.deleteById(commentId);
            return true;
        } else {
            return false;
        }
    }

}
