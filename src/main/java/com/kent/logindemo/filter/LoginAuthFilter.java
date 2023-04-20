package com.kent.logindemo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.kent.logindemo.model.bean.User;

@WebFilter(urlPatterns = {"/", "/index"})
public class LoginAuthFilter extends OncePerRequestFilter {
    private static final String LOGIN_URL = "/login";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // get user from session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(LOGIN_URL);
            return;
        }
        filterChain.doFilter(request, response);
    }
    
}
