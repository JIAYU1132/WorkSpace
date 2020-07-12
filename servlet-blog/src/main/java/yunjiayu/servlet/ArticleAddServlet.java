package yunjiayu.servlet;

import yunjiayu.dao.ArticleDAO;
import yunjiayu.model.Article;
import yunjiayu.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/articleAdd")
public class ArticleAddServlet extends AbstractBaseServlet {
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Article article = JSONUtil.deserialize(req.getInputStream(), Article.class);
        int num = ArticleDAO.insert(article);
        if (num != 1) {
            throw new RuntimeException("插入文章数量异常");
        }

        return null;
    }
}
