package hansung.capstone.service;

import hansung.capstone.dao.*;
import hansung.capstone.dto.FreeBoardDTO;
import hansung.capstone.dto.MemberDTO;
import hansung.capstone.dto.SecretBoardDTO;
import hansung.capstone.dto.request.UpdateFreeBoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SecretBoardService {

    private final MemberDAO memberDAO;

    private final SecretBoardDAO boardDAO;

    private final SecretCommentDAO commentDAO;

    private final ResourceLoader resourceLoader;

    // 게시글 등록
    public void createPost(SecretBoardDTO secretBoardDTO) {
        MemberDTO member = memberDAO.findByStudentId(secretBoardDTO.getStudentId());

        secretBoardDTO.setNickname(member.getNickname());

        String base64Image = secretBoardDTO.getImageFile();

        if (base64Image != null && !base64Image.isEmpty()) {
            // 저장할 디렉토리 지정
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/freeboard/";
            // 고유한 파일 이름 생성
            String fileName = "image_" + secretBoardDTO.getStudentId() + System.currentTimeMillis() + ".png";
            Path path = Paths.get(uploadDir + fileName);

            // Base64를 디코딩하여 이미지를 저장
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image.split(",")[1]);

            try {
                Files.write(path, decodedBytes);
                // 파일 저장
                secretBoardDTO.setFileDir(path.toString());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("File saving failed", e);
            }
        }
        boardDAO.save(secretBoardDTO);
    }

    /**
     * 게시글 수정
     * @param updateFreeBoardRequest
     * @return FreeBoardDTO
     */
    public SecretBoardDTO updateSecretBoard(String studentId, UpdateFreeBoardRequest updateFreeBoardRequest) throws IllegalAccessException {
        // 업데이트할 게시글을 가져옵니다.
        SecretBoardDTO freeBoard = boardDAO.findById(updateFreeBoardRequest.getId());

        // studentId가 일치하는지 확인합니다.
        if (!freeBoard.getStudentId().equals(studentId)) {
            throw new IllegalAccessException("게시글을 수정할 권한이 없습니다.");
        }

        // 게시글을 수정합니다.
        freeBoard.setTitle(updateFreeBoardRequest.getTitle());
        freeBoard.setContent(updateFreeBoardRequest.getContent());
        freeBoard.setAnonymous(updateFreeBoardRequest.getIsAnonymous());

        // 이미지 파일 처리
        String base64Image = updateFreeBoardRequest.getImageFile();
        if (base64Image != null && !base64Image.isEmpty()) {
            // 저장할 디렉토리 지정
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/secret/";

            // 디렉토리 생성
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 고유한 파일 이름 생성
            String fileName = "image_" + freeBoard.getStudentId() + System.currentTimeMillis() + ".png";
            Path path = Paths.get(uploadDir + fileName);

            // Base64를 디코딩하여 이미지를 저장
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image.split(",")[1]);

            try {
                Files.write(path, decodedBytes);
                // 파일 저장
                freeBoard.setFileDir(path.toString());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("File saving failed", e);
            }
        }

        // 수정된 게시글을 저장합니다.
        return boardDAO.save(freeBoard);
    }

    /**
     * ID로 게시글 조회
     * @param id
     * @return FreeBoardDTO
     */
    public SecretBoardDTO getPostById(int id) {
        SecretBoardDTO board = boardDAO.findById(id);

        Resource imageResource = getImage(id);
        if (imageResource != null && imageResource.exists()) {
            try {
                byte[] imageData = StreamUtils.copyToByteArray(imageResource.getInputStream());
                String base64Image = Base64.getEncoder().encodeToString(imageData);
                board.setImageFile(base64Image);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read image data", e);
            }
        }
        return board;
    }

    /**
     * ID로 게시글 조회후 이미지 리턴
     */
    public Resource getImage(int id) {
        SecretBoardDTO board = boardDAO.findById(id);
        String fileDir = board.getFileDir();

        if (fileDir == null || fileDir.isEmpty()) {
            return null;
        }

        Resource imageResource = resourceLoader.getResource("file:" + fileDir);

        if (!imageResource.exists()) {
            return null;
        }

        return imageResource;
    }


    /**
     * 모든 게시글 조회
     * @return List<FreeBoardDTO>
     */
    public List<SecretBoardDTO> getAllPosts() {
        return boardDAO.findAll();
    }

    /**
     * 게시글 삭제
     * @param studentId, id
     */
    @Transactional
    public void deleteFreeBoard(String studentId, int id) {
        SecretBoardDTO board = boardDAO.findById(id);

        if(!Objects.equals(board.getStudentId(), studentId)) {
            throw new IllegalStateException("작성자만 게시글을 삭제할 수 있습니다.");
        }
        boardDAO.deleteById(id);
        commentDAO.deleteByBoardId(id);
    }

}
