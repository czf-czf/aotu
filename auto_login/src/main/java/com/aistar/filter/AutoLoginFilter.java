package com.aistar.filter;

import com.aistar.pojo.Customer;
import com.aistar.service.CustomerService;
import com.aistar.service.impl.CustomerServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AutoLoginFilter",urlPatterns = "/*",
        initParams = {@WebInitParam(name = "ignorePath", value ="customer/auto_login;customer/logout;auto_login.jsp" )})


public class AutoLoginFilter implements Filter {
    public String[] ignorePaths;

    public void init(FilterConfig config) throws ServletException {
        String ignorePath = config.getInitParameter("ignorePath");
        ignorePaths = ignorePath.split(";");
        for (String s : ignorePaths) {

            System.out.println(s);

        }

    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        //获得请求的资源
        for(String path:ignorePaths){
            if(request.getRequestURI().contains(path)) {
                chain.doFilter(req, resp);
                return;
            }
        }

        //1. session 中用户书否处于登录状态
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("autoLogin");
        //2.session 有用户信息，说明已登录，直接放行
        if(customer != null){
            chain.doFilter(req, resp);
        }else {//3. session 没有用户信息，说明未登录，找到request-cookie ---autoLogin
            Cookie[] cookies = request.getCookies();
            if(cookies == null){//（1）cookie==null, 必须先登录，跳转登录页面..
                response.sendRedirect(request.getContextPath()+"/customer/auto_login.jsp");
            }else { //（2)cookie!=null,获得cookie中的用户名与密码 ,再执行登录
                for (Cookie cookie:cookies){
                    if (cookie.getName().equals("customer")){//用户名密码正确，执行自动登录，跳转到首页
                        String username =   cookie.getValue().split("&")[0];
                        //分隔符产生的数组第0位和第一位
                        String password =   cookie.getValue().split("&")[1];
                        CustomerService customerService = new CustomerServiceImpl();
                        Customer customerLogin = customerService.getByUsernameAndPwd(username,password);
                        if (customerLogin!=null){ //用户名密码正确，执行自动登录，跳转到首页
                            session.setAttribute("autoLogin",customerLogin);
                            session.setAttribute("autoLoginName",customerLogin.getUsername());
                            response.sendRedirect(request.getContextPath()+"/index.jsp");
                        }else {//用户名或密码错误，请重新登录，需要跳转到登录页面
                            response.sendRedirect(request.getContextPath()+"/customer/auto_login.jsp");
                        }
                    }
                }
                //用户未登录，cookie中没有自动登录，需要跳转到登录页面
                response.sendRedirect(request.getContextPath()+"/customer/auto_login.jsp");
            }
        }
    }

    public void destroy() {

    }

}
