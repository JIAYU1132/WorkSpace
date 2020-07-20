package servlet;

import dao.BuildingDAO;
import model.DictionaryTag;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/building/queryAsDict")
public class BuildingQueryServlet extends AbstractBaseServlet{

    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<DictionaryTag> tags = BuildingDAO.query();
        return tags;
    }
}
