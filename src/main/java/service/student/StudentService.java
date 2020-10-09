package service.student;

import model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentService implements IStudentService {
    private static Map<Long, Student> listStudent;
    static {
        listStudent = new HashMap<>();
        listStudent.put(1L, new Student(1L, "Bao", "Thai Nguyen", "1.png"));
        listStudent.put(2L, new Student(2L,"Bao1", "Thai Nguyen1", "2.png"));
        listStudent.put(3L, new Student(3L,"Bao2", "Thai Nguyen2", "1.png"));
        listStudent.put(4L, new Student(4L,"Bao3", "Thai Nguyen3", "1.png"));
    }

    @Override
    public List<Student> findAll() {
        ArrayList list = new ArrayList<>(listStudent.values());
        return list;
    }

    @Override
    public Student findById(Long id) {
        return listStudent.get(id);
    }

    @Override
    public void update(Student model) {
        listStudent.put(model.getId(), model);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void save(Student model) {
        Long stt = listStudent.size() + 1L;
        model.setId(stt);
        listStudent.put(stt, model);

    }
}
