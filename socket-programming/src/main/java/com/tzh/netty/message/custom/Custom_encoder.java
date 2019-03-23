package com.tzh.netty.message.custom;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

//自定义消息编码器
public class Custom_encoder extends MessageToByteEncoder<Message_entity> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message_entity message_entity, ByteBuf byteBuf) throws Exception {
        if(message_entity !=null){
            byteBuf.writeInt(message_entity.getMessageLength());
            byteBuf.writeBytes(message_entity.getMessageContent().getBytes());
        }else{
            throw new Exception("message_entity is null");
        }
    }
}
