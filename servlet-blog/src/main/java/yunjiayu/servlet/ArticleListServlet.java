package yunjiayu.servlet;

import yunjiayu.dao.ArticleDAO;
import yunjiayu.model.Article;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//@WebServlet("/articleList")
public class ArticleListServlet extends AbstractBaseServlet {

    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");// 获取前段数据, 用户id
        List<Article> articles = ArticleDAO.query(Integer.parseInt(id));
        return articles;
    }

}
