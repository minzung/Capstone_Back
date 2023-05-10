package hansung.capstone.dao;

import hansung.capstone.dto.SecretBoardDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretBoardDAO extends JpaRepository<SecretBoardDTO, Integer>  {

    SecretBoardDTO findById(int boardId);

}
