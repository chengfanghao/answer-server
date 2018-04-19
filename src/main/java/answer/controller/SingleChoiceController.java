package answer.controller;

import answer.domain.SingleChoice;
import answer.repository.SingleChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/singleChoice")
@CrossOrigin
public class SingleChoiceController {
    @Autowired
    private SingleChoiceRepository singleChoiceRepository;

    @PostMapping(path={"/add","/update"}, consumes = "application/json")
    public @ResponseBody String addOrUpdateSingleChoice (@RequestBody SingleChoice singleChoice) {
        singleChoiceRepository.save(singleChoice);
        return "Success";
    }

    @GetMapping(path="/getById/{id}")
    public @ResponseBody Optional<SingleChoice> getSingleChoiceById(@PathVariable int id) {
        return singleChoiceRepository.findById(id);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<SingleChoice> getAllSingleChoices() {
        // This returns a JSON or XML with the singleChoices
        return singleChoiceRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        singleChoiceRepository.deleteById(id);
    }

    @GetMapping(path="/getCount")
    public @ResponseBody long getCount(){
        return  singleChoiceRepository.count();
    }

    @GetMapping(path="/getPage{pageNumber}")
    public @ResponseBody Iterable<SingleChoice> getPageData(@PathVariable int pageNumber){
        return singleChoiceRepository.findByQuestionStatusAndTestSubject("开放","语文",new PageRequest(pageNumber,7));
    }
}