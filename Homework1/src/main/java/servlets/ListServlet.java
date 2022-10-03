package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@WebServlet("/list")
public class ListServlet extends HttpServlet {

    public static class ContentHolder {
        private final String mapping;
        private final String button;
        private final String discriptio;
        private final String pict;

        public ContentHolder(String mapping,String button,String discriptio, String pict) {
            this.mapping = mapping;
            this.button=button;
            this.discriptio=discriptio;
            this.pict=pict;
        }
        public String getButton(){return button;}

        public String getPict(){return pict;}

        public String getDiscriptio(){return discriptio;}


        public String getMapping() {
            return mapping;
        }
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //возвращается мапа с ключом в виде имени сервлетов и значением в виде сервлетов
        Map<String, ? extends ServletRegistration> servletRegistrationMap = req.getServletContext().getServletRegistrations();

        //берем из мапы значения и кидаем их в список проверяя что они анотированы созданной нами аннотацией
        List<? extends ServletRegistration> contentRegistrations = servletRegistrationMap.values().stream()
                .filter(this::checkIfContentUnitInstance)
                .collect(Collectors.toList());

        //ПРЕДПОЛОЖИТЕЛЬНО создаем список севрлетов в виде отельного класса содержащего ссылку на нахождение сервлета в папке и эндпойнт которому сервлет принадлежит
        List<ContentHolder> contentHolders = contentRegistrations.stream()
                .map(reg -> new ContentHolder(
                        reg.getMappings().stream().findAny().orElseThrow(() ->
                                new RuntimeException("Servlet mappings not found")),
                        getNameButton(reg),
                        getDiscription(reg),
                        getPicture(reg)
                )).collect(Collectors.toList());

        resp.setContentType("text/html");

        req.setAttribute("Content", contentHolders);


        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/list.jsp");
        requestDispatcher.forward(req, resp);

    }


    private <M extends ServletRegistration> String getResourcePath(M servletReg) {
        return  getServletClass(servletReg).getAnnotation(ContentServlet.class).resourcePath();
    }

    private <M extends ServletRegistration> String getNameButton(M servletReg) {
        return  getServletClass(servletReg).getAnnotation(ContentServlet.class).nameOfButton();
    }

    private <M extends ServletRegistration> String getDiscription(M servletReg) {
        return  getServletClass(servletReg).getAnnotation(ContentServlet.class).discription();
    }

    private <M extends ServletRegistration> String getPicture(M servletReg) {
        return  getServletClass(servletReg).getAnnotation(ContentServlet.class).picture();
    }

    private <M extends ServletRegistration> boolean checkIfContentUnitInstance(M servletReg) {
        return  getServletClass(servletReg).isAnnotationPresent(ContentServlet.class);
    }

    private <M extends ServletRegistration> Class<?> getServletClass(M servletReg) {

        String className = servletReg.getClassName();
        Class<?> servletClass;
        try {
            servletClass = this.getClass().getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return servletClass;
    }

}