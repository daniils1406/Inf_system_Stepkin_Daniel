package controllers.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.service.user.UserService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@WebServlet("/userChange")
public class ChangeUser extends HttpServlet {
    UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService= (UserService) config.getServletContext().getAttribute("userService");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("changeData")!=null){
            String result=userService.update(Long.parseLong(req.getParameter("id")),req.getParameter("nick"),req.getParameter("first_name"),req.getParameter("last_name"),req.getParameter("mail"),req.getParameter("password"), Boolean.parseBoolean(req.getParameter("admin")),req.getParameter("salt"));
            if(!result.equals("All clear!")){
                req.getSession().setAttribute("Warning",result);
            }else {
                req.getSession().removeAttribute("Warning");
            }
        }

        if(userService.findUser(req.getParameter("mail"),req.getParameter("password"))!=null){
            req.getSession().setAttribute("CurrentUser",userService.findUser(req.getParameter("mail"),req.getParameter("password")));
        }

        if(req.getParameter("changeImg")!=null && req.getParameter("changeImg")!=""){
            try{
                BufferedInputStream inputStream=new BufferedInputStream(new URL(req.getParameter("changeImg")).openStream());
                Files.copy(inputStream, new File("..\\fileForImages\\UsersAvatar\\"+req.getParameter("id")+".png").toPath(), StandardCopyOption.REPLACE_EXISTING);

            }catch (IOException e){
                try{
                    new File("..\\fileForImages\\UsersAvatar").mkdirs();
                    File file1=new File("..\\fileForImages\\UsersAvatar\\def.png");
                    File file2=new File("..\\fileForImages\\UsersAvatar\\"+req.getParameter("id")+".png");
                    Files.copy(file1.toPath(),file2.toPath(),StandardCopyOption.REPLACE_EXISTING);
                }catch (IOException e1){
                    throw new RuntimeException(e1);
                }
            }
        }

        resp.sendRedirect("http://localhost:8080/user");
    }
}
