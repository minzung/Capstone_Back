package hansung.capstone.service;

import hansung.capstone.dao.TimeDAO;
import hansung.capstone.dto.TimeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeDAO dao;

    public TimeDTO saveTime(TimeDTO timeDTO) {
        return dao.save(timeDTO);
    }

    public List<TimeDTO> getTime(String studentId) {
        return dao.findAllByStudentId(studentId);
    }

    public void deleteTime(String studentId, int id) {
        TimeDTO time = dao.findById(id);
        if (Objects.equals(time.getStudentId(), studentId)) {
            dao.deleteById(id);
        }
    }

}
