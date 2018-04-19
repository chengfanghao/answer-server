package answer.repository;

import answer.domain.Blank;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BlankRepository extends PagingAndSortingRepository<Blank, Integer> {
    Slice<Blank> findByQuestionStatusAndTestSubject(String questionStatus, String testSubject, Pageable pageable);
}