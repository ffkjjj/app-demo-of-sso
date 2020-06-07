package cf.zhul.appdemoforcas.controller;

import cf.zhul.appdemoforcas.vos.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhul
 * @date 2020/6/6 00:53
 * @description
 */
@Controller
public class IndexController {

    @GetMapping("/hello/get")
    @ResponseBody
    public Result<Void> getHello(){
        return new Result<>();
    }

    @PostMapping("/hello/post")
    @ResponseBody
    public Result<Void> postHello(){
        return new Result<>();
    }

    @GetMapping("/checkTicket")
    public void index(HttpServletResponse response) throws IOException {
        // 前端页面地址
        response.sendRedirect("http://localhost:8888/app");
    }
}
