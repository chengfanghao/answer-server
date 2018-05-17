package answer.repository;

import answer.domain.Teacher;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Integer> {

    Teacher findById(String id);

    void deleteById(String id);


}