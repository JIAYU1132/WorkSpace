package servlet;

import dao.StudentDAO;
import model.Page;
import model.Student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/query")
public class StudentQueryServlet extends AbstractBaseServlet {

    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 解析searchText=&sortOrder=asc&pageSize=7&pageNumber=1
        Page page = Page.parse(req);
        List<Student> students = StudentDAO.query(page);
        return students;
    }
}
