package hansung.capstone.controller;

import hansung.capstone.dto.SecretBoardDTO;
import hansung.capstone.dto.request.UpdateFreeBoardRequest;
import hansung.capstone.service.SecretBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secretboard")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SecretBoardController {

    private final SecretBoardService secretBoardService;

    /**
     * 게시글 등록
     * @param secretBoardDTO
     * @return
     */
    @PostMapping("")
    public ResponseEntity<Void> createPost(@RequestBody SecretBoardDTO secretBoardDTO) {
        secretBoardService.createPost(secretBoardDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * ID로 게시글 조회
     * @param id
     * @return FreeBoardDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<SecretBoardDTO> getPostById(@PathVariable int id) {
        return ResponseEntity.ok(secretBoardService.getPostById(id));
    }

    /**
     * 게시글 수정
     * @param updateFreeBoardRequest
     * @return FreeBoardDTO
     */
    @PatchMapping("/{studentId}/{id}")
    public ResponseEntity<?> updateFreeBoard(@PathVariable String studentId, @PathVariable int id, @RequestBody UpdateFreeBoardRequest updateFreeBoardRequest) {
        try {
            updateFreeBoardRequest.setId(id);
            return ResponseEntity.ok(secretBoardService.updateSecretBoard(studentId, updateFreeBoardRequest));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("작성자만 게시글을 수정할 수 있습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정할 게시글이 존재하지 않습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
        }
    }

    /**
     * 모든 게시글 조회
     * @return FreeBoardDTO
     */
    @GetMapping("/all")
    public ResponseEntity<List<SecretBoardDTO>> getAllPosts() {
        List<SecretBoardDTO> freeBoardDTOList = secretBoardService.getAllPosts();
        return ResponseEntity.ok(freeBoardDTOList);
    }

    /**
     * 게시글 삭제
     * @param studentId, id
     * @return FreeBoardDTO
     */
    @DeleteMapping("/{studentId}/{id}")
    public ResponseEntity<?> deleteFreeBoard(@PathVariable("studentId") String studentId, @PathVariable("id") int id) {
        try {
            secretBoardService.deleteFreeBoard(studentId, id);
            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("작성자만 게시글을 삭제할 수 있습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 게시글이 존재하지 않습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
        }
    }

}

