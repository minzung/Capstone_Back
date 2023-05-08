package hansung.capstone.service;

import hansung.capstone.dao.BookDAO;
import hansung.capstone.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDAO bookDAO;

    public void saveBook(BookDTO bookDTO) {
        String base64Image = bookDTO.getImageFile();

        if (base64Image != null && !base64Image.isEmpty()) {
            // 저장할 디렉토리 지정
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/book/";
            // 고유한 파일 이름 생성
            String fileName = "image_" + bookDTO.getStudentId() + System.currentTimeMillis() + ".png";
            Path path = Paths.get(uploadDir + fileName);

            // Base64를 디코딩하여 이미지를 저장
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image.split(",")[1]);

            try {
                Files.write(path, decodedBytes);
                // 파일 저장
                bookDTO.setFileDir(path.toString());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("File saving failed", e);
            }
        }
        bookDAO.save(bookDTO);
    }

    public BookDTO getBookById(int id) {
        return bookDAO.getBookById(id);
    }

    public void deleteBook(int id) {
        bookDAO.delete(id);
    }

    public List<BookDTO> getAllBook() {
        return getAllBook();
    }

}
