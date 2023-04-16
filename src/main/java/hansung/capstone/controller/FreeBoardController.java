package hansung.capstone.controller;

import hansung.capstone.dto.FreeBoardDTO;
import hansung.capstone.dto.request.UpdateFreeBoardRequest;
import hansung.capstone.dto.request.UpdateLikeRequest;
import hansung.capstone.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/freeboard")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    /**
     * 게시글 등록
     * @param freeBoardDTO
     * @return
     */
    @PostMapping("")
    public ResponseEntity<Void> createPost(@RequestBody FreeBoardDTO freeBoardDTO) {
        freeBoardService.createPost(freeBoardDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * ID로 게시글 조회
     * @param id
     * @return FreeBoardDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<FreeBoardDTO> getPostById(@PathVariable int id) {
        FreeBoardDTO freeBoardDTO = freeBoardService.getPostById(id);
        return ResponseEntity.ok(freeBoardDTO);
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
            return ResponseEntity.ok(freeBoardService.updateFreeBoard(studentId, updateFreeBoardRequest));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("작성자만 게시글을 수정할 수 있습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정할 게시글이 존재하지 않습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
        }
    }

    /**
     * 좋아요 수
     * @param id, updateLikeRequest
     * @return FreeBoardDTO
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateLike(@PathVariable int id, @RequestBody UpdateLikeRequest updateLikeRequest) {
        try {
            freeBoardService.updateLike(id, updateLikeRequest.getCountLike());
            return ResponseEntity.ok("좋아용");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
        }
    }

    /**
     * 모든 게시글 조회
     * @return FreeBoardDTO
     */
    @GetMapping("/all")
    public ResponseEntity<List<FreeBoardDTO>> getAllPosts() {
        List<FreeBoardDTO> freeBoardDTOList = freeBoardService.getAllPosts();
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
            freeBoardService.deleteFreeBoard(studentId, id);
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
