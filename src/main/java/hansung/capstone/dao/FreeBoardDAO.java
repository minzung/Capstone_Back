package hansung.capstone.dao;

import hansung.capstone.dto.FreeBoardDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeBoardDAO extends JpaRepository<FreeBoardDTO, Integer>  {

    FreeBoardDTO getPostById(int id);

}
