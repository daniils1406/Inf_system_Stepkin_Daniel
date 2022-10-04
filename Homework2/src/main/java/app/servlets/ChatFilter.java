package app.servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebFilter(urlPatterns = "/chat")
public class ChatFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        if(req.getParameter("beforeLeave")!=null){
            req.getSession().setAttribute("ExitButton",req.getParameter("beforeLeave"));

        }else{
            req.getSession().setAttribute("ExitButton",1);

        }
        chain.doFilter(req,res);

    }
}
