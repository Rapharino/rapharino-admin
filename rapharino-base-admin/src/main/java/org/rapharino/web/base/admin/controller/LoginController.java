package org.rapharino.web.base.admin.controller;

import com.google.common.base.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Rapharino on 2016/12/25.
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "rememberMe", required = true) boolean rememberMe){

//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        token.setRememberMe(rememberMe);
//        try {
//            subject.login(token);
//        } catch (UnknownAccountException|IncorrectCredentialsException e) {
//            error = "用户名/密码错误";
//        }catch (AuthenticationException e) {
//            //其他错误，比如锁定，如果想单独处理请单独catch处理
//            error = "其他错误：" + e.getMessage();
//        }
//        if(error != null) {//出错了，返回登录页面
//            req.setAttribute("error", error);
//            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
//        } else {//登录成功
//            req.getRequestDispatcher("/WEB-INF/jsp/loginSuccess.jsp").forward(req, resp);
//        }
        return null;
    }
}
