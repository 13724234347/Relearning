package com.tzh.netty.message.custom;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class Message_client_handler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i<10;i++) {
            String messageContent = "刘聪";
            Message_entity message_entity = new Message_entity(messageContent.getBytes().length, messageContent);
            ctx.writeAndFlush(message_entity);
        }
    }
}
