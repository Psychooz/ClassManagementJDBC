package xyz.ziadboukhalkhal.servlet.classmanagement.presentation.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;


@WebFilter(urlPatterns = "*.do")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (null != httpRequest.getSession().getAttribute("username")){
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("/login.do").forward(request,
                    response);
        }
    }
    @Override
    public void destroy() {
    }

}
