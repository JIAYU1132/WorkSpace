package servlet;

import dao.UserDAO;
import model.User;
import util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class UserLoginServlet extends AbstractBaseServlet{
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取http请求的数据, 解析为用户类
        User user = JSONUtil.read(req.getInputStream(), User.class);
        // 根据用户名密码, 从数据库中查询用户
        User queryUser = UserDAO.query(user);
        // 如果没有查到, 抛出异常
        if (queryUser == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 如果不等于空, 就得建立一个会话
        HttpSession session = req.getSession();//获取session, 如果获取不到, 就创建一个
        session.setAttribute("user", queryUser);
        return null;
    }
}
