package hansung.capstone.dao;

import hansung.capstone.dto.MessageRoomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRoomDAO extends JpaRepository<MessageRoomDTO, Integer> {

    MessageRoomDTO findBySenderAndReceiver(String sender, String receiver);

}
