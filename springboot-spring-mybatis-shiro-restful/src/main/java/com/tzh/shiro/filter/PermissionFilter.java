package com.tzh.shiro.filter;

import com.tzh.entity.Permission;
import com.tzh.entity.Role;
import com.tzh.entity.User;
import com.tzh.util.LoggerUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class PermissionFilter extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String url = request.getRequestURI();
        if (user == null || user.equals("")) {
            return Boolean.FALSE;
        } else {
            String method = request.getMethod();
            for (Role role : user.getRoleSet()) {
                for (Permission permission : role.getPermissionSet()) {
                    String permissionUrl = permission.getUrl().substring(permission.getUrl().lastIndexOf("/") + 1);
                    //如果从数据库中查出截取到url中的最后一个"/"后的值为"*"号,
                    //就分别截取数据库和页面请求的url中的最后一个"/"后的值作比较,和数据库中的请求方式与页面的请求方式做匹配,两个条件必须都成立
                    if (permissionUrl.equals("*")) {
                        permissionUrl = permission.getUrl().substring(0, permission.getUrl().lastIndexOf("/"));
                        String newUrl = url.substring(0, url.lastIndexOf("/"));
                        if (permissionUrl.equals(newUrl) && (permission.getRequestMode()).equals(method)) {
                            return Boolean.TRUE;
                        }
                    } else {
                        if ((permission.getUrl()).equals(url) && (permission.getRequestMode()).equals(method)) {
                            return Boolean.TRUE;
                        }
                    }
                }
            }
            if (ShiroFilterUtils.isAjax(servletRequest)) {
                Map<String, String> resultMap = new HashMap<>();
                LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
                resultMap.put("login_status", "300");
                resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");//当前用户没有登录！
//            ShiroFilterUtils.out(servletResponse, resultMap);

            }
        }
        return Boolean.FALSE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || user.equals("")) {
            return Boolean.FALSE;
        } else {
            if (null == user.getUsername()) {//表示没有登录，重定向到登录页面
                saveRequest(servletRequest);
                WebUtils.issueRedirect(servletRequest, servletResponse, ShiroFilterUtils.LOGIN_URL);
            } else {
                if (StringUtils.hasText(ShiroFilterUtils.UNAUTHORIZED)) {//如果有未授权页面跳转过去
                    saveRequest(servletRequest);
                    WebUtils.issueRedirect(servletRequest, servletResponse, ShiroFilterUtils.UNAUTHORIZED);
                } else {//否则返回401未授权状态码
                    saveRequest(servletRequest);
                    WebUtils.toHttp(servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }
        return Boolean.FALSE;
    }
}
