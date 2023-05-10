package hansung.capstone.service;

import hansung.capstone.dao.FreeBoardDAO;
import hansung.capstone.dao.FreeHeartDAO;
import hansung.capstone.dto.FreeBoardDTO;
import hansung.capstone.dto.FreeHeartDTO;
import hansung.capstone.dto.response.LikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreeHeartService {

    private final FreeHeartDAO heartDAO;

    private final FreeBoardDAO freeBoardDAO;

    public void addLike(String studentId, int id) {
        FreeBoardDTO board = freeBoardDAO.findById(id);

        FreeHeartDTO heart = new FreeHeartDTO();
        heart.setStudentId(studentId);
        heart.setBoardId(id);
        heart.setIsFilled(true);

        board.setCountLike(board.getCountLike() + 1);

        heartDAO.save(heart);
    }

    public LikeResponse getLike(String studentId, int id) {
        FreeBoardDTO board = freeBoardDAO.findById(id);
        FreeHeartDTO heart = heartDAO.findByMemberAndBoard(studentId, id);

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
        FreeBoardDTO board = freeBoardDAO.findById(id);
        FreeHeartDTO heart = heartDAO.findByMemberAndBoard(studentId, id);

        board.setCountLike(board.getCountLike() - 1);
        heartDAO.delete(heart);
    }

}