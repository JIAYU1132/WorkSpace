package servlet;

import dao.StudentDAO;
import model.Student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/queryById")
public class StudentQueryByIdServlet extends AbstractBaseServlet{
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        Student s = StudentDAO.queryById(Integer.parseInt(id));
        return s;
    }
}
