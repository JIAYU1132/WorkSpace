package yunjiayu.servlet;

import yunjiayu.dao.ArticleDAO;
import yunjiayu.model.Article;
import yunjiayu.model.Response;
import yunjiayu.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class AbstractBaseServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    // 设计模式 : 模板模式
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // 请求体设置编码
        resp.setCharacterEncoding("UTF-8"); // 响应体设置编码
        resp.setContentType("application/json; charset = UTF-8"); // 浏览器接受数据解析方式

        Response response = new Response();
        try {
            response.setData(process(req, resp));
            response.setSuccess(true);
        } catch (Exception e) { // 如果出现异常, 返回 success = false, 并设置错误消息, 异常堆栈信息
            response.setMessage(e.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter writer = new PrintWriter(sw);
            e.printStackTrace(writer);
            response.setStackTrace(sw.toString());
        }
        // 响应数据, json数据格式
        PrintWriter pw = resp.getWriter();
        pw.println(JSONUtil.serialize(response));
        pw.flush();
    }

    public abstract Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
