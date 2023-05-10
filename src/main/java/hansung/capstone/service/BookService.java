package hansung.capstone.service;

import hansung.capstone.dao.BookDAO;
import hansung.capstone.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

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

    private final ResourceLoader resourceLoader;

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

    /**
     * ID로 게시글 조회
     * @param id
     * @return FreeBoardDTO
     */
    public BookDTO getBookById(int id) {
        BookDTO book = bookDAO.findById(id);

        Resource imageResource = getImage(id);

        if (imageResource != null && imageResource.exists()) {
            try {
                byte[] imageData = StreamUtils.copyToByteArray(imageResource.getInputStream());
                String base64Image = Base64.getEncoder().encodeToString(imageData);
                book.setImageFile(base64Image);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read image data", e);
            }
        }
        return book;
    }

    /**
     * ID로 게시글 조회후 이미지 리턴
     */
    public Resource getImage(int id) {
        BookDTO book = bookDAO.findById(id);
        String fileDir = book.getFileDir();

        if (fileDir == null || fileDir.isEmpty()) {
            return null;
        }

        Resource imageResource = resourceLoader.getResource("file:" + fileDir);

        if (!imageResource.exists()) {
            return null;
        }

        return imageResource;
    }

    public void deleteBook(int id) {
        bookDAO.deleteById(id);
    }

    public List<BookDTO> getAllBooks() {
        return bookDAO.findAll();
    }

}
