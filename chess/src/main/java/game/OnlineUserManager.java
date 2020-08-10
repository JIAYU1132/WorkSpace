package game;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

// 通过这个类管理在线用户
// 一个程序中, 不需要多个用户管理器实例, 所以应该使用单例模式
public class OnlineUserManager {
    // 使用一个hash表来保存在线用户信息, 为保证线程安全, 使用ConcurrentHashMap
    private ConcurrentHashMap<Integer, Session> users = new ConcurrentHashMap<>();
    // key存用户的userId, value就是该用户对应的websocket的session
    // 存好这个session后, 就可以随时使用这个session和客户端进行通信

    public void online(int userId, Session session) {
        users.put(userId, session);
    }

    public void offline(int userId) {
        users.remove(userId);
    }

    public Session getSession(int userId) {
        return users.get(userId);
    }


    private static volatile OnlineUserManager instance = null;

    private OnlineUserManager() {}

    public static OnlineUserManager getInstance() {
        if (instance == null) {
            synchronized (OnlineUserManager.class) {
                if (instance == null) {
                    instance = new OnlineUserManager();
                }
            }
        }
        return instance;
    }
}
