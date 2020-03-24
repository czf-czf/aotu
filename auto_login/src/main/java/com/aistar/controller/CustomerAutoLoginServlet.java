package com.aistar.controller;

import com.aistar.pojo.Customer;
import com.aistar.service.CustomerService;
import com.aistar.service.impl.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "CustomerAutoLoginServlet",urlPatterns = "/customer/auto_login")
public class CustomerAutoLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //登录
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //1.service 查询用户
        CustomerService customerService = new CustomerServiceImpl();
        Customer customer = customerService.getByUsernameAndPwd(username,password);
        //2.  (1)customer == null  "用户名或密码错误，请重新输入" ,跳转到登录页面
        if (customer == null){
            request.setAttribute("loginMsg","用户名或密码错误，请重新输入");
            request.getRequestDispatcher("/customer/auto_login.jsp").forward(request,response);
        }else {//(2) customer !=null 登录成功，session.setAttribute(...) ,页面跳转到首页
            HttpSession session = request.getSession();
            session.setAttribute("autoLogin",customer);
            session.setAttribute("autoLoginName",customer.getUsername());
            //3如果点击自动登录，就要生成cookie
            System.out.println(request.getParameter("autologin"));
            if(request.getParameter("autologin") != null && request.getParameter("autologin").equals("ok")){
               Cookie cookie =  new Cookie("customer",username + "&" + password);
               cookie.setMaxAge(7*24*60*60);
               cookie.setPath(request.getContextPath());
               response.addCookie(cookie);
            }
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doPost(request, response);
    }
}
