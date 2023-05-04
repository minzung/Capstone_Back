package hansung.capstone.service;

import hansung.capstone.dao.TimeDAO;
import hansung.capstone.dto.TimeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeDAO dao;

    public TimeDTO saveTime(TimeDTO timeDTO) {
        return dao.save(timeDTO);
    }

    public List<TimeDTO> getTime(int studentId) {
        return dao.findAllByStudentId(studentId);
    }

    public void deleteTime(int id) {
        dao.deleteById(id);
    }


}
