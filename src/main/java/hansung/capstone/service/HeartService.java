package hansung.capstone.service;

import hansung.capstone.dao.FreeBoardDAO;
import hansung.capstone.dao.HeartDAO;
import hansung.capstone.dao.MemberDAO;
import hansung.capstone.dto.FreeBoardDTO;
import hansung.capstone.dto.HeartDTO;
import hansung.capstone.dto.MemberDTO;
import hansung.capstone.dto.response.LikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartDAO heartDAO;

    private final MemberDAO memberDAO;

    private final FreeBoardDAO freeBoardDAO;

    public void addLike(String studentId, int id) {
        FreeBoardDTO board = freeBoardDAO.findById(id);

        HeartDTO heart = new HeartDTO();
        heart.setStudentId(studentId);
        heart.setBoardId(id);
        heart.setIsFilled(true);

        board.setCountLike(board.getCountLike() + 1);

        heartDAO.save(heart);
    }

    public LikeResponse getLike(String studentId, int id) {
        FreeBoardDTO board = freeBoardDAO.findById(id);
        HeartDTO heart = heartDAO.findByMemberAndBoard(studentId, id);

        System.out.println("=====" + heart);

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
        HeartDTO heart = heartDAO.findByMemberAndBoard(studentId, id);

        board.setCountLike(board.getCountLike() - 1);
        heartDAO.delete(heart);
    }

}