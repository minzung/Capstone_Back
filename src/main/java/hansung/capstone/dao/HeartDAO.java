package hansung.capstone.dao;

import hansung.capstone.dto.FreeBoardDTO;
import hansung.capstone.dto.HeartDTO;
import hansung.capstone.dto.MemberDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartDAO extends JpaRepository<HeartDTO, Integer> {

    @Query("SELECT h FROM heart h WHERE h.member = :member AND h.board = :board")
    HeartDTO findByMemberAndBoard(MemberDTO member, FreeBoardDTO board);

}
