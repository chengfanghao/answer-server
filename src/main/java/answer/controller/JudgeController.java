package answer.controller;

import answer.domain.Judge;
import answer.repository.JudgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/judge")
@CrossOrigin
public class JudgeController {
    @Autowired
    private JudgeRepository judgeRepository;

    @PostMapping(path={"/add","/update"}, consumes = "application/json")
    public @ResponseBody String addOrUpdateJudge (@RequestBody Judge judge) {
        judgeRepository.save(judge);
        return "Success";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Judge> getAllJudges() {
        // This returns a JSON or XML with the judges
        return judgeRepository.findAll();
    }

    @GetMapping(path="/getCount")
    public @ResponseBody long getCount(){
        return  judgeRepository.count();
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        judgeRepository.deleteById(id);
    }

    @GetMapping(path="/getById/{id}")
    public @ResponseBody Optional<Judge> getJudgeById(@PathVariable int id) {
        return judgeRepository.findById(id);
    }

    @GetMapping(path="/getPage{pageNumber}")
    public @ResponseBody Iterable<Judge> getPageData(@PathVariable int pageNumber){
        return judgeRepository.findByQuestionStatusAndTestSubject("开放","语文",new PageRequest(pageNumber,7));
    }
}