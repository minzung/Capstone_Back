package hansung.capstone.dao;

import hansung.capstone.dto.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDAO extends JpaRepository<BookDTO, Integer> {

    void deleteById(int id);

    BookDTO findById(int id);

}
