package com.tzh.shiro.filter;


import com.tzh.util.LoggerUtils;
import net.sf.json.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ShiroFilterUtils {
    final static Class<? extends ShiroFilterUtils> CLAZZ = ShiroFilterUtils.class;
    //登录页面
    static final String LOGIN_URL = "/login/jumpLogin";
    //踢出登录提示
//	final static String KICKED_OUT = "/open/kickedOut.shtml";
    //没有权限提醒
    final static String UNAUTHORIZED = "/login/unauthorized";
    /**
     * 是否是Ajax请求
     * @param request
     * @return
     */
    /**
     * 是否是Ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request) {
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(header)) {
            LoggerUtils.debug(CLAZZ, "当前请求为Ajax请求");
            return Boolean.TRUE;
        }
        LoggerUtils.debug(CLAZZ, "当前请求非Ajax请求");
        return Boolean.FALSE;
    }

    /**
     * @return void
     * @author: tangzihao
     * @methodName： out
     * @createDate: 2019/3/8 19:23
     * @param: [response, resultMap]
     * @version: 1.0
     */
    public static Boolean out(ServletResponse response, Map<String, String> resultMap) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("Content-type",
                "application/json;charset=UTF-8");
        PrintWriter out;
        try {
            out = httpServletResponse.getWriter();
            out.println("{\"code\":-1,\"msg\":\"登录用户无权执行该操作！\"}");
            out.flush();
            out.close();
        } catch (IOException e) {
            LoggerUtils.fmtError(CLAZZ, e, "输出JSON报错。");
        }
        return false;
    }
}
