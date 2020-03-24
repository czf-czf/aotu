package com.aistar.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "CustomerLogoutServlet",urlPatterns = "/customer/logout")
public class CustomerLogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 从session中移除登录用户
        HttpSession session = request.getSession();
        session.removeAttribute("autoLogin");
        session.removeAttribute("autoLoginName");
        //2. 从cookie中移除自动登录cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            if(cookie.getName().equals("customer")){

                cookie.setMaxAge(0);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);

            }
        }
        //3. 跳转到登录
        response.sendRedirect(request.getContextPath()+"/customer/auto_login.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doPost(request, response);
    }
}
