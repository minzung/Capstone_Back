package hansung.capstone.controller;

import hansung.capstone.dto.BookDTO;
import hansung.capstone.dto.LectureDTO;
import hansung.capstone.dto.item.State;
import hansung.capstone.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookController {

    private final BookService bookService;

    @PostMapping("")
    public ResponseEntity<Void> saveBook(@RequestBody BookDTO bookDTO) {
        bookService.saveBook(bookDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<BookDTO> setSaleState(@PathVariable int id, @RequestParam("saleState") String saleState) {
        return new ResponseEntity<>(bookService.setSaleState(id, State.valueOf(saleState)), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

}
