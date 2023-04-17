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

    public void addLike(String studentId, int freeboardId) {
        MemberDTO member = memberDAO.findByStudentId(studentId);
        FreeBoardDTO board = freeBoardDAO.findById(freeboardId).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다. id: " + freeboardId));

        HeartDTO heart = heartDAO.findByMemberAndBoard(member, board);

        if (heart != null) { // 좋아요가 이미 등록되어 있을 경우
            heart.setIsFilled(true);
            board.setCountLike(board.getCountLike() + 1);
            heartDAO.save(heart);
        } else { // 좋아요가 등록되어 있지 않은 경우
            heart = new HeartDTO();
            heart.setMember(member);
            heart.setBoard(board);
            heart.setIsFilled(true);
            board.setCountLike(board.getCountLike() + 1);
            heartDAO.save(heart);
        }
    }

    public LikeResponse getLike(String studentId, int freeboardId) {
        MemberDTO member = memberDAO.findByStudentId(studentId);
        FreeBoardDTO board = freeBoardDAO.findById(freeboardId).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다. id: " + freeboardId));
        HeartDTO heart = heartDAO.findByMemberAndBoard(member, board);

        LikeResponse response = new LikeResponse();
        response.setCountLike(board.getCountLike());
        response.setIsFilled(heart.getIsFilled());

        return response;
    }

    public void deleteLike(String studentId, int freeboardId) {
        MemberDTO member = memberDAO.findByStudentId(studentId);
        FreeBoardDTO board = freeBoardDAO.findById(freeboardId).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다. id: " + freeboardId));
        HeartDTO heart = heartDAO.findByMemberAndBoard(member, board);

        board.setCountLike(board.getCountLike() - 1);
        heartDAO.delete(heart);
    }

}