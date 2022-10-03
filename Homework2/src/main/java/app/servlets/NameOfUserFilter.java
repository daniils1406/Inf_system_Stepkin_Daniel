package app.servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebFilter(urlPatterns = "/")
public class NameOfUserFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        //System.out.println(req.getParameter("NameOfUser"));
        //System.out.println(req.getParameter("beforeLeave"));
        req.getSession().setAttribute("NameNotNull",req.getParameter("NameOfUser"));
        chain.doFilter(req,res);
    }
}
