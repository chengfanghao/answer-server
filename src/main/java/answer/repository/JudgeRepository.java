package answer.repository;

import answer.domain.Judge;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface JudgeRepository extends PagingAndSortingRepository<Judge, Integer> {
    Slice<Judge> findByQuestionStatusAndTestSubjectAndGrade(String questionStatus, String testSubject, String grade, Pageable pageable);

    List<Judge> findAllByQuestionLevel(String questionLevel);

    List<Judge> findAllByIdIn(List<Integer> var1);

    long countByTestSubjectAndGrade(String testSubject, String grade);
}