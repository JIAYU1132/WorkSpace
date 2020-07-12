package yunjiayu.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/blogdemo?useUnicode=true&characterEncoding=UTF-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "yunjiayu990814";

    /**
     * 创建数据库连接的方式:
     *  1. DriverManager: 每次都是创建数据库物理连接. connection.close() 关闭物理连接
     *  2. DataSource: 初始化就创建一定数量的连接. connection.close() 关闭只是重置连接对象, 归还连接池
     *  DataSource 创建连接的方式, 效率更好
     */

    private static volatile DataSource DATA_SOURCE;
    // volatile保证: 1. 可见性 2. 禁止指令重排序/建立内存屏障

    private DBUtil() {
    }

    // 获取数据库连接池: 使用双重校验锁的单例模式, 创建数据库连接池
    // 自己使用, 所以不开放
    private static DataSource getDataSource() {
        if (DATA_SOURCE == null) { // 并发执行, 提高效率
            synchronized (DBUtil.class) { // 加锁
                if (DATA_SOURCE == null) { // 保证只创建一次满足单例的同一个对象的要求
                    DATA_SOURCE = new MysqlDataSource();
                    ((MysqlDataSource) DATA_SOURCE).setUrl(URL);
                    ((MysqlDataSource) DATA_SOURCE).setUser(USERNAME);
                    ((MysqlDataSource) DATA_SOURCE).setPassword(PASSWORD);
                }
            }
        }
        return DATA_SOURCE;
    }

    // 获取数据库连接: 提供公共的方法给其他地方操作 JDBC 时获取数据库连接
    public static Connection getConnection() {
        try {
                return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("获取数据库连接失败", e);
        }
    }

    /**
     *  jdbc 操作步骤:
     *  (1) 创建数据库连接: DriverManager/DataSource
     *  (2) 创建操作命令对象 Statement
     *   Statement 简单 sql 语句的执行
     *   PreparedStatement: 可以执行带参数的 sql ---
     *                          (1) 可以预编译, 效率更高 (2) 防止一定程度的sql注入(单引号转义)
     *  (3) 执行 sql
     *  (4) 如果是查询, 需要处理结果集 ResultSet
     *  (5) 释放资源(反向释放)
     */

    public static void close(Connection c, Statement s, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("关闭数据库连接失败", e);
        }
    }

    public static void close(Connection c, Statement s) {
        close(c, s, null);
    }

}
