package game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

// 用来实现匹配功能，内部管理一个匹配队列
public class Matcher {

    private Gson gson = new GsonBuilder().create();

    // 表示匹配成功后的相应数据
    static class MatchResponse{
        public String type;
        public String roomId;
        public boolean isWhite;
        public int otherUserId;
    }

    //实现一个匹配队列
    //借助阻塞队列来完成，阻塞队列(1.线程安全的；2.当队列为空时，尝试出队列，就会阻塞，当队列满时，尝试入队列，也会阻塞)
    private BlockingQueue<GameAPI.Request> queue = new LinkedBlockingDeque<>();

    //实现插入到阻塞队列中的方法
    public void addMatchQueue(GameAPI.Request request) throws InterruptedException {
        queue.put(request);
    }

    //在构造实例时，创建一个扫描线程，尝试进行匹配功能
    private Matcher() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    // 这个方法实现一次匹配过程
                    handerMatch();
                }
            }
        };
        thread.start();
    }

    /**
     * 这里使用生产者消费者模型
     * 服务器收到一次请求是无法完成匹配的，必须要请求多次才能够完成一次匹配过程
     * 使用生产者消费者模型，就相当于能把多次请求的内容积攒起来，积攒的够了自然就能够完成匹配了
     *
     * 其他场景中也会经常见到基于阻塞队列的生产者消费者模型
     * 这种编码风格，抗压能力强(如果短时间出现大量的请求，服务器不会因为请求太多而立刻就挂了)
     */

    private void handerMatch() {
        //实现一次匹配的过程
        try {
            //1. 从阻塞队列中取出两个玩家信息
            //    queue是阻塞队列，如果队列为空时，take会阻塞
            GameAPI.Request player1 = queue.take();
            GameAPI.Request player2 = queue.take();
            System.out.println("匹配到两个玩家：" + player1.userId + ", " + player2.userId);
            //2. 检查两个玩家是否同时在线，处理玩家下线的情况，也要处理一下玩家自己匹配到自己的情况
            OnlineUserManager manager = OnlineUserManager.getInstance();
            Session session1 = manager.getSession(player1.userId);
            Session session2 = manager.getSession(player2.userId);
            // 如果玩家不在线，对应的session就是null
            if (session1 == null) {
                queue.put(player2);
                System.out.println("玩家2不在线");
                return;
            }
            if (session2 == null) {
                queue.put(player1);
                System.out.println("玩家1不在线");
                return;
            }
            if (session1 == session2) {
                //自己匹配到了自己(极端的情况)
                queue.put(player1);
                System.out.println("自己匹配到自己");
                return;
            }
            //3. 把两个玩家加入到同一个游戏房间中
            //   此处需要引入"房间"对象, 还需要把房间对象管理起来
            Room room = new Room();
            room.setPlayerId1(player1.userId);
            room.setPlayerId2(player2.userId);
            // 引入一个房间管理器对象，去组织房间
            RoomManager.getInstance().addRoom(room);
            System.out.println("玩家进入房间成功！ roomId: "+ room.getRoomId());
            //4. 分别给玩家一发送匹配响应，告诉玩家，匹配成功，对手是谁，房间号是多少
            //    此处按照前面所约定的响应格式，把匹配成功的结果返回客户端
            MatchResponse response1 = new MatchResponse();
            response1.type = "startMatch";
            response1.roomId = room.getRoomId();
            response1.isWhite = true;
            response1.otherUserId = player2.userId;
            String respJson1 = gson.toJson(response1);
            session1.getBasicRemote().sendText(respJson1);
            System.out.println("给玩家1响应：" + respJson1);
            //4. 给玩家二发送匹配响应，告诉玩家，匹配成功，对手是谁，房间号是多少
            MatchResponse response2 = new MatchResponse();
            response2.type = "startMatch";
            response2.roomId = room.getRoomId();
            response2.isWhite = false;
            response2.otherUserId = player1.userId;
            String respJson2 = gson.toJson(response2);
            session2.getBasicRemote().sendText(respJson2);
            System.out.println("给玩家2响应：" + respJson2);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private static volatile Matcher instance = null;


    public static Matcher getInstance() {
        if (instance == null) {
            synchronized (Matcher.class) {
                if (instance == null) {
                    instance = new Matcher();
                }
            }
        }
        return instance;
    }
}
