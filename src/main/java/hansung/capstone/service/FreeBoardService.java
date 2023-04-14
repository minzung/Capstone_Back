package hansung.capstone.service;

import hansung.capstone.dao.FreeBoardDAO;
import hansung.capstone.dto.FreeBoardDTO;
import hansung.capstone.dto.item.Files;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FreeBoardService {

    private final FreeBoardDAO dao;

    // 게시글 등록
    public void createPost(FreeBoardDTO freeBoardDTO) {
        MultipartFile imageFile = freeBoardDTO.getImageFile();

        // 파일이 존재하고, 비어있지 않다면
        if (imageFile != null && !imageFile.isEmpty()) {
            // 저장할 디렉토리 지정
            String uploadDir = "uploadedImages/";

            // 고유한 파일 이름 생성
            String originalFilename = imageFile.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + extension;

            // 파일 저장
            try {
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                String filePath = Paths.get(uploadDir, uniqueFilename).toString();
                imageFile.transferTo(new File(filePath));

                // 파일 정보를 DTO의 Files 객체에 저장
                Files files = new Files();
                files.setFilename(uniqueFilename);
                files.setFileOriName(originalFilename);
                files.setFileUrl(filePath);

                freeBoardDTO.setFiles(files);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("File upload failed.");
            }
        }
        freeBoardDTO.setAnonymous(freeBoardDTO.isAnonymous());
        dao.save(freeBoardDTO);
    }

    // ID로 게시글 조회
    public FreeBoardDTO getPostById(int id) {
        return dao.getPostById(id);
    }

    // 모든 게시글 조회
    public List<FreeBoardDTO> getAllPosts() {
        return dao.findAll();
    }
}
