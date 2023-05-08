package hansung.capstone.service;

import hansung.capstone.dao.BookDAO;
import hansung.capstone.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDAO bookDAO;

    public void saveBook(BookDTO bookDTO) {
        bookDAO.save(bookDTO);
    }

    public void deleteBook(int id) {
        bookDAO.delete(id);
    }

}
