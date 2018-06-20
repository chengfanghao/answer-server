package answer.repository;

import answer.domain.Manager;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ManagerRepository extends PagingAndSortingRepository<Manager, String> {
}