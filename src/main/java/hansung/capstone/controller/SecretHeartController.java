package hansung.capstone.controller;

import hansung.capstone.dto.response.LikeResponse;
import hansung.capstone.service.FreeHeartService;
import hansung.capstone.service.SecretHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secret/heart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SecretHeartController {

    private final SecretHeartService heartService;

    @PostMapping("/{studentId}/{id}")
    public ResponseEntity<?> addLike(@PathVariable String studentId, @PathVariable int id) {
        heartService.addLike(studentId, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studentId}/{id}")
    public ResponseEntity<LikeResponse> getLike(@PathVariable String studentId, @PathVariable int id) {
        LikeResponse like = heartService.getLike(studentId, id);
        return ResponseEntity.ok().body(like);
    }

    @DeleteMapping("/{studentId}/{id}")
    public ResponseEntity<?> deleteLike(@PathVariable String studentId, @PathVariable int id) {
        heartService.deleteLike(studentId, id);
        return ResponseEntity.ok().build();
    }

}
