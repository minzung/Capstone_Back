package hansung.capstone.dao;

import hansung.capstone.dto.MessageRoomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRoomDAO extends JpaRepository<MessageRoomDTO, Integer> {

    MessageRoomDTO findBySenderAndReceiver(String sender, String receiver);

    @Query("SELECT r FROM room r WHERE r.sender = :studentId OR r.receiver = :studentId")
    List<MessageRoomDTO> findBySenderOrReceiver(String studentId);

}
