package com.tzh.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        String user = request.getParameter("user");// 得到用户名
        String pd = request.getParameter("pd");// 得到密码
        String randomCode = request.getParameter("randomCode");// 得到验证码
        System.out.println(request.getSession().getAttribute("rand"));// 打出存在于session里面的验证码
        // 刚开始登录的审核提示验证码是隐藏的，所以等于空
        String hiddenCode = request.getParameter("hiddenCode");// 第一次进来hiddenCode等于空

        if(null == hiddenCode||"".equals(hiddenCode)){// 如果hiddenCode没有值,则不需要判读验证码是否正确，那么就直接判读用户和账号是否正确
            if(null==user || null==pd || "".equals(user) || "".equals(pd)){//如果页面未输入用户名或密码
                request.setAttribute("err", 1);// 提示请输入用户名或密码
                getRequest(request, response);// 改变hiddenCode的值
                return;
            }else if("admin".equals(user)&&"admin".equals(pd)){//如果用户名和密码都正确，那么就跳入成功页面
                request.getRequestDispatcher("welcome.jsp").forward(request, response);
                return;
            }else{//如果用户名和密码不正确,那么
                request.setAttribute("err", 2);// 提示用户名或密码错误
                getRequest(request, response);// 改变hiddenCode的值
                return;
            }
        }else{//如果hiddenCode有值,则说明需要判断验证码
            if (null == randomCode || "".equals(randomCode)) {//如果页面未输入验证码
                request.setAttribute("err", 0);// 提示请输入验证码
                getRequest(request,response);
                return;
            }else if(randomCode.toUpperCase().equals(request.getSession().getAttribute("rand"))) {
                if(null==user || null==pd || "".equals(user) || "".equals(pd)){//如果页面未输入用户名或密码
                    request.setAttribute("err", 1);// 提示请输入用户名或密码
                    getRequest(request, response);// 改变hiddenCode的值
                    return;
                }else if("admin".equals(user)&&"admin".equals(pd)){//如果用户名和密码都正确，那么就跳入成功页面
                    request.getRequestDispatcher("welcome.jsp").forward(request, response);
                    return;
                }else{//如果用户名和密码不正确,那么
                    request.setAttribute("err", 2);// 提示用户名或密码错误
                    getRequest(request, response);// 改变hiddenCode的值
                    return;
                }
            }else{
                request.setAttribute("err", 3);// 验证码错误
                getRequest(request, response);
                return;
            }
        }
	}

	/**
	 * 如果输入错误各种原因跳会登录页面
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setAttribute("hiddenCode", 1);
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
	}
}