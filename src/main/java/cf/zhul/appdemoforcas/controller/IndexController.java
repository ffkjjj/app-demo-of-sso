package cf.zhul.appdemoforcas.controller;

import cf.zhul.appdemoforcas.domain.UserDomain;
import cf.zhul.appdemoforcas.vos.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhul
 * @date 2020/6/6 00:53
 * @description
 */
@RestController
public class IndexController {

    @GetMapping("/hello/get")
    public Result<UserDomain> getHello(HttpServletRequest httpServletRequest){
        return new Result<>(new UserDomain(httpServletRequest.getRemoteUser()));
    }

    @PostMapping("/hello/post")
    public Result<Void> postHello(){
        return new Result<>();
    }

    @GetMapping("/checkTicket")
    public void index(HttpServletResponse response) throws IOException {
        // 前端页面地址
        response.sendRedirect("http://localhost:8888/app");
    }
}
