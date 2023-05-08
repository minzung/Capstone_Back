package hansung.capstone.dao;

import hansung.capstone.dto.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDAO extends JpaRepository<BookDTO, Integer> {

    void delete(int id);

}
