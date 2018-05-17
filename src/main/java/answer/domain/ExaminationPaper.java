package answer.domain;

import javax.persistence.*;

@Entity
public class ExaminationPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paperId;

    //试题名称
    private String examinationName;

    //考试时长
    private String examinationTime;

    //试题在相应类型表中的id
    private Integer id;

    //试题的中文类型
    private String questionType;

    //学科
    private String subject;

    //年级
    private String grade;

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }

    public String getExaminationTime() {
        return examinationTime;
    }

    public void setExaminationTime(String examinationTime) {
        this.examinationTime = examinationTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "ExaminationPaper{" +
                "paperId=" + paperId +
                ", examinationName='" + examinationName + '\'' +
                ", examinationTime='" + examinationTime + '\'' +
                ", id=" + id +
                ", questionType='" + questionType + '\'' +
                '}';
    }
}
