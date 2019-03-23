package com.tzh.shiro.filter;

import com.tzh.entity.Role;
import com.tzh.entity.User;
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

public class RoleFilter extends AccessControlFilter {

    static final String LOGIN_URL = "login";
    static final String UNAUTHORIZED_URL = "unauthorized";


    /**
     * @author:        tangzihao
     * @methodName：   isAccessAllowed
     * @createDate:    2019/3/9 19:35
     * @param:         [servletRequest, servletResponse, o]
     * @return         boolean
     * @version:       1.0
     *
     * 返回true，如果请求被允许通过过滤器正常进行，或者false 如果请求应由处理 onAccessDenied(request,response,mappedValue) 方法来代替。
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse,Object o) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
       //获取与请求关联的主题的便捷方法。
        Subject subject = getSubject(servletRequest, servletResponse);
        for (Role role:user.getRoleSet()){
            if (subject.hasRole(role.getRoleName())){
                return true;
            }
        }
        return false;
    }

    /**
     * @author:        tangzihao
     * @methodName：   onAccessDenied
     * @createDate:    2019/3/9 19:36
     * @param:         [servletRequest, servletResponse]
     * @return         boolean
     * @version:       1.0
     *
     * 根据isAccessAllowed 方法确定拒绝主体被拒绝访问的请求 。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        Subject subject = getSubject(servletRequest, servletResponse);
        if (subject.getPrincipal() == null) {//表示没有登录，重定向到登录页面
            saveRequest(servletRequest);
            WebUtils.issueRedirect(servletRequest, servletResponse, LOGIN_URL);
        } else {
            if (StringUtils.hasText(UNAUTHORIZED_URL)) {//如果有未授权页面跳转过去
                WebUtils.issueRedirect(servletRequest, servletResponse, UNAUTHORIZED_URL);
            } else {//否则返回401未授权状态码
                WebUtils.toHttp(servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }
}
