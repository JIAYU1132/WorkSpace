package game;

import java.util.concurrent.ConcurrentHashMap;

//这是一个单例类，负责管理若干个房间
///使用一个Hash表把房间存储起来，key就是roomId, value就是Room对象
public class RoomManager {

    //使用一个Hash表把房间信息保存起来
    private ConcurrentHashMap<String, Room> rooms = new ConcurrentHashMap<>();

    public void addRoom(Room room) {
        rooms.put(room.getRoomId(), room);
    }

    public void removeRoom(String roomId) {
        rooms.remove(roomId);
    }

    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }

    private static volatile RoomManager instance = null;

    private RoomManager() {}

    public static RoomManager getInstance() {
        if (instance == null) {
            synchronized (RoomManager.class) {
                if (instance == null) {
                    instance = new RoomManager();
                }
            }
        }
        return instance;
    }
}
