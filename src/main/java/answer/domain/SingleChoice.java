package answer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SingleChoice {
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

    //试题答案 A B C D
    private String choiceAnswer;
    //选项A
    private String choiceA;
    //选项B
    private String choiceB;
    //选项C
    private String choiceC;
    //选项D
    private String choiceD;

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

    public String getChoiceAnswer() {
        return choiceAnswer;
    }

    public void setChoiceAnswer(String choiceAnswer) {
        this.choiceAnswer = choiceAnswer;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public String getQuestionAnalysis() {
        return questionAnalysis;
    }

    public void setQuestionAnalysis(String questionAnalysis) {
        this.questionAnalysis = questionAnalysis;
    }
}
