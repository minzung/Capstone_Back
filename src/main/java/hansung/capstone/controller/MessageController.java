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
     * 쪽지 전송
     * @param messageDTO
     * @return MessageDTO
     */
    @PostMapping("")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO) {
        return ResponseEntity.ok(messageService.sendMessage(messageDTO));
    }

    /**
     * 채팅방 입장시 대화내용 조회
     * @param roomId
     * @return List<MessageDTO>
     */
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<MessageDTO>> getRoomMessages(@PathVariable int roomId) {
        List<MessageDTO> messages = messageService.getRoomMessages(roomId);
        return ResponseEntity.ok(messages);
    }

    /**
     * 채팅방 나갈시 대화내용 삭제
     * @param roomId
     * @return Boolean
     */
    @DeleteMapping("/room/{roomId}")
    public ResponseEntity<Boolean> deleteRoom(@PathVariable int roomId) {
        return ResponseEntity.ok(messageService.deleteRoom(roomId));
    }

}
