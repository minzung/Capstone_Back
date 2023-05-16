package hansung.capstone.controller;

import hansung.capstone.dto.LectureDTO;
import hansung.capstone.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LectureController {

    private final LectureService lectureService;

    /**
     * lecture 등록
     * @param lectureDTO
     * @return LectureDTO
     */
    @PostMapping("/register")
    public ResponseEntity<LectureDTO> registerLecture(@RequestBody LectureDTO lectureDTO) {
        LectureDTO createdLecture = lectureService.register(lectureDTO);
        return new ResponseEntity<>(createdLecture, HttpStatus.CREATED);
    }

    /**
     * id로 lecture 조회
     * @param id
     * @return LectureDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getLectureById(@PathVariable int id) {
        Optional<LectureDTO> lecture = lectureService.getLectureById(id);
        if (lecture.isPresent()) {
            return new ResponseEntity<>(lecture.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLecture(@PathVariable("id") int id) {
        try {
            lectureService.deleteLecture(id);
            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("서버 내부 오류가 발생했습니다.");
        }
    }

    /**
     * 모든 lecture 조회
     * @return LectureDTO
     */
    @GetMapping("/all")
    public ResponseEntity<List<LectureDTO>> getAllLectures() {
        List<LectureDTO> lectures = lectureService.getAllLectures();
        return new ResponseEntity<>(lectures, HttpStatus.OK);
    }

}
