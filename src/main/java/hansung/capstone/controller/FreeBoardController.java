package hansung.capstone.controller;

import hansung.capstone.dto.FreeBoardDTO;
import hansung.capstone.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 모든 게시글 조회
     * @return FreeBoardDTO
     */
    @GetMapping("/all")
    public ResponseEntity<List<FreeBoardDTO>> getAllPosts() {
        List<FreeBoardDTO> freeBoardDTOList = freeBoardService.getAllPosts();
        return ResponseEntity.ok(freeBoardDTOList);
    }

}
