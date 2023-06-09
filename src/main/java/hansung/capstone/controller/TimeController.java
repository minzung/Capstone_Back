package hansung.capstone.controller;

import hansung.capstone.dto.TimeDTO;
import hansung.capstone.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/time")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TimeController {

    private final TimeService timeService;

    /**
     * 시간표 등록
     * @param timeDTO
     * @return MessageDTO
     */
    @PostMapping("")
    public ResponseEntity<TimeDTO> saveTime(@RequestBody TimeDTO timeDTO) {
        return ResponseEntity.ok(timeService.saveTime(timeDTO));
    }

    /**
     * 시간표 조회
     * @param studentId
     * @return List<TimeDTO>
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<List<TimeDTO>> getTime(@PathVariable("studentId") String studentId) {
        return ResponseEntity.ok(timeService.getTime(studentId));
    }

    /**
     * 시간표 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTime(@PathVariable("id") int id) {
        timeService.deleteTime(id);
        return ResponseEntity.ok("삭제 완료");
    }

}
