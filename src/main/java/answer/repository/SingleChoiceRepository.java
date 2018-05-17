package answer.repository;

import answer.domain.SingleChoice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SingleChoiceRepository extends PagingAndSortingRepository<SingleChoice, Integer> {
    Slice<SingleChoice> findByQuestionStatusAndTestSubjectAndGrade(String questionStatus, String testSubject, String grade, Pageable pageable);

    List<SingleChoice> findAllByQuestionLevel(String questionLevel);

    List<SingleChoice> findAllByIdIn(List<Integer> var1);

    long countByTestSubjectAndGrade(String testSubject, String grade);
}