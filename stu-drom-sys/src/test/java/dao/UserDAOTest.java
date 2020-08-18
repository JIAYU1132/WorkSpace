package dao;

import junit.framework.TestCase;
import model.User;

public class UserDAOTest extends TestCase {

    public void testQuery() {
        User user = new User();
        user.setUsername("abc");
        user.setPassword("123");
        User queryUser = new User();
        queryUser = UserDAO.query(user);
        System.out.println(queryUser);
    }


    public void testInsert() {
        User user = new User();
        user.setEmail("386902834@qq.com");
        user.setPassword("Yunjiayu990814");
        user.setUsername("yjy");
        UserDAO.insert(user);
    }
}