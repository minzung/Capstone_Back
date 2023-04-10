package hansung.capstone.controller;

import hansung.capstone.dto.LectureDTO;
import hansung.capstone.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
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
