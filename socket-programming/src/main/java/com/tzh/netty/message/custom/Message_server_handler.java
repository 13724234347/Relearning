package com.tzh.netty.message.custom;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class Message_server_handler extends SimpleChannelInboundHandler<Message_entity> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message_entity message_entity) throws Exception {
        if(message_entity != null){

            System.out.println(message_entity.toString());

        }else {
            throw new Exception("message_entity is null");
        }


    }
}
