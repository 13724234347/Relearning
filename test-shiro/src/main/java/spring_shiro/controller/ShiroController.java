package spring_shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
public class ShiroController {

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,@RequestParam("password") String password){


        System.out.println("-------------");
        Subject subject = SecurityUtils.getSubject();
        try {
            username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        try {
            subject.login(token);
        }catch (AuthenticationException e){
            System.out.println("登入失败"+ e.getMessage());
            return "/shiro-login";
        }
        return "/shiro-success";
    }
}
