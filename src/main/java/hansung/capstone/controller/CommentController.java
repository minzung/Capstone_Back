package hansung.capstone.controller;

import hansung.capstone.dto.CommentDTO;
import hansung.capstone.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글을 저장합니다.
     * @param id, comment
     * @return CommentDTO
     */
    @PostMapping("/{id}")
    public ResponseEntity<CommentDTO> saveComment(@PathVariable int id, @RequestBody CommentDTO comment) {
        CommentDTO result = commentService.saveComment(id, comment);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 특정 게시판의 모든 댓글을 가져옵니다.
     * @param boardId
     * @return List<CommentDTO>
     */
    @GetMapping("/freeboard/{boardId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByBoardId(@PathVariable int boardId) {
        List<CommentDTO> result = commentService.getAllCommentsByBoardId(boardId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 댓글을 업데이트합니다.
     * @param id, newComment
     * @return CommentDTO
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable int id, @RequestBody CommentDTO newComment) {
        CommentDTO result = commentService.updateComment(id, newComment);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 댓글을 삭제합니다.
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 대댓글 등록 기능
     * @param parentId
     * @param reply
     * @return
     */
    @PostMapping("/{parentId}/replies")
    public ResponseEntity<CommentDTO> createReply(@PathVariable int parentId, @RequestBody CommentDTO reply) {
        CommentDTO createdReply = commentService.createReply(parentId, reply);
        if (createdReply != null) {
            return new ResponseEntity<>(createdReply, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{commentId}/replies")
    public ResponseEntity<CommentDTO> updateReply(@PathVariable int commentId, @RequestBody CommentDTO updatedComment) {
        CommentDTO result = commentService.updateReply(commentId, updatedComment);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{commentId}/replies")
    public ResponseEntity<Void> deleteReply(@PathVariable int commentId) {
        boolean isDeleted = commentService.deleteReply(commentId);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}