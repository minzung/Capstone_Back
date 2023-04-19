package hansung.capstone.service;

import hansung.capstone.dao.FreeBoardDAO;
import hansung.capstone.dao.MemberDAO;
import hansung.capstone.dto.FreeBoardDTO;
import hansung.capstone.dto.MemberDTO;
import hansung.capstone.dto.item.Files;
import hansung.capstone.dto.request.UpdateFreeBoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FreeBoardService {

    private final MemberDAO memberDAO;

    private final FreeBoardDAO dao;

    // 게시글 등록
    public void createPost(FreeBoardDTO freeBoardDTO) {
        MemberDTO member = memberDAO.findByStudentId(freeBoardDTO.getStudentId());

        freeBoardDTO.setNickname(member.getNickname());

        MultipartFile imageFile = freeBoardDTO.getImageFile();

        // 파일이 존재하고, 비어있지 않다면
        if (imageFile != null && !imageFile.isEmpty()) {
            // 저장할 디렉토리 지정
            String uploadDir = "C://Users/KMJ/Desktop/capstone/src/main/resources/static/freeboard";

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
        dao.save(freeBoardDTO);
    }

    /**
     * 게시글 수정
     * @param updateFreeBoardRequest
     * @return FreeBoardDTO
     */
    public FreeBoardDTO updateFreeBoard(String studentId, UpdateFreeBoardRequest updateFreeBoardRequest) throws IllegalAccessException {
        // 업데이트할 게시글을 가져옵니다.
        FreeBoardDTO freeBoard = dao.findById(updateFreeBoardRequest.getId());

        // studentId가 일치하는지 확인합니다.
        if (!freeBoard.getStudentId().equals(studentId)) {
            throw new IllegalAccessException("게시글을 수정할 권한이 없습니다.");
        }

        // 게시글을 수정합니다.
        freeBoard.setTitle(updateFreeBoardRequest.getTitle());
        freeBoard.setContent(updateFreeBoardRequest.getContent());
        freeBoard.setIsAnonymous(updateFreeBoardRequest.getIsAnonymous());

        // 수정된 게시글을 저장합니다.
        return dao.save(freeBoard);
    }

    /**
     * ID로 게시글 조회
     * @param id
     * @return FreeBoardDTO
     */
    public FreeBoardDTO getPostById(int id) {
        return dao.findById(id);
    }

    /**
     * 모든 게시글 조회
     * @return List<FreeBoardDTO>
     */
    public List<FreeBoardDTO> getAllPosts() {
        return dao.findAll();
    }

    /**
     * 게시글 삭제
     * @param studentId, id
     */
    public void deleteFreeBoard(String studentId, int id) {
        FreeBoardDTO freeboard = dao.findById(id);

        if(!Objects.equals(freeboard.getStudentId(), studentId)) {
            throw new IllegalStateException("작성자만 게시글을 삭제할 수 있습니다.");
        }

        dao.deleteById(id);
    }

}
