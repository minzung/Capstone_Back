package hansung.capstone.controller;

import hansung.capstone.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/heart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("")
    public ResponseEntity<?> addLike(@RequestParam("studentId") String studentId, @RequestParam("freeboardId") int freeboardId) {
        heartService.addLike(studentId, freeboardId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studentId}/{freeboardId}")
    public ResponseEntity<?> getLike(@PathVariable String studentId, @PathVariable int freeboardId) {
        heartService.getLike(studentId, freeboardId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteLike(@RequestParam("studentId") String studentId, @RequestParam("freeboardId") int freeboardId) {
        heartService.deleteLike(studentId, freeboardId);
        return ResponseEntity.ok().build();
    }
}
