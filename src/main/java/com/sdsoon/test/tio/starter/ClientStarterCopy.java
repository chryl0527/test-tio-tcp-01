package com.sdsoon.test.tio.starter;

import com.alibaba.fastjson.JSON;
import com.sdsoon.test.tio.bean.RequestPacket;
import com.sdsoon.test.tio.bean.TestBean;
import com.sdsoon.test.tio.client.ClientHandler;
import com.sdsoon.test.tio.client.ClientListener;
import com.sdsoon.test.tio.config.TcpConfig;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Node;
import org.tio.core.Tio;

import java.io.UnsupportedEncodingException;

/**
 * Created By Chr on 2019/4/16.
 */
public class ClientStarterCopy {
    //连接服务器端的ip+port
    public static Node serverNode = new Node(TcpConfig.HOST, TcpConfig.PORT);
    //handler：
    public static ClientAioHandler clientAioHandler = new ClientHandler();

    //listener：
    public static ClientAioListener clientAioListener = new ClientListener();


    //短链后自动连接，不想自动连接设为null：（long ,int）
    private static ReconnConf reconnConf = new ReconnConf(5000L);
    //context：客户端上下文
    public static ClientGroupContext clientGroupContext = new ClientGroupContext(clientAioHandler, clientAioListener, reconnConf);

    //channel：tcp建立通道连接建立之后  就会产生channel，
    public static ClientChannelContext clientChannelContext = null;

    //tioClient客户端入口,等到连接的时候再连接
    public static TioClient tioClient = null;


    /**
     * 客户端程序入口
     */
    public static void main(String args[]) throws Exception {

        //设置 心跳时间
        clientGroupContext.setHeartbeatTimeout(TcpConfig.TimeOut);

        //打开客户端入口
        tioClient = new TioClient(clientGroupContext);

        //根据连接服务器端，建立通道
        clientChannelContext = tioClient.connect(serverNode);

        //客户端发送  数据信息
        sendJaBe();

    }



    public static void sendJaBe() throws UnsupportedEncodingException {
        RequestPacket requestPacket = new RequestPacket();

        TestBean t = new TestBean();
        t.setXh(1);t.setDl((byte) 2);
        t.setQd(3);t.setImei("imei");
        t.setImsi("imsi");t.setTimel(System.currentTimeMillis());

        String s = JSON.toJSONString(t);


        requestPacket.setBody(s.getBytes(RequestPacket.CHARSET));

        //需要在channel中发送 数据信息
        Tio.send(clientChannelContext, requestPacket);
    }
}
