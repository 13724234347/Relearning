package com.tzh.netty.message.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

//自定义解码器
public class Custom_decoder extends LengthFieldBasedFrameDecoder {

    private static final int HEADER_SIZE = 4;

    public Custom_decoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in == null) {
            return null;
        }
        System.out.println(in.readableBytes());
        if (in.readableBytes() < HEADER_SIZE) {
            throw new Exception("可读信息段比头部信息都小，你在逗我？");
        }
        //注意在读的过程中，readIndex的指针也在移动
        int messageLength = in.readInt();
        System.out.println("messageLength === " + messageLength);

        if (in.readableBytes() < messageLength) {
            throw new Exception("body字段你告诉我长度是" + messageLength + ",但是真实情况是没有这么多，你又逗我？");
        }


        ByteBuf buf = in.readBytes(messageLength);
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String messageContent = new String(req);
        System.out.println(messageContent);
        Message_entity message_entity = new Message_entity(messageLength, messageContent);
        return message_entity;
    }
}
