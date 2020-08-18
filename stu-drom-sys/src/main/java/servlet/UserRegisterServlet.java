package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.UserDAO;
import model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/register")
public class UserRegisterServlet extends AbstractBaseServlet{

    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new GsonBuilder().create();
        User user = gson.fromJson(req.getReader(), User.class);
        int num = UserDAO.insert(user);
        return null;
    }
}
