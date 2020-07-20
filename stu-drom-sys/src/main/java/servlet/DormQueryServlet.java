package servlet;

import dao.BuildingDAO;
import dao.DormDAO;
import model.DictionaryTag;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/dorm/queryAsDict")
public class DormQueryServlet extends AbstractBaseServlet{

    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String key = req.getParameter("dictionaryKey");
        List<DictionaryTag> tags = DormDAO.query(Integer.parseInt(key));
        return tags;
    }
}
