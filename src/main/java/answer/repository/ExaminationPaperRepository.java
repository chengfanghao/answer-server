package answer.repository;

import answer.domain.ExaminationPaper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ExaminationPaperRepository extends PagingAndSortingRepository<ExaminationPaper, Integer> {
    @Query(value = "SELECT DISTINCT examination_name,examination_time FROM examination_paper WHERE subject=?1 AND grade=?2", nativeQuery = true)
    List<List<String>> findDistinctExaminationPaper(String subject, String grade);

    List<ExaminationPaper> findAllByExaminationName(String examinationName);

    @Query(value = "SELECT count(DISTINCT examination_name,examination_time) FROM examination_paper WHERE subject=?1 AND grade=?2", nativeQuery = true)
    long getCountDistinctBySubjectAndGrade(String subject, String grade);
}