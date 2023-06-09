package hansung.capstone.dao;

import hansung.capstone.dto.MessageDTO;
import hansung.capstone.dto.MessageRoomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDAO extends JpaRepository<MessageDTO, Integer> {

    List<MessageDTO> findByRoomIdOrderBySendTime(int roomId);

    void deleteByRoom(MessageRoomDTO room);

    List<MessageDTO> findByRoom(MessageRoomDTO room);

    MessageDTO findById(int roomId);

}
