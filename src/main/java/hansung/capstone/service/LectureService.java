package hansung.capstone.service;

import hansung.capstone.dao.LectureDAO;
import hansung.capstone.dto.LectureDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureDAO dao;

    public LectureDTO register(LectureDTO lectureDTO) {
        return dao.save(lectureDTO);
    }

    public Optional<LectureDTO> getLectureById(int id) {
        return dao.findById(id);
    }

    public List<LectureDTO> getAllLectures() {
        return dao.findAll();
    }

    public void deleteLecture(int id) {
        dao.deleteById(id);
    }

}
