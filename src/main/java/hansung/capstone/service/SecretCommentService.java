package hansung.capstone.service;

import hansung.capstone.dao.*;
import hansung.capstone.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecretCommentService {

    private final MemberDAO memberDAO;

    private final SecretBoardDAO secretBoardDAO;

    private final SecretCommentDAO commentDAO;

    public SecretCommentDTO saveComment(int boardId , SecretCommentDTO comment) {
        MemberDTO student = memberDAO.findByStudentId(comment.getStudentId());
        SecretBoardDTO board = secretBoardDAO.findById(boardId);

        comment.setBoardId(boardId);
        comment.setNickname(student.getNickname());
        comment.setAnonymous(comment.getAnonymous());

        board.setCountComment(board.getCountComment() + 1);

        return commentDAO.save(comment);
    }

    @Transactional
    public void deleteComment(int boardId, int id) {
        SecretBoardDTO board = secretBoardDAO.findById(boardId);
        int reply = commentDAO.countByParentId(id);

        board.setCountComment(board.getCountComment() - reply - 1);

        commentDAO.deleteById(id);
        commentDAO.deleteByParentId(id);
    }

    public List<SecretCommentDTO> getAllCommentsByBoardId(int boardId) {
        return commentDAO.findAllByBoardId(boardId);
    }

    public SecretCommentDTO createReply(int parentId, SecretCommentDTO reply) {
        MemberDTO member = memberDAO.findByStudentId(reply.getStudentId());
        SecretBoardDTO board = secretBoardDAO.findById(reply.getBoardId());

        reply.setParentId(parentId);
        reply.setNickname(member.getNickname());
        board.setCountComment(board.getCountComment() + 1);

        commentDAO.save(reply);
        return reply;
    }

    public List<SecretCommentDTO> getReply(int parentId) {
        return commentDAO.findAllByParentId(parentId);
    }

    public void deleteReply(int boardId, int replyId) {
        SecretBoardDTO board = secretBoardDAO.findById(boardId);

        board.setCountComment(board.getCountComment() - 1);

        commentDAO.deleteById(replyId);
    }

}
