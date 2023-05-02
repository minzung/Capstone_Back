package hansung.capstone.dao;

import hansung.capstone.dto.MessageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDAO extends JpaRepository<MessageDTO, Integer> {

    @Query("SELECT m FROM message m WHERE m.sender = :studentId OR m.receiver = :studentId")
    List<MessageDTO> findByStudentId(String studentId);

    @Modifying
    @Query("DELETE FROM message m WHERE m.sender = :studentId OR m.receiver = :studentId")
    MessageDTO deleteByStduentId(int studentId);

}
