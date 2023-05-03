package hansung.capstone.controller;

import hansung.capstone.dto.FreeCommentDTO;
import hansung.capstone.service.FreeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/freeboard/comment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FreeCommentController {

    private final FreeCommentService commentService;

    /**
     * 댓글 저장
     * @param boardId, comment
     * @return CommentDTO
     */
    @PostMapping("/{boardId}")
    public ResponseEntity<FreeCommentDTO> saveComment(@PathVariable int boardId, @RequestBody FreeCommentDTO comment) {
        FreeCommentDTO result = commentService.saveComment(boardId, comment);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 모든 댓글 조회
     * @param boardId
     * @return List<CommentDTO>
     */
    @GetMapping("/{boardId}")
    public ResponseEntity<List<FreeCommentDTO>> getAllCommentsByBoardId(@PathVariable int boardId) {
        List<FreeCommentDTO> result = commentService.getAllCommentsByBoardId(boardId);
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
    public ResponseEntity<FreeCommentDTO> createReply(@PathVariable int parentId, @RequestBody FreeCommentDTO reply) {
        FreeCommentDTO createdReply = commentService.createReply(parentId, reply);
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
    public ResponseEntity<List<FreeCommentDTO>> getReply(@PathVariable int parentId) {
        return new ResponseEntity<>(commentService.getReply(parentId), HttpStatus.CREATED);
    }

    /**
     * 대댓글 삭제
     * @param replyId
     * @return
     */
    @DeleteMapping("/replies/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable int replyId) {
        commentService.deleteReply(replyId);
        return null;
    }

}