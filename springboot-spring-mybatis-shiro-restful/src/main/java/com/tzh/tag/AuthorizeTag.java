package com.tzh.tag;

import com.tzh.entity.Permission;
import com.tzh.entity.User;
import com.tzh.service.Permission.PermissionService;
import com.tzh.util.StringUtils;
import javafx.scene.Parent;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.Set;


/**
 * 自定义标签  用于前台判断按钮权限
 * ALL权限指的是---拥有相应的所有权限
 *
 * @author A
 */
@Component
public class AuthorizeTag extends BodyTagSupport {
    private static final long serialVersionUID = 1L;
    @Autowired
    private PermissionService permissionService;


    private static AuthorizeTag serverHandler;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {  // 初始化调用获取Service实例
        serverHandler = this;
        serverHandler.permissionService = this.permissionService;
        // 初使化时将已静态化的testService实例化
    }

    /**
     * 请求路径
     */
    private String buttonUrl;
    /**
     * 请求方式
     */
    private String buttonMethod;

    public String getButtonUrl() {
        return buttonUrl;
    }


    public void setButtonUrl(String buttonUrl) {
        this.buttonUrl = buttonUrl;
    }

    public String getButtonMethod() {
        return buttonMethod;
    }


    public void setButtonMethod(String buttonMethod) {
        this.buttonMethod = buttonMethod;
    }


    @SuppressWarnings("static-access")
    @Override
    public int doStartTag() throws JspException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || user.equals("")) {
            System.out.println("没有任何用户登录");
        } else {
            Set<Permission> queryAll = serverHandler.permissionService.queryAll();
            String name = user.getUsername();
            boolean flag = true;
//		/**根据字符串提取最后一个"/"之前的所有字符串*/
//        buttonUrl = StringUtils.substringBeforeLast(buttonUrl, "/");
//        System.out.println(buttonUrl);
//		/**根据","分隔获取多个请求方式(可单个)*/
            String date[] = buttonMethod.split(",");
            if (1 < date.length) {
                for (int i = 0; i < date.length; i++) {
                    for (Permission permission : queryAll) {
                        /**判断，如果有相应的权限对应，则会进入验证*/
                        if (permission.getUrl().equals(buttonUrl) && (permission.getRequestMode().equals(date[i]) || permission.getRequestMode().equals("ALL")))
                            flag = false;
                    }
                }
            } else {
                for (Permission permission : queryAll) {
                    /**判断，如果有相应的权限对应，则会进入验证*/
                    if (permission.getUrl().equals(buttonUrl) && (permission.getRequestMode().equals(buttonMethod) || permission.getRequestMode().equals("ALL")))
                        flag = false;
                }
            }
            if (flag) //数据库中没有该链接，直接显示
                return EVAL_BODY_INCLUDE;
            else {
                Permission permission = new Permission();
                permission.setUsername(name);
                permission.setUrl(buttonUrl);
                if (1 < date.length) {
                    for (int i = 0; i < date.length; i++) {
                        /**获取每个请求方式*/
                        permission.setRequestMode(date[i]);
                        Integer value = serverHandler.permissionService.authorTag(permission);//loadMenu(resources);
                        if (value > 0) return EVAL_BODY_INCLUDE;//数据库中有该链接，并且该用户拥有该角色，显示
                    }
                } else {
                    permission.setRequestMode(buttonMethod);
                    /**根据用户名+url+method查询用户是否有权限显示*/
                    Integer value = serverHandler.permissionService.authorTag(permission);//loadMenu(resources);
                    if (value > 0) return EVAL_BODY_INCLUDE;//数据库中有该链接，并且该用户拥有该角色，显示
                }
            }
        }
        return this.SKIP_BODY;  //不显示
    }

}
