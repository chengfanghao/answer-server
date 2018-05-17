package answer.controller;

import answer.domain.*;
import answer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(path = "/examinationPaper")
public class ExaminationPaperController {
    @Autowired
    private ExaminationPaperRepository examinationPaperRepository;
    @Autowired
    private BlankRepository blankRepository;
    @Autowired
    private SingleChoiceRepository singleChoiceRepository;
    @Autowired
    private JudgeRepository judgeRepository;
    @Autowired
    private EssayRepository essayRepository;

    @GetMapping(path = "/allExaminationPaper")
    @ResponseBody
    public Iterable<List<String>> getAllExaminationPapers(HttpServletRequest httpServletRequest) {
        Teacher teacher = (Teacher) httpServletRequest.getSession().getAttribute("teacher");
        return examinationPaperRepository.findDistinctExaminationPaper(teacher.getSubject(), teacher.getGrade());
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        examinationPaperRepository.deleteById(id);
    }

    @GetMapping(path = "/getCount")
    @ResponseBody
    public long getCount(HttpServletRequest httpServletRequest) {
        Teacher teacher = (Teacher) httpServletRequest.getSession().getAttribute("teacher");
        return examinationPaperRepository.getCountDistinctBySubjectAndGrade(teacher.getSubject(), teacher.getGrade());
    }

    @PostMapping(path = "/saveExaminationPaper", consumes = "application/json")
    @ResponseBody
    public void saveExaminationPaper(@RequestBody List<ExaminationPaper> examinationPapers) {
        for (ExaminationPaper examinationPaper : examinationPapers) {
            examinationPaperRepository.save(examinationPaper);
        }
    }

    @GetMapping(path = "/getPaper/{examinationName}")
    @ResponseBody
    public Iterable<Iterable<? extends Object>> getPaper(@PathVariable String examinationName) {
        //获取所有题型的ids
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("选择", new ArrayList<Integer>());
        map.put("填空", new ArrayList<Integer>());
        map.put("判断", new ArrayList<Integer>());
        map.put("问答", new ArrayList<Integer>());
        List<ExaminationPaper> examinationPaperList = examinationPaperRepository.findAllByExaminationName(examinationName);
        for (ExaminationPaper examinationPaper : examinationPaperList) {
            map.get(examinationPaper.getQuestionType()).add(examinationPaper.getId());
        }

        //分别查找对应的题型集合
        List<Iterable<? extends Object>> questionList = new ArrayList<>();
        questionList.add(singleChoiceRepository.findAllByIdIn(map.get("选择")));
        questionList.add(blankRepository.findAllByIdIn(map.get("填空")));
        questionList.add(judgeRepository.findAllByIdIn(map.get("判断")));
        questionList.add(essayRepository.findAllByIdIn(map.get("问答")));

        return questionList;
    }

    @PostMapping(path = "/saveCustomPaper")
    @ResponseBody
    public void saveCustomPaper(@RequestBody CustomTest customTest) {
        String examinationName = customTest.getExaminationName(),
                examinationTime = customTest.getExaminationTime();
        Random random = new Random();

        //获取选择题
        int selectLevelCount1 = customTest.getSelectLevel1();
        if (selectLevelCount1 > 0) {
            List<SingleChoice> selectList1 = singleChoiceRepository.findAllByQuestionLevel("容易");
            int selectLength1 = selectList1.size();
            if (selectLength1 < selectLevelCount1) {
                selectLevelCount1 = selectLength1;
            }
            Set<Integer> selectRecord1 = new HashSet<>();
            for (int i = 0; i < selectLevelCount1; i++) {
                int selectIndex1;
                do {
                    selectIndex1 = (int) random.nextInt(selectLength1);
                } while (selectRecord1.contains(selectIndex1));
                selectRecord1.add(selectIndex1);
                SingleChoice select1 = selectList1.get(selectIndex1);

                ExaminationPaper selectExaminationPaper1 = new ExaminationPaper();
                selectExaminationPaper1.setQuestionType("选择");
                selectExaminationPaper1.setId(select1.getId());
                selectExaminationPaper1.setExaminationName(examinationName);
                selectExaminationPaper1.setExaminationTime(examinationTime);
                examinationPaperRepository.save(selectExaminationPaper1);
            }
        }

        int selectLevelCount2 = customTest.getSelectLevel2();
        if (selectLevelCount2 > 0) {
            List<SingleChoice> selectList2 = singleChoiceRepository.findAllByQuestionLevel("常规");
            int selectLength2 = selectList2.size();
            if (selectLength2 < selectLevelCount2) {
                selectLevelCount2 = selectLength2;
            }
            Set<Integer> selectRecord2 = new HashSet<>();
            for (int i = 0; i < selectLevelCount2; i++) {
                int selectIndex2;
                do {
                    selectIndex2 = (int) random.nextInt(selectLength2);
                } while (selectRecord2.contains(selectIndex2));
                selectRecord2.add(selectIndex2);
                SingleChoice select2 = selectList2.get(selectIndex2);

                ExaminationPaper selectExaminationPaper2 = new ExaminationPaper();
                selectExaminationPaper2.setQuestionType("选择");
                selectExaminationPaper2.setId(select2.getId());
                selectExaminationPaper2.setExaminationName(examinationName);
                selectExaminationPaper2.setExaminationTime(examinationTime);
                examinationPaperRepository.save(selectExaminationPaper2);
            }
        }

        int selectLevelCount3 = customTest.getSelectLevel3();
        if (selectLevelCount3 > 0) {
            List<SingleChoice> selectList3 = singleChoiceRepository.findAllByQuestionLevel("困难");
            int selectLength3 = selectList3.size();
            if (selectLength3 < selectLevelCount3) {
                selectLevelCount3 = selectLength3;
            }
            Set<Integer> selectRecord3 = new HashSet<>();
            for (int i = 0; i < selectLevelCount3; i++) {
                int selectIndex3;
                do {
                    selectIndex3 = (int) random.nextInt(selectLength3);
                } while (selectRecord3.contains(selectIndex3));
                selectRecord3.add(selectIndex3);
                SingleChoice select3 = selectList3.get(selectIndex3);

                ExaminationPaper selectExaminationPaper3 = new ExaminationPaper();
                selectExaminationPaper3.setQuestionType("选择");
                selectExaminationPaper3.setId(select3.getId());
                selectExaminationPaper3.setExaminationName(examinationName);
                selectExaminationPaper3.setExaminationTime(examinationTime);
                examinationPaperRepository.save(selectExaminationPaper3);
            }
        }

        //获取填空题
        int blankLevelCount1 = customTest.getBlankLevel1();
        if (blankLevelCount1 > 0) {
            List<Blank> blankList1 = blankRepository.findAllByQuestionLevel("容易");
            int blankLength1 = blankList1.size();
            if (blankLength1 < blankLevelCount1) {
                blankLevelCount1 = blankLength1;
            }
            Set<Integer> blankRecord1 = new HashSet<>();
            for (int i = 0; i < blankLevelCount1; i++) {
                int blankIndex1;
                do {
                    blankIndex1 = (int) random.nextInt(blankLength1);
                } while (blankRecord1.contains(blankIndex1));
                blankRecord1.add(blankIndex1);
                Blank blank1 = blankList1.get(blankIndex1);

                ExaminationPaper blankExaminationPaper1 = new ExaminationPaper();
                blankExaminationPaper1.setQuestionType("填空");
                blankExaminationPaper1.setId(blank1.getId());
                blankExaminationPaper1.setExaminationName(examinationName);
                blankExaminationPaper1.setExaminationTime(examinationTime);
                examinationPaperRepository.save(blankExaminationPaper1);
            }
        }

        int blankLevelCount2 = customTest.getBlankLevel2();
        if (blankLevelCount2 > 0) {
            List<Blank> blankList2 = blankRepository.findAllByQuestionLevel("常规");
            int blankLength2 = blankList2.size();
            if (blankLength2 < blankLevelCount2) {
                blankLevelCount2 = blankLength2;
            }
            Set<Integer> blankRecord2 = new HashSet<>();
            for (int i = 0; i < blankLevelCount2; i++) {
                int blankIndex2;
                do {
                    blankIndex2 = (int) random.nextInt(blankLength2);
                } while (blankRecord2.contains(blankIndex2));
                blankRecord2.add(blankIndex2);
                Blank blank2 = blankList2.get(blankIndex2);

                ExaminationPaper blankExaminationPaper2 = new ExaminationPaper();
                blankExaminationPaper2.setQuestionType("填空");
                blankExaminationPaper2.setId(blank2.getId());
                blankExaminationPaper2.setExaminationName(examinationName);
                blankExaminationPaper2.setExaminationTime(examinationTime);
                examinationPaperRepository.save(blankExaminationPaper2);
            }
        }

        int blankLevelCount3 = customTest.getBlankLevel3();
        if (blankLevelCount3 > 0) {
            List<Blank> blankList3 = blankRepository.findAllByQuestionLevel("困难");
            int blankLength3 = blankList3.size();
            if (blankLength3 < blankLevelCount3) {
                blankLevelCount3 = blankLength3;
            }
            Set<Integer> blankRecord3 = new HashSet<>();
            for (int i = 0; i < blankLevelCount3; i++) {
                int blankIndex3;
                do {
                    blankIndex3 = (int) random.nextInt(blankLength3);
                } while (blankRecord3.contains(blankIndex3));
                blankRecord3.add(blankIndex3);
                Blank blank3 = blankList3.get(blankIndex3);

                ExaminationPaper blankExaminationPaper3 = new ExaminationPaper();
                blankExaminationPaper3.setQuestionType("填空");
                blankExaminationPaper3.setId(blank3.getId());
                blankExaminationPaper3.setExaminationName(examinationName);
                blankExaminationPaper3.setExaminationTime(examinationTime);
                examinationPaperRepository.save(blankExaminationPaper3);
            }
        }

        //获取判断题
        int judgeLevelCount1 = customTest.getJudgeLevel1();
        if (judgeLevelCount1 > 0) {
            List<Judge> judgeList1 = judgeRepository.findAllByQuestionLevel("容易");
            int judgeLength1 = judgeList1.size();
            if (judgeLength1 < judgeLevelCount1) {
                judgeLevelCount1 = judgeLength1;
            }
            Set<Integer> judgeRecord1 = new HashSet<>();
            for (int i = 0; i < judgeLevelCount1; i++) {
                int judgeIndex1;
                do {
                    judgeIndex1 = (int) random.nextInt(judgeLength1);
                } while (judgeRecord1.contains(judgeIndex1));
                judgeRecord1.add(judgeIndex1);
                Judge judge1 = judgeList1.get(judgeIndex1);

                ExaminationPaper judgeExaminationPaper1 = new ExaminationPaper();
                judgeExaminationPaper1.setQuestionType("判断");
                judgeExaminationPaper1.setId(judge1.getId());
                judgeExaminationPaper1.setExaminationName(examinationName);
                judgeExaminationPaper1.setExaminationTime(examinationTime);
                examinationPaperRepository.save(judgeExaminationPaper1);
            }
        }

        int judgeLevelCount2 = customTest.getJudgeLevel2();
        if (judgeLevelCount2 > 0) {
            List<Judge> judgeList2 = judgeRepository.findAllByQuestionLevel("常规");
            int judgeLength2 = judgeList2.size();
            if (judgeLength2 < judgeLevelCount2) {
                judgeLevelCount2 = judgeLength2;
            }
            Set<Integer> judgeRecord2 = new HashSet<>();
            for (int i = 0; i < judgeLevelCount2; i++) {
                int judgeIndex2;
                do {
                    judgeIndex2 = (int) random.nextInt(judgeLength2);
                } while (judgeRecord2.contains(judgeIndex2));
                judgeRecord2.add(judgeIndex2);
                Judge judge2 = judgeList2.get(judgeIndex2);

                ExaminationPaper judgeExaminationPaper2 = new ExaminationPaper();
                judgeExaminationPaper2.setQuestionType("判断");
                judgeExaminationPaper2.setId(judge2.getId());
                judgeExaminationPaper2.setExaminationName(examinationName);
                judgeExaminationPaper2.setExaminationTime(examinationTime);
                examinationPaperRepository.save(judgeExaminationPaper2);
            }
        }

        int judgeLevelCount3 = customTest.getJudgeLevel3();
        if (judgeLevelCount3 > 0) {
            List<Judge> judgeList3 = judgeRepository.findAllByQuestionLevel("困难");
            int judgeLength3 = judgeList3.size();
            if (judgeLength3 < judgeLevelCount3) {
                judgeLevelCount3 = judgeLength3;
            }
            Set<Integer> judgeRecord3 = new HashSet<>();
            for (int i = 0; i < judgeLevelCount3; i++) {
                int judgeIndex3;
                do {
                    judgeIndex3 = (int) random.nextInt(judgeLength3);
                } while (judgeRecord3.contains(judgeIndex3));
                judgeRecord3.add(judgeIndex3);
                Judge judge3 = judgeList3.get(judgeIndex3);

                ExaminationPaper judgeExaminationPaper3 = new ExaminationPaper();
                judgeExaminationPaper3.setQuestionType("判断");
                judgeExaminationPaper3.setId(judge3.getId());
                judgeExaminationPaper3.setExaminationName(examinationName);
                judgeExaminationPaper3.setExaminationTime(examinationTime);
                examinationPaperRepository.save(judgeExaminationPaper3);
            }
        }

        //获取问答题
        int essayLevelCount1 = customTest.getEssayLevel1();
        if (essayLevelCount1 > 0) {
            List<Essay> essayList1 = essayRepository.findAllByQuestionLevel("容易");
            int essayLength1 = essayList1.size();
            if (essayLength1 < essayLevelCount1) {
                essayLevelCount1 = essayLength1;
            }
            Set<Integer> essayRecord1 = new HashSet<>();
            for (int i = 0; i < essayLevelCount1; i++) {
                int essayIndex1;
                do {
                    essayIndex1 = (int) random.nextInt(essayLength1);
                } while (essayRecord1.contains(essayIndex1));
                essayRecord1.add(essayIndex1);
                Essay essay1 = essayList1.get(essayIndex1);

                ExaminationPaper essayExaminationPaper1 = new ExaminationPaper();
                essayExaminationPaper1.setQuestionType("问答");
                essayExaminationPaper1.setId(essay1.getId());
                essayExaminationPaper1.setExaminationName(examinationName);
                essayExaminationPaper1.setExaminationTime(examinationTime);
                examinationPaperRepository.save(essayExaminationPaper1);
            }
        }

        int essayLevelCount2 = customTest.getEssayLevel2();
        if (essayLevelCount2 > 0) {
            List<Essay> essayList2 = essayRepository.findAllByQuestionLevel("常规");
            int essayLength2 = essayList2.size();
            if (essayLength2 < essayLevelCount2) {
                essayLevelCount2 = essayLength2;
            }
            Set<Integer> essayRecord2 = new HashSet<>();
            for (int i = 0; i < essayLevelCount2; i++) {
                int essayIndex2;
                do {
                    essayIndex2 = (int) random.nextInt(essayLength2);
                } while (essayRecord2.contains(essayIndex2));
                essayRecord2.add(essayIndex2);
                Essay essay2 = essayList2.get(essayIndex2);

                ExaminationPaper essayExaminationPaper2 = new ExaminationPaper();
                essayExaminationPaper2.setQuestionType("问答");
                essayExaminationPaper2.setId(essay2.getId());
                essayExaminationPaper2.setExaminationName(examinationName);
                essayExaminationPaper2.setExaminationTime(examinationTime);
                examinationPaperRepository.save(essayExaminationPaper2);
            }
        }

        int essayLevelCount3 = customTest.getEssayLevel3();
        if (essayLevelCount3 > 0) {
            List<Essay> essayList3 = essayRepository.findAllByQuestionLevel("困难");
            int essayLength3 = essayList3.size();
            if (essayLength3 < essayLevelCount3) {
                essayLevelCount3 = essayLength3;
            }
            Set<Integer> essayRecord3 = new HashSet<>();
            for (int i = 0; i < essayLevelCount3; i++) {
                int essayIndex3;
                do {
                    essayIndex3 = (int) random.nextInt(essayLength3);
                } while (essayRecord3.contains(essayIndex3));
                essayRecord3.add(essayIndex3);
                Essay essay3 = essayList3.get(essayIndex3);

                ExaminationPaper essayExaminationPaper3 = new ExaminationPaper();
                essayExaminationPaper3.setQuestionType("问答");
                essayExaminationPaper3.setId(essay3.getId());
                essayExaminationPaper3.setExaminationName(examinationName);
                essayExaminationPaper3.setExaminationTime(examinationTime);
                examinationPaperRepository.save(essayExaminationPaper3);
            }
        }
    }
}
