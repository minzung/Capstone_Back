package hansung.capstone.dao;

import hansung.capstone.dto.TimeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeDAO extends JpaRepository<TimeDTO, Integer> {

    List<TimeDTO> findAllByStudentId(int studentId);

}