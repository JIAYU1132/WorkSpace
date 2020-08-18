package dao;

import model.Student;
import model.User;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {


    public static User query(User user) {
        User queryUser = null;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = DBUtil.getConnection();
            String sql = "select id, nickname, email from user " +
                    " where username = ? and password = ?";
            ps = c.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            rs = ps.executeQuery();
            while (rs.next()) {
                queryUser = user;
                queryUser.setId(rs.getInt("id"));
                queryUser.setNickname(rs.getString("nickname"));
                queryUser.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            throw new RuntimeException("登录校验用户名密码出错", e);
        } finally {
            DBUtil.close(c, ps, rs);
        }
        return queryUser;
    }

    public static int insert(User user) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = DBUtil.getConnection();
            String sql = "insert into user(email, password, username)" +
                    "   values (?, ?, ?);";
            ps = c.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getUsername());
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("用户注册出错", e);
        } finally {
            DBUtil.close(c, ps);
        }
    }
}
