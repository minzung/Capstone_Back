package hansung.capstone.service;

import hansung.capstone.dao.MessageDAO;
import hansung.capstone.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageDAO messageDAO;

    public MessageDTO saveMessage(MessageDTO messageDTO) {
        return messageDAO.save(messageDTO);
    }

    public List<MessageDTO> getAllMessage(String studentId){
        List<MessageDTO> messages = messageDAO.findByStudentId(studentId);
        if (messages.isEmpty()) {
            return null;
        }
        return messages;
    }

    public List<MessageDTO> getConversation(String sender, String receiver) {
        return messageDAO.findBySenderAndReceiverOrderByTimestampAsc(sender, receiver);
    }

    public MessageDTO deleteMessage(int studentId) {
        return messageDAO.deleteByStduentId(studentId);
    }


}