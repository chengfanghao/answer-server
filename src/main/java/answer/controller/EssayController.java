package answer.controller;

import answer.domain.Essay;
import answer.domain.Teacher;
import answer.repository.EssayRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/essay")
public class EssayController {
    @Autowired
    private EssayRepository essayRepository;

    @PostMapping(path={"/add","/update"}, consumes = "application/json")
    public @ResponseBody String addOrUpdateEssay (@RequestBody Essay essay) {
        essayRepository.save(essay);
        return "Success";
    }

    @GetMapping(path="/getById/{id}")
    public @ResponseBody Optional<Essay> getEssayById(@PathVariable int id) {
        return essayRepository.findById(id);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Essay> getAllEssays() {
        // This returns a JSON or XML with the essays
        return essayRepository.findAll();
    }

    @GetMapping(path="/getCount")
    @ResponseBody
    public long getCount(HttpServletRequest httpServletRequest) {
        Teacher teacher = (Teacher) httpServletRequest.getSession().getAttribute("teacher");
        return essayRepository.countByTestSubjectAndGrade(teacher.getSubject(), teacher.getGrade());
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        essayRepository.deleteById(id);
    }

    @GetMapping(path="/getPage{pageNumber}")
    public @ResponseBody Iterable<Essay> getPageData(HttpServletRequest httpServletRequest,@PathVariable int pageNumber){
        Teacher teacher = (Teacher) httpServletRequest.getSession().getAttribute("teacher");
        return essayRepository.findByQuestionStatusAndTestSubjectAndGrade("开放",teacher.getSubject(), teacher.getGrade(),new PageRequest(pageNumber,7));
    }
}