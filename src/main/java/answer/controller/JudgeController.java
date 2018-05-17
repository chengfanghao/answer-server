package answer.controller;

import answer.domain.Judge;
import answer.domain.Teacher;
import answer.repository.JudgeRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/judge")
public class JudgeController {
    @Autowired
    private JudgeRepository judgeRepository;

    @PostMapping(path = {"/add", "/update"}, consumes = "application/json")
    public @ResponseBody
    String addOrUpdateJudge(@RequestBody Judge judge) {
        judgeRepository.save(judge);
        return "Success";
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Judge> getAllJudges() {
        // This returns a JSON or XML with the judges
        return judgeRepository.findAll();
    }

    @GetMapping(path = "/getCount")
    @ResponseBody
    public long getCount(HttpServletRequest httpServletRequest) {
        Teacher teacher = (Teacher) httpServletRequest.getSession().getAttribute("teacher");
        return judgeRepository.countByTestSubjectAndGrade(teacher.getSubject(), teacher.getGrade());
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        judgeRepository.deleteById(id);
    }

    @GetMapping(path = "/getById/{id}")
    public @ResponseBody
    Optional<Judge> getJudgeById(@PathVariable int id) {
        return judgeRepository.findById(id);
    }

    @GetMapping(path = "/getPage{pageNumber}")
    public @ResponseBody
    Iterable<Judge> getPageData(HttpServletRequest httpServletRequest, @PathVariable int pageNumber) {
        Teacher teacher = (Teacher) httpServletRequest.getSession().getAttribute("teacher");
        return judgeRepository.findByQuestionStatusAndTestSubjectAndGrade("开放", teacher.getSubject(), teacher.getGrade(), new PageRequest(pageNumber, 7));
    }
}