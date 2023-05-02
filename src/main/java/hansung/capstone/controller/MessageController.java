package hansung.capstone.controller;

import hansung.capstone.dto.MessageDTO;
import hansung.capstone.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MessageController {

    private final MessageService messageService;

    /**
     * 쪽지 저장
     * @param messageDTO
     * @return MessageDTO
     */
    @PostMapping("")
    public ResponseEntity<MessageDTO> saveMessage(@RequestBody MessageDTO messageDTO) {
        return ResponseEntity.ok(messageService.saveMessage(messageDTO));
    }

    /**
     * id로 쪽지 조회
     * @param studentId
     * @return List<MessageDTO>
     */
    @GetMapping("{studentId}")
    public ResponseEntity<List<MessageDTO>> getMessage(@PathVariable("studentId") int studentId) {
        return ResponseEntity.ok(messageService.getMessage(studentId));
    }


    /**
     * 쪽지방 나가기
     */
    @DeleteMapping("{studentId}")
    public ResponseEntity<MessageDTO> deleteMessage(@PathVariable("studentId") int studentId) {
        return ResponseEntity.ok(messageService.deleteMessage(studentId));
    }

}
