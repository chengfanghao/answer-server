package answer.repository;

import answer.domain.Blank;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BlankRepository extends PagingAndSortingRepository<Blank, Integer> {
    Slice<Blank> findByQuestionStatusAndTestSubjectAndGrade(String questionStatus, String testSubject, String grade, Pageable pageable);

    List<Blank> findAllByQuestionLevel(String questionLevel);

    List<Blank> findAllByIdIn(List<Integer> var1);

    long countByTestSubjectAndGrade(String testSubject, String grade);
}