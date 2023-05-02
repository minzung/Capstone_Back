package hansung.capstone.service;

import hansung.capstone.dao.MessageDAO;
import hansung.capstone.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageDAO messageDAO;

    public MessageDTO saveMessage(MessageDTO messageDTO) {
        return messageDAO.save(messageDTO);
    }

    public List<MessageDTO> getMessage(int studentId) throws NoSuchElementException {
        List<MessageDTO> messages = messageDAO.findByStudentId(studentId);
        if (messages.isEmpty()) {
            throw new NoSuchElementException("No messages found for studentId: " + studentId);
        }
        return messages;
    }

    public MessageDTO deleteMessage(int studentId) {
        return messageDAO.deleteByStduentId(studentId);
    }

}