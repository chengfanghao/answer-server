package answer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Judge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //试题难度
    private String questionLevel;
    //试题科目
    private String testSubject;
    //试题来源
    private String questionSource;
    //试题状态
    private String questionStatus;
    //试题题干
    private String titleName;
    //试题答案
    private String judgeAnswer;
    //试题解析
    private String questionAnalysis;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionLevel() {
        return questionLevel;
    }

    public void setQuestionLevel(String questionLevel) {
        this.questionLevel = questionLevel;
    }

    public String getTestSubject() {
        return testSubject;
    }

    public void setTestSubject(String testSubject) {
        this.testSubject = testSubject;
    }

    public String getQuestionSource() {
        return questionSource;
    }

    public void setQuestionSource(String questionSource) {
        this.questionSource = questionSource;
    }

    public String getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(String questionStatus) {
        this.questionStatus = questionStatus;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getJudgeAnswer() {
        return judgeAnswer;
    }

    public void setJudgeAnswer(String judgeAnswer) {
        this.judgeAnswer = judgeAnswer;
    }

    public String getQuestionAnalysis() {
        return questionAnalysis;
    }

    public void setQuestionAnalysis(String questionAnalysis) {
        this.questionAnalysis = questionAnalysis;
    }
}
