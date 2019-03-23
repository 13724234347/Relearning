package com.tzh.controller;

import com.tzh.entity.User;
import com.tzh.service.login.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;

import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private LoginService loginService;

    //跳转到登录表单页面
    @RequestMapping(value = "/jumpLogin", method = RequestMethod.GET)
    public String jumpLogin() {
        return "login";
    }

    //跳转到角色页面
    @RequestMapping(value = "/jumpRole", method = RequestMethod.GET)
    public String jumpRole() {
        return "role";
    }

    //跳转到用户页面
    @RequestMapping(value = "/jumpUser", method = RequestMethod.GET)
    public String jumpUser() {
        return "user";
    }

    //跳转到权限页面
    @RequestMapping(value = "/jumpPermission", method = RequestMethod.GET)
    public String jumpPermission() {
        return "permission";
    }

    //跳转没有权限页面
    @RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
    public String jumPunauthorized() {
        return "unauthorized";
    }


    //验证登录信息是否通过
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, Map<String, Object> map, Boolean rememberMe) {
        if (user.getUsername() != null && user.getPassword() != null) {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(), "login");
            Subject subject = SecurityUtils.getSubject();
            logger.info("对用户[" + user.getUsername() + "]进行登录验证..验证开始");
            try {
                subject.login(token);
                SecurityUtils.getSubject().isPermitted(user.getUsername());
                loginService.setLastLoginTime(user.getUsername());
                if (subject.isAuthenticated()) {
                    logger.info("用户[" + user.getUsername() + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
                    System.out.println("用户[" + user.getUsername() + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
                    return "user";
                }
            } catch (UnknownAccountException uae) {
                logger.info("对用户[" + user.getUsername() + "]进行登录验证..验证失败-用户名不在系统中");
                System.out.println("对用户[" + user.getUsername() + "]进行登录验证..验证失败-用户名不在系统中");
            } catch (IncorrectCredentialsException ice) {
                logger.info("对用户[" + user.getUsername() + "]进行登录验证..验证失败-密码不匹配");
                System.out.println("对用户[" + user.getUsername() + "]进行登录验证..验证失败-密码不匹配");
            } catch (LockedAccountException lae) {
                logger.info("对用户[" + user.getUsername() + "]进行登录验证..验证失败-帐户已锁定在系统中");
                System.out.println("对用户[" + user.getUsername() + "]进行登录验证..验证失败-帐户已锁定在系统中");
            } catch (AuthenticationException ae) {
                logger.error(ae.getMessage());
                token.clear();
                System.out.println("用户[" + user.getUsername() + "]登录认证失败,重新登陆");
                return "login";
            }
        }
        return "login";
    }
}