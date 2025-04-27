package xyz.ziadboukhalkhal.servlet.classmanagement.presentation.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xyz.ziadboukhalkhal.servlet.classmanagement.service.IService;
import xyz.ziadboukhalkhal.servlet.classmanagement.service.ServiceImpl;
import xyz.ziadboukhalkhal.servlet.classmanagement.service.model.Class;

import java.io.IOException;
import java.util.List;


@WebServlet("/classes.do")
public class ClassServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IService service = new ServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "create":
                    createClass(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateClass(request, response);
                    break;
                case "delete":
                    deleteClass(request, response);
                    break;
                case "search":
                    searchClasses(request, response);
                    break;
                default:
                    listClasses(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void listClasses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Class> classes = service.getAllClasses();
        request.setAttribute("classes", classes);
        request.getRequestDispatcher("/view/classes.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/class-form.jsp").forward(request, response);
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Class cls = service.getClassById(id);
        request.setAttribute("class", cls);
        request.getRequestDispatcher("/view/class-form.jsp").forward(request, response);
    }

    private void createClass(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Class cls = new Class();
        cls.setName(request.getParameter("name"));
        cls.setDescription(request.getParameter("description"));
        cls.setSchedule(request.getParameter("schedule"));
        cls.setTeacher(request.getParameter("teacher"));
        cls.setRoom(request.getParameter("room"));

        service.createClass(cls);
        response.sendRedirect("classes.do");
    }
    private void updateClass(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Class cls = new Class();
            cls.setId(id);
            cls.setName(request.getParameter("name"));
            cls.setDescription(request.getParameter("description"));
            cls.setSchedule(request.getParameter("schedule"));
            cls.setTeacher(request.getParameter("teacher"));
            cls.setRoom(request.getParameter("room"));

            service.updateClass(cls);

            // Set success message
            request.getSession().setAttribute("message", "Class updated successfully");
            response.sendRedirect(request.getContextPath() + "/classes.do");
        } catch (Exception e) {
            throw new ServletException("Error updating class", e);
        }
    }

    private void deleteClass(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        service.deleteClass(id);
        response.sendRedirect("classes.do");
    }

    private void searchClasses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Class> classes = service.searchClasses(keyword);
        request.setAttribute("classes", classes);
        request.getRequestDispatcher("/view/classes.jsp").forward(request, response);
    }

}
