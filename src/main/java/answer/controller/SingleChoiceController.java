package answer.controller;

import answer.domain.SingleChoice;
import answer.domain.Teacher;
import answer.repository.SingleChoiceRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/singleChoice")
public class SingleChoiceController {
    @Autowired
    private SingleChoiceRepository singleChoiceRepository;

    @PostMapping(path = {"/add", "/update"}, consumes = "application/json")
    public @ResponseBody
    String addOrUpdateSingleChoice(@RequestBody SingleChoice singleChoice) {
        singleChoiceRepository.save(singleChoice);
        return "Success";
    }

    @GetMapping(path = "/getById/{id}")
    public @ResponseBody
    Optional<SingleChoice> getSingleChoiceById(@PathVariable int id) {
        return singleChoiceRepository.findById(id);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<SingleChoice> getAllSingleChoices() {
        // This returns a JSON or XML with the singleChoices
        return singleChoiceRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        singleChoiceRepository.deleteById(id);
    }

    @GetMapping(path = "/getCount")
    @ResponseBody
    public long getCount(HttpServletRequest httpServletRequest) {
        Teacher teacher = (Teacher) httpServletRequest.getSession().getAttribute("teacher");
        return singleChoiceRepository.countByTestSubjectAndGrade(teacher.getSubject(), teacher.getGrade());
    }

    @GetMapping(path = "/getPage{pageNumber}")
    @ResponseBody
    public Iterable<SingleChoice> getPageData(HttpServletRequest httpServletRequest, @PathVariable int pageNumber) {
        Teacher teacher = (Teacher) httpServletRequest.getSession().getAttribute("teacher");
        return singleChoiceRepository.findByQuestionStatusAndTestSubjectAndGrade("开放", teacher.getSubject(), teacher.getGrade(), new PageRequest(pageNumber, 7));
    }
}