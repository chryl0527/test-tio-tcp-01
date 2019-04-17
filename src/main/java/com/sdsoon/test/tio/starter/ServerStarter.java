package com.sdsoon.test.tio.starter;

import com.sdsoon.test.tio.config.TcpConfig;
import com.sdsoon.test.tio.server.ServerHandler;
import com.sdsoon.test.tio.server.ServerListener;
import org.tio.server.ServerGroupContext;
import org.tio.server.TioServer;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

import java.io.IOException;

/**
 * Created By Chr on 2019/4/16.
 */
public class ServerStarter {

    //handler处理类：编码，解码，消息处理
    public static ServerAioHandler serverAioHandler = new ServerHandler();
    //listener事件监听器：可以为null，但是建议实现自己的接口
    public static ServerAioListener serverAioListener = new ServerListener();

    //context：一组连接共用上下文对象（Handler,Listener）
    public static ServerGroupContext serverGroupContext = new ServerGroupContext(serverAioHandler, serverAioListener);

    //    tioServer：服务器端 的入口（groupContext）
    public static TioServer tioServer = new TioServer(serverGroupContext);

    //Ip+Port
    //ip：如果不需要绑定ip，可为null
    public static String serverIp = null;
    //port：监听的端口
    public static int serverPort = TcpConfig.PORT;


    /**
     * 启动
     */
    public static void main(String args[]) throws IOException {
        //设置心跳时间
        serverGroupContext.setHeartbeatTimeout(TcpConfig.TimeOut);

        //监听，启动
        tioServer.start(serverIp,serverPort);
    }

}
