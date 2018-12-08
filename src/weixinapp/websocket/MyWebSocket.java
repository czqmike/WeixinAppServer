package weixinapp.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.json.*;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket")
public class MyWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
//    private static CopyOnWriteArraySet<MyWebSocket> webSocketMap = new CopyOnWriteArraySet<MyWebSocket>();
    private static ConcurrentHashMap<Integer, MyWebSocket> webSocketMap = new ConcurrentHashMap<Integer, MyWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    private int user_id = -1;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
       // webSocketMap.put(arg0, this);     // 因需要client传user_id，故加入Map在Message中完成
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！ID为"  + session.getId() +  "当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
    	if (this.user_id != -1) {
			webSocketMap.remove(this.user_id);  //从set中删除
    	}
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！ID为"  + session.getId() +  "当前在线人数为" + getOnlineCount());
    }
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
		System.out.println(message); // test

    	JSONObject jsonobj = null;
		try {
			jsonobj = new JSONObject(message);

			if (jsonobj.has("id_confirm")) { // 这个消息不是会话，而是身份验证
				this.user_id = Integer.parseInt(jsonobj.getString("user_id"));
				webSocketMap.put(this.user_id, this);
			} else {  // 发送消息给user_des 与 user_src
				String user_srcs = jsonobj.getString("user_src");
				String user_dess = jsonobj.getString("user_des");
				String content = jsonobj.getString("content");
				MyWebSocket srcSocket = webSocketMap.get(Integer.parseInt(user_srcs));
				MyWebSocket desSocket = webSocketMap.get(Integer.parseInt(user_dess));
				
				if (srcSocket != null) {
					srcSocket.sendMessage(message); // 为了显示聊天记录，发送消息的用户也要收到自己的消息
				}
				if (desSocket != null) {
					desSocket.sendMessage(message);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}