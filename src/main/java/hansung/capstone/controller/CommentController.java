package hansung.capstone.controller;

import hansung.capstone.dto.CommentDTO;
import hansung.capstone.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    private final CommentService commentService;

    /**
     * 특정 게시판의 모든 댓글을 가져옵니다.
     * @param boardId
     * @return
     */
    @GetMapping("/freeboard/{boardId}")
    public List<CommentDTO> getAllCommentsByBoardId(@PathVariable int boardId) {
        return commentService.getAllCommentsByBoardId(boardId);
    }

    /**
     * 댓글을 저장합니다.
     * @param comment
     * @return
     */
    @PostMapping
    public CommentDTO saveComment(@RequestBody CommentDTO comment) {
        return commentService.saveComment(comment);
    }

    /**
     * 댓글 ID로 댓글을 가져옵니다.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CommentDTO getCommentById(@PathVariable int id) {
        return commentService.getCommentById(id);
    }

    /**
     * 댓글을 업데이트합니다.
     * @param id
     * @param newComment
     * @return
     */
    @PutMapping("/{id}")
    public CommentDTO updateComment(@PathVariable int id, @RequestBody CommentDTO newComment) {
        return commentService.updateComment(id, newComment);
    }

    /**
     * 댓글을 삭제합니다.
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
    }

}