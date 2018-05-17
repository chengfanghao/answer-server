package answer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/")
public class MainController {
    @GetMapping(path = "loginOut")
    @ResponseBody
    public String loginOut(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();
        return "success";
    }

    //判断session是否存活，防止直接申请登录后的页面 1.放行 2.跳转到登录界面
}
