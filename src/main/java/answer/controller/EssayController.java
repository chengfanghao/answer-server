package answer.controller;

import answer.domain.Essay;
import answer.repository.EssayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/essay")
@CrossOrigin
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
    public @ResponseBody long getCount(){
        return  essayRepository.count();
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        essayRepository.deleteById(id);
    }

    @GetMapping(path="/getPage{pageNumber}")
    public @ResponseBody Iterable<Essay> getPageData(@PathVariable int pageNumber){
        return essayRepository.findByQuestionStatusAndTestSubject("开放","语文",new PageRequest(pageNumber,7));
    }
}