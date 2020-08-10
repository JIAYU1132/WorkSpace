package test;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/webSocketTest/{userId}")
public class TestWebSocket {
    private int userId;

    // 在客户端建立连接成功时自动调用
    // 类似于Servlet中的doGet/doPost方法, 有Tomcat自动调用
    @OnOpen // 如果没有这个注解, onOpen方法就无法被正确调用
    public void onOpen(@PathParam("userId") String userIdStr, Session session) {
        // String userId 指定的是路径中userId这个参数
            // 如果路径是(/websocket/100), 那么此时userId的值就会被自动填写成100
            // 而自动填写这个操作时使用 @PathParam 这个注解来实现的
        // Session session 这个Session不是HttpSession(Servlet中带的Session), 而是webSocket中带的Session,
            // 此处的Session描述的是一个webSocket的会话, 通过这个会话, 可以获取到客户端的信息, 同时也可以给客户端反馈一些响应
            // 两种Session是两个不相干的体系, 但是所处的定位类似, 功能类似
        System.out.println("建立连接 + :" + userIdStr);
        this.userId = Integer.parseInt(userIdStr);

        // 创建一个线程, 通过这个线程, 循环给客户端发送数据
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    String resp = "" + System.currentTimeMillis();
                    try {
                        session.getBasicRemote().sendText(resp);
                        Thread.sleep(1000);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    // 在客户端连接被断开的时候自动调用
    @OnClose
    public void onClose() {
        System.out.println("断开连接 + :" + userId);
    }

    // 在客户端连接异常中止的时候, 被自动调用
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("连接异常 + :" + userId);
        System.out.println(error.getMessage());
    }

    // 在客户端收到消息的时候被自动调用
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("收到消息 + :" + message);
        // 服务器给客户端返回一个消息
        // 返回一个当前的时间戳
        String resp = "" + System.currentTimeMillis();
        session.getBasicRemote().sendText(resp);
    }

}
