package game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

// 通过这个类处理websocket的相关通信逻辑
@ServerEndpoint(value = "/game/{userId}")
public class GameAPI {

    // 落子请求
    static class Request {
        public String type;
        public int userId;
        public String roomId;
        public int row;
        public int col;

        @Override
        public String toString() {
            return "Request{" +
                    "type='" + type + '\'' +
                    ", userId=" + userId +
                    ", roomId='" + roomId + '\'' +
                    ", row=" + row +
                    ", col=" + col +
                    '}';
        }
    }

    private int userId;

    @OnOpen
    public void onOpen(@PathParam("userId") String userIdStr, Session session) {
        userId = Integer.parseInt(userIdStr);
        System.out.println("玩家建立连接: " + userId);

        //  把玩家加入到在线玩家列表中
        OnlineUserManager.getInstance().online(userId, session);
    }

    @OnClose
    public void onClose() {
        System.out.println("玩家断开连接: " + userId);

        //   把玩家从在线玩家列表中剔除
    }

    @OnError
    public void onError(Session session, Throwable error) {
        //  把玩家从在线玩家列表中剔除
        System.out.println("玩家断开连接: " + userId);
        OnlineUserManager.getInstance().offline(userId);

        error.printStackTrace();
    }
    // onMessage 收到的数据可能是匹配请求, 也可能是落子请求, 就需要根据请求的type类型来做出区分
    // 如果type是startMatch, 处理匹配请求
    // 如果type是putChess, 处理落子请求
    // message请求内容时一个JSON结构的字符串, 于是就需要针对这个JSON进行解析(使用 Gson进行接卸==解析)
    @OnMessage
    public void onMessage(String message, Session session) throws InterruptedException, IOException {
        System.out.printf("收到玩家 %d 的消息: %s\n", userId, message);

        // 实例化Gson对象
        Gson gson = new GsonBuilder().create();
        // 这句代码就把message这个JSON格式的字符串转成了 Request 对象
        Request request = gson.fromJson(message, Request.class);
        if (request.type.equals("startMatch")) {
            //执行匹配机制
            //通过引入一个匹配队列的数据结构来实现
            Matcher.getInstance().addMatchQueue(request);
        } else if (request.type.equals("putChess")) {
            // 执行落子逻辑
            Room curRoom = RoomManager.getInstance().getRoom(request.roomId);
            curRoom.putChess(request);
        } else {
            System.out.println("非法的type值！" + request.type);
        }
    }
}
