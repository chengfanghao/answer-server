package answer.controller;

import answer.domain.ExaminationPaper;
import answer.domain.SingleChoice;
import answer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(path = "/examinationPaper")
@CrossOrigin
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
    public Iterable<List<String>> getAllExaminationPapers() {
        return examinationPaperRepository.findDistinctExaminationPaper();
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        examinationPaperRepository.deleteById(id);
    }

    @GetMapping(path = "/getCount")
    @ResponseBody
    public long getCount() {
        return examinationPaperRepository.count();
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
    public Iterable<Iterable<? extends Object>> getPaper(@PathVariable  String examinationName) {
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
        questionList.add(singleChoiceRepository.findAllById(map.get("选择")));
        questionList.add(blankRepository.findAllById(map.get("填空")));
        questionList.add(judgeRepository.findAllById(map.get("判断")));
        questionList.add(essayRepository.findAllById(map.get("问答")));

        return questionList;
    }
}