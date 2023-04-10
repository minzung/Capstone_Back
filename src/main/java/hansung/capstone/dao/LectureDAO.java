package hansung.capstone.dao;

import hansung.capstone.dto.LectureDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureDAO extends JpaRepository<LectureDTO, Integer> {

    List<LectureDTO> findAll();

}
