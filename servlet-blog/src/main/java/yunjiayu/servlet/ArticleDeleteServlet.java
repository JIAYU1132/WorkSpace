package yunjiayu.servlet;

import yunjiayu.dao.ArticleDAO;
import yunjiayu.model.Article;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/articleDelete")
public class ArticleDeleteServlet extends AbstractBaseServlet{
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String idsString = req.getParameter("ids"); //ids = 1, 2
        String[] idsArray = idsString.split(",");//["1", "2"]
        int[] ids = new int[idsArray.length];// [1, 2]
        for (int i = 0; i < idsArray.length; i++) {
            ids[i] = Integer.parseInt(idsArray[i]);
        }
        int num = ArticleDAO.delete(ids);
        return null;
    }
}
