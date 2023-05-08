package hansung.capstone.controller;

import hansung.capstone.dto.BookDTO;
import hansung.capstone.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("")
    public ResponseEntity<Void> saveBook(@RequestBody BookDTO bookDTO) {
        bookService.saveBook(bookDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") int id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getAllBook() {
        return ResponseEntity.ok(bookService.getAllBook());
    }

}
