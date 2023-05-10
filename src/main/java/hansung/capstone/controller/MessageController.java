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
        return ResponseEntity.ok(messageService.getAllMessage(studentId));
    }

    /**
     * 대화 내용 조회
     * @param sender, receiver
     * @return MessageDTO
     */
    @GetMapping("")
    public ResponseEntity<List<MessageDTO>> getConversation(@RequestParam String sender, @RequestParam String receiver) {
        return ResponseEntity.ok(messageService.getConversation(sender, receiver));
    }

    /**
     * 쪽지방 나가기
     */
    @DeleteMapping("{studentId}")
    public ResponseEntity<MessageDTO> deleteMessage(@PathVariable("studentId") int studentId) {
        return ResponseEntity.ok(messageService.deleteMessage(studentId));
    }

}
