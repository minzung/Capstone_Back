package hansung.capstone.dao;

import hansung.capstone.dto.TimeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeDAO extends JpaRepository<TimeDTO, Integer> {

    @Query("SELECT t FROM time t WHERE t.studentId = :studentId")
    List<TimeDTO> findAllByStudentId(String studentId);

}