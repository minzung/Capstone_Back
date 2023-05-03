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

@Service
@RequiredArgsConstructor
public class FreeCommentService {

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

        return commentDAO.save(comment);
    }

    public void deleteComment(int boardId, int id) {
        FreeBoardDTO board = freeBoardDAO.findById(boardId);
        board.setCountComment(board.getCountComment() - 1);

        commentDAO.deleteAllCommentById(id);
    }

    public List<FreeCommentDTO> getAllCommentsByBoardId(int boardId) {
        return commentDAO.findAllByBoardId(boardId);
    }

    public FreeCommentDTO createReply(int parentId, FreeCommentDTO reply) {
        MemberDTO member = memberDAO.findByStudentId(reply.getStudentId());
        FreeBoardDTO board = freeBoardDAO.findById(reply.getBoardId());

        reply.setParentId(parentId);
        reply.setNickname(member.getNickname());
        board.setCountComment(board.getCountComment() + 1);

        commentDAO.save(reply);
        return reply;
    }

    public List<FreeCommentDTO> getReply(int parentId) {
        return commentDAO.findAllByParentId(parentId);
    }

    public void deleteReply(int replyId) {
        FreeBoardDTO board = freeBoardDAO.findById(replyId);

        board.setCountComment(board.getCountComment() - 1);

        commentDAO.deleteById(replyId);
    }

}
