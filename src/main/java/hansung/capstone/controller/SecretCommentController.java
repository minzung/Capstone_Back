package hansung.capstone.controller;

import hansung.capstone.dto.FreeCommentDTO;
import hansung.capstone.dto.SecretCommentDTO;
import hansung.capstone.service.FreeCommentService;
import hansung.capstone.service.SecretCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secretboard/comment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SecretCommentController {

    private final SecretCommentService commentService;

    /**
     * 댓글 저장
     * @param boardId, comment
     * @return CommentDTO
     */
    @PostMapping("/{boardId}")
    public ResponseEntity<SecretCommentDTO> saveComment(@PathVariable int boardId, @RequestBody SecretCommentDTO comment) {
        SecretCommentDTO result = commentService.saveComment(boardId, comment);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 모든 댓글 조회
     * @param boardId
     * @return List<CommentDTO>
     */
    @GetMapping("/{boardId}")
    public ResponseEntity<List<SecretCommentDTO>> getAllCommentsByBoardId(@PathVariable int boardId) {
        List<SecretCommentDTO> result = commentService.getAllCommentsByBoardId(boardId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 댓글을 삭제
     * @param commentId
     */
    @DeleteMapping("/{boardId}/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int boardId, @PathVariable int commentId) {
        commentService.deleteComment(boardId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 대댓글 등록
     * @param parentId
     * @return
     */
    @PostMapping("/{parentId}/replies")
    public ResponseEntity<SecretCommentDTO> createReply(@PathVariable int parentId, @RequestBody SecretCommentDTO reply) {
        SecretCommentDTO createdReply = commentService.createReply(parentId, reply);
        if (createdReply != null) {
            return new ResponseEntity<>(createdReply, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 대댓글 조회
     * @param parentId
     * @return
     */
    @GetMapping("/{parentId}/replies")
    public ResponseEntity<List<SecretCommentDTO>> getReply(@PathVariable int parentId) {
        return new ResponseEntity<>(commentService.getReply(parentId), HttpStatus.CREATED);
    }

    /**
     * 대댓글 삭제
     * @param boardId, replyId
     * @return
     */
    @DeleteMapping("/{boardId}/{replyId}/replies")
    public ResponseEntity<?> deleteReply(@PathVariable int boardId, @PathVariable int replyId) {
        commentService.deleteReply(boardId, replyId);
        return null;
    }

}