package hansung.capstone.service;

import hansung.capstone.dao.FreeCommentDAO;
import hansung.capstone.dao.FreeBoardDAO;
import hansung.capstone.dao.MemberDAO;
import hansung.capstone.dto.FreeBoardDTO;
import hansung.capstone.dto.FreeCommentDTO;
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

    private final FreeCommentDAO commentDAO;

    public FreeCommentDTO saveComment(int boardId , FreeCommentDTO comment) {
        MemberDTO student = memberDAO.findByStudentId(comment.getStudentId());
        FreeBoardDTO board = freeBoardDAO.findById(boardId);

        comment.setBoardId(boardId);
        comment.setNickname(student.getNickname());
        comment.setIsAnonymous(comment.getIsAnonymous());

        board.setCountComment(board.getCountComment() + 1);
        freeBoardDAO.save(board);

        return commentDAO.save(comment);
    }

    public void deleteComment(int boardId, int id) {
        FreeBoardDTO board = freeBoardDAO.findById(boardId);
        board.setCountComment(board.getCountComment() - 1);
        freeBoardDAO.save(board);

        commentDAO.deleteById(id);
    }

    public List<FreeCommentDTO> getAllCommentsByBoardId(int boardId) {
        return commentDAO.findAllByBoardId(boardId);
    }

    public FreeCommentDTO createReply(int parentId, FreeCommentDTO reply) {
        Optional<FreeCommentDTO> parentComment = commentDAO.findById(parentId);
        if (parentComment.isPresent()) {
            FreeCommentDTO parent = parentComment.get();
            reply.setParent(parent);
            return commentDAO.save(reply);
        } else {
            return null;
        }
    }

    public boolean deleteReply(int commentId) {
        Optional<FreeCommentDTO> existingComment = commentDAO.findById(commentId);
        if (existingComment.isPresent()) {
            commentDAO.deleteById(commentId);
            return true;
        } else {
            return false;
        }
    }

}
