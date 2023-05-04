package hansung.capstone.controller;

import hansung.capstone.dto.MessageDTO;
import hansung.capstone.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MessageController {

    private final MessageService messageService;

    /**
     * 쪽지 전송
     * @param messageDTO
     * @return MessageDTO
     */
    @PostMapping("")
    public ResponseEntity<MessageDTO> saveMessage(@RequestBody MessageDTO messageDTO) {
        return ResponseEntity.ok(messageService.saveMessage(messageDTO));
    }

    /**
     * studnetId로 모든 쪽지 조회
     * @param studentId
     * @return List<MessageDTO>
     */
    @GetMapping("{studentId}/all")
    public ResponseEntity<List<MessageDTO>> getAllMessage(@PathVariable("studentId") String studentId) {
        try {
            List<MessageDTO> messages = messageService.getAllMessage(studentId);
            return ResponseEntity.ok(messages);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * id로 쪽지 내용 조회
     * @param id
     * @return List<MessageDTO>
     */
    @GetMapping("{studentId}")
    public ResponseEntity<Optional<MessageDTO>> getMessageById(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(messageService.getMessageById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    /**
     * 쪽지방 나가기
     */
    @DeleteMapping("{studentId}")
    public ResponseEntity<MessageDTO> deleteMessage(@PathVariable("studentId") int studentId) {
        return ResponseEntity.ok(messageService.deleteMessage(studentId));
    }

}
