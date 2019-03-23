package com.tzh.shiro.filter;

import com.tzh.entity.User;
import com.tzh.shiro.manager.TokenManager;
import com.tzh.util.LoggerUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        User token = TokenManager.getToken();
        if(null != token || isLoginRequest(servletRequest, servletResponse)){// && isEnabled()
            return Boolean.TRUE;
        }
        if (ShiroFilterUtils.isAjax(servletRequest)) {// ajax请求
            Map<String,String> resultMap = new HashMap<>();
            LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
            resultMap.put("login_status", "300");
            resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");//当前用户没有登录！
            ShiroFilterUtils.out(servletResponse, resultMap);
        }
        return Boolean.FALSE ;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //保存Request和Response 到登录后的链接
        saveRequestAndRedirectToLogin(servletRequest, servletResponse);
        return false;
    }
}
