package answer.repository;

import answer.domain.Essay;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface EssayRepository extends PagingAndSortingRepository<Essay, Integer> {
    Slice<Essay> findByQuestionStatusAndTestSubject(String questionStatus, String testSubject, Pageable pageable);
}