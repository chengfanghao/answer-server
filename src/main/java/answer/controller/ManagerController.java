package answer.controller;

import answer.domain.Manager;
import answer.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/manager")
public class ManagerController {
    @Autowired
    private ManagerRepository managerRepository;

    @PostMapping(path = {"/login"}, consumes = "application/json")
    @ResponseBody
    public String addOrUpdateManager(HttpServletRequest httpServletRequest, @RequestBody Manager manager) {
        Manager dbManager = managerRepository.findById(manager.getId()).get();

        if (dbManager == null || !dbManager.getPassword().equals(manager.getPassword())) {
            return "fail";
        }

        httpServletRequest.getSession().setAttribute("manager", dbManager);

        return "managerMainPage";
    }

    @GetMapping(path = {"/getUser"})
    @ResponseBody
    public Manager getManagerSession(HttpServletRequest httpServletRequest) {
        return (Manager) httpServletRequest.getSession().getAttribute("manager");
    }
}