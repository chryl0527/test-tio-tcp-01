package com.sdsoon.test.tio.sender;

import com.sdsoon.test.tio.bean.RequestPacket;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

/**
 * 未实现
 * <p>
 * Created By Chr on 2019/4/16.
 */
public class TioSender {


    public static void sendObj(ChannelContext channelContext, Object object) throws Exception {
        //如何发送数据
        RequestPacket packet = new RequestPacket();
        //发送字节数据===>Object转换为字节数组
        packet.setBody("hello world".getBytes(RequestPacket.CHARSET));
        //Tio进行发送
        Tio.send(channelContext, packet);
    }

}
