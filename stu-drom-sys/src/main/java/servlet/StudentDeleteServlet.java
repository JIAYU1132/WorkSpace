package servlet;

import dao.StudentDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/delete")
public class StudentDeleteServlet extends AbstractBaseServlet{
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //相同的key有多个, 通过getparameterValues可以获取到value数组
        String[] ids = req.getParameterValues("ids");
        int num = StudentDAO.delete(ids);
        return null;
    }
}
