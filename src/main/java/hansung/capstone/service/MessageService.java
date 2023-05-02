package hansung.capstone.service;

import hansung.capstone.dao.MessageDAO;
import hansung.capstone.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageDAO messageDAO;

    public MessageDTO saveMessage(MessageDTO messageDTO) {
        return messageDAO.save(messageDTO);
    }

    public List<MessageDTO> getMessage(int studentId) {
        return messageDAO.findByStudentId(studentId);
    }

    public void deleteMessage(int studentId) {
        messageDAO.deleteByStduentId(studentId);
    }

}