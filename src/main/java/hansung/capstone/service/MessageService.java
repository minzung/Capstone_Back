package hansung.capstone.service;

import hansung.capstone.dao.MessageDAO;
import hansung.capstone.dao.MessageRoomDAO;
import hansung.capstone.dto.MessageDTO;
import hansung.capstone.dto.MessageRoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageDAO messageDAO;

    private final MessageRoomDAO roomDAO;

    public MessageDTO sendMessage(MessageDTO messageDTO) {
        // 메시지방이 존재하는지 확인
        MessageRoomDTO room = roomDAO.findBySenderAndReceiver(messageDTO.getSender(), messageDTO.getReceiver());

        if (room == null) {
            // sender와 receiver로 방을 찾아보고 존재하지 않을 경우, receiver와 sender로 다시 방을 찾아봅니다.
            room = roomDAO.findBySenderAndReceiver(messageDTO.getReceiver(), messageDTO.getSender());
        }

        if (room == null) {
            // 방이 존재하지 않는 경우, 새로운 메시지방을 생성합니다.
            room = createMessageRoom(messageDTO.getSender(), messageDTO.getReceiver());
        }

        messageDTO.setRoom(room);
        return messageDAO.save(messageDTO);
    }

    private MessageRoomDTO createMessageRoom(String sender, String receiver) {
        MessageRoomDTO room = new MessageRoomDTO();
        room.setSender(sender);
        room.setReceiver(receiver);
        return roomDAO.save(room);
    }

    public List<MessageDTO> getRoomList(String studentId) {
        return messageDAO.findBySenderOrReceiver(studentId);
    }

    public List<MessageDTO> getRoomMessages(int roomId) {
        return messageDAO.findByRoomIdOrderBySendTime(roomId);
    }

    public boolean deleteRoom(int roomId) {
        Optional<MessageRoomDTO> optionalRoom = roomDAO.findById(roomId);
        if (optionalRoom.isPresent()) {
            MessageRoomDTO room = optionalRoom.get();
            messageDAO.deleteByRoom(room);
            roomDAO.delete(room);
            return true;
        } else {
            return false;
        }
    }

}