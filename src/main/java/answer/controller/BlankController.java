package answer.controller;

import answer.domain.Blank;
import answer.domain.Teacher;
import answer.repository.BlankRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/blank")
public class BlankController {
    @Autowired
    private BlankRepository blankRepository;

    @PostMapping(path = {"/add", "/update"}, consumes = "application/json")
    @ResponseBody
    public String addOrUpdateBlank(@RequestBody Blank blank) {
        blankRepository.save(blank);
        return "Success";
    }

    @GetMapping(path = "/getById/{id}")
    @ResponseBody
    public Optional<Blank> getBlankById(@PathVariable int id) {
        return blankRepository.findById(id);
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Blank> getAllBlanks() {
        return blankRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        blankRepository.deleteById(id);
    }

    @GetMapping(path = "/getCount")
    @ResponseBody
    public long getCount(HttpServletRequest httpServletRequest) {
        Teacher teacher = (Teacher) httpServletRequest.getSession().getAttribute("teacher");
        return blankRepository.countByTestSubjectAndGrade(teacher.getSubject(), teacher.getGrade());
    }

    @GetMapping(path = "/getPage{pageNumber}")
    @ResponseBody
    public Iterable<Blank> getPageData(HttpServletRequest httpServletRequest, @PathVariable int pageNumber) {
        Teacher teacher = (Teacher) httpServletRequest.getSession().getAttribute("teacher");
        return blankRepository.findByQuestionStatusAndTestSubjectAndGrade("开放", teacher.getSubject(), teacher.getGrade(), new PageRequest(pageNumber, 7));
    }
}