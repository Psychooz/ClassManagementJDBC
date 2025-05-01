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
            request.setAttribute("error", "An error occurred: " + ex.getMessage());
            listClasses(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                createClass(request, response);
            } else if ("update".equals(action)) {
                updateClass(request, response);
            } else {
                doGet(request, response);
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listClasses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Class> classes = service.getAllClasses();
        request.setAttribute("classes", classes);
        request.getRequestDispatcher("/view/classes.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initialize empty class data
        request.setAttribute("classData", new Class());
        request.getRequestDispatcher("/view/class-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Class cls = service.getClassById(id);
            if (cls == null) {
                request.setAttribute("error", "Class not found");
                listClasses(request, response);
                return;
            }
            request.setAttribute("classData", cls);
            request.setAttribute("id", id); // For form detection
            request.getRequestDispatcher("/view/class-form.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid class ID");
            listClasses(request, response);
        }
    }

    private void createClass(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Class cls = new Class();
            cls.setName(request.getParameter("name").trim());
            cls.setDescription(request.getParameter("description").trim());
            cls.setSchedule(request.getParameter("schedule").trim());
            cls.setTeacher(request.getParameter("teacher").trim());
            cls.setRoom(request.getParameter("room").trim());

            if (cls.getName().isEmpty()) {
                request.setAttribute("error", "Class name is required");
                request.setAttribute("classData", cls);
                showNewForm(request, response);
                return;
            }

            service.createClass(cls);
            request.getSession().setAttribute("success", "Class created successfully");
            response.sendRedirect(request.getContextPath() + "/classes.do");
        } catch (Exception e) {
            request.setAttribute("error", "Error creating class: " + e.getMessage());
            Class errorClass = new Class();
            errorClass.setName(request.getParameter("name"));
            errorClass.setDescription(request.getParameter("description"));
            errorClass.setSchedule(request.getParameter("schedule"));
            errorClass.setTeacher(request.getParameter("teacher"));
            errorClass.setRoom(request.getParameter("room"));
            request.setAttribute("classData", errorClass);
            showNewForm(request, response);
        }
    }

    private void updateClass(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Class existingClass = service.getClassById(id);
            if (existingClass == null) {
                request.setAttribute("error", "Class not found");
                listClasses(request, response);
                return;
            }

            Class cls = new Class();
            cls.setId(id);
            cls.setName(request.getParameter("name").trim());
            cls.setDescription(request.getParameter("description").trim());
            cls.setSchedule(request.getParameter("schedule").trim());
            cls.setTeacher(request.getParameter("teacher").trim());
            cls.setRoom(request.getParameter("room").trim());

            // Validate inputs
            if (cls.getName().isEmpty()) {
                request.setAttribute("error", "Class name is required");
                request.setAttribute("classData", cls);
                request.setAttribute("id", id);
                showEditForm(request, response);
                return;
            }

            service.updateClass(cls);
            request.getSession().setAttribute("success", "Class updated successfully");
            response.sendRedirect(request.getContextPath() + "/classes.do");
        } catch (Exception e) {
            request.setAttribute("error", "Error updating class: " + e.getMessage());
            showEditForm(request, response);
        }
    }

    private void deleteClass(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Class existingClass = service.getClassById(id);
            if (existingClass == null) {
                request.setAttribute("error", "Class not found");
            } else {
                service.deleteClass(id);
                request.getSession().setAttribute("success", "Class deleted successfully");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid class ID");
        } catch (Exception e) {
            request.setAttribute("error", "Error deleting class: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/classes.do");
    }
    private void searchClasses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String teacher = request.getParameter("teacher");
        String room = request.getParameter("room");

        List<Class> classes;
        if ((keyword == null || keyword.isEmpty()) &&
                (teacher == null || teacher.isEmpty()) &&
                (room == null || room.isEmpty())) {
            classes = service.getAllClasses();
        } else {
            classes = service.searchClasses(keyword, teacher, room);
        }

        request.setAttribute("classes", classes);
        request.getRequestDispatcher("/view/classes.jsp").forward(request, response);
    }
}
