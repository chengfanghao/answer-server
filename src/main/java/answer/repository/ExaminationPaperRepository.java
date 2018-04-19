package answer.repository;

import answer.domain.ExaminationPaper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ExaminationPaperRepository extends PagingAndSortingRepository<ExaminationPaper, Integer> {
    @Query(value = "SELECT DISTINCT examination_name,examination_time FROM examination_paper", nativeQuery = true)
    List<List<String>> findDistinctExaminationPaper();

    List<ExaminationPaper> findAllByExaminationName(String examinationName);
}