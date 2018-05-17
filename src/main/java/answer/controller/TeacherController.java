package answer.controller;

import answer.domain.Teacher;
import answer.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/teacher")
public class TeacherController {
    @Autowired
    private TeacherRepository teacherRepository;

    @PostMapping(path = {"/login"}, consumes = "application/json")
    @ResponseBody
    public String addOrUpdateTeacher(HttpServletRequest httpServletRequest, @RequestBody Teacher teacher) {
        Teacher dbTeacher = teacherRepository.findById(teacher.getId());

        if (dbTeacher == null || !dbTeacher.getPassword().equals(teacher.getPassword())) {
            return "fail";
        }

        httpServletRequest.getSession().setAttribute("teacher", dbTeacher);

        return "addExaminationPaper";
    }

    @GetMapping(path = {"/getUser"})
    @ResponseBody
    public Teacher getTeacherSession(HttpServletRequest httpServletRequest) {
        return (Teacher) httpServletRequest.getSession().getAttribute("teacher");
    }

    @GetMapping(path = {"/getTeachers"})
    @ResponseBody
    public Iterable<Teacher> getAllTeachers() {
        Iterable<Teacher> teachers = teacherRepository.findAll();
        return teacherRepository.findAll();
    }

    @GetMapping(path = {"/deleteTeacher/{id}"})
    @ResponseBody
    @Transactional
    public String getAllTeachers(@PathVariable String id) {
        teacherRepository.deleteById(id);
        return "success";
    }

    @GetMapping(path = "/getCount")
    @ResponseBody
    public long getCount() {
        return teacherRepository.count();
    }

    @PostMapping(path = {"/add"})
    @ResponseBody
    public String addNewTeacher(@RequestBody Teacher teacher) {
        teacher.setPassword(DigestUtils.md5DigestAsHex("123".getBytes()));
        teacherRepository.save(teacher);
        return "Success";
    }

}