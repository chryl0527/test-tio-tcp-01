package com.sdsoon.test.tio.client;

import com.sdsoon.test.tio.bean.RequestPacket;
import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 客户端的处理类:与ServerHandler一样
 * <p>
 * Created By Chr on 2019/4/16.
 */
public class ClientHandler implements ClientAioHandler {


    /**
     * 解码：
     *
     * @param buffer
     * @param limit
     * @param position
     * @param readableLength
     * @param channelContext
     * @return
     * @throws AioDecodeException
     */
    @Override
    public Packet decode(ByteBuffer buffer, int limit, int position, int readableLength, ChannelContext channelContext) throws AioDecodeException {

        //拿到client的packet,对比收到的消息头格式
        if (readableLength < RequestPacket.HANDER_LENGTH) {
            return null;
        }
        //格式正确，操作消息体
        //缓冲区当前位置的 int 值
        int bodyLength = buffer.getInt();
        //消息体格式不正确
        if (bodyLength < 0) {
            throw new AioDecodeException//
                    ("bodyLength [" + bodyLength + "] is not right, remote:" + channelContext.getClientNode());
        }
        //本次接收的数据需要的 缓冲区的长度(总长度=消息头长度+消息体长度)
        int neededLength = RequestPacket.HANDER_LENGTH + bodyLength;
        //验证 本地收到的 数据是否足够组包：防止发生 半包 和 粘包
        int isDataEnough = readableLength - neededLength;
        //不够消息体长度，无法用buffer组合
        if (isDataEnough < 0) {
            return null;
        } else {//组包成功

            RequestPacket requestPacket = new RequestPacket();
            if (bodyLength > 0) {
                //本次接受的 位置的int值
                byte[] bytes = new byte[bodyLength];
                buffer.get(bytes);
                requestPacket.setBody(bytes);
            }
            return requestPacket;
        }

    }

    /**
     * 编码
     *
     * @param packet
     * @param groupContext
     * @param channelContext
     * @return
     */
    @Override
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
        RequestPacket requestPacket = (RequestPacket) packet;

        //要发送的数据对象，以字节数组byte[]放在Packet的body中
        byte[] body = requestPacket.getBody();
        int bodyLength = 0;
        if (body != null) {
            bodyLength = body.length;
        }
        //byteBuffer的总长度=消息头长度（headLen）+消息体长度（bodyLen）
        int byteBufferLen = RequestPacket.HANDER_LENGTH + bodyLength;
        //初始化新的ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(byteBufferLen);
        //设置字节序：？？？？？？
        //新的字节顺序，要么是 BIG_ENDIAN，要么是 LITTLE_ENDIAN
//        buffer.order(groupContext.getByteOrder());
        buffer.order(ByteOrder.BIG_ENDIAN);
        //写入消息头
        buffer.putInt(bodyLength);
        //写入消息体
        if (body != null) {
            buffer.put(body);
        }
        return buffer;
    }

    /**
     * 数据处理
     *
     * @param packet
     * @param channelContext
     * @throws Exception
     */
    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {

        //接受 server发送来的 数据
        RequestPacket requestPacket = (RequestPacket) packet;

        //得到包装的数据
        byte[] body = requestPacket.getBody();

        if (body != null) {
            java.lang.String s = new java.lang.String(body, RequestPacket.CHARSET);
            System.err.println("客户端 收到 服务端的回执" + s);

        }
    }

    @Override
    public Packet heartbeatPacket() {
        return new RequestPacket();
    }
}
