package hansung.capstone.service;

import hansung.capstone.dao.SecretBoardDAO;
import hansung.capstone.dao.SecretHeartDAO;
import hansung.capstone.dto.SecretBoardDTO;
import hansung.capstone.dto.SecretHeartDTO;
import hansung.capstone.dto.response.LikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecretHeartService {

    private final SecretHeartDAO heartDAO;

    private final SecretBoardDAO secretBoardDAO;

    public void addLike(String studentId, int id) {
        SecretBoardDTO board = secretBoardDAO.findById(id);

        SecretHeartDTO heart = new SecretHeartDTO();
        heart.setStudentId(studentId);
        heart.setBoardId(id);
        heart.setIsFilled(true);

        board.setCountLike(board.getCountLike() + 1);

        heartDAO.save(heart);
    }

    public LikeResponse getLike(String studentId, int id) {
        SecretBoardDTO board = secretBoardDAO.findById(id);
        SecretHeartDTO heart = heartDAO.findByMemberAndBoard(studentId, id);

        LikeResponse response = new LikeResponse();
        response.setCountLike(board.getCountLike());

        if (heart == null || board.getCountLike() == 0) {
            response.setIsFilled(false);
        } else {
            response.setIsFilled(heart.getIsFilled());
        }

        return response;
    }

    public void deleteLike(String studentId, int id) {
        SecretBoardDTO board = secretBoardDAO.findById(id);
        SecretHeartDTO heart = heartDAO.findByMemberAndBoard(studentId, id);

        board.setCountLike(board.getCountLike() - 1);
        heartDAO.delete(heart);
    }

}