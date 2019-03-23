package com.tzh.netty.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class Message_client_handler extends ChannelInboundHandlerAdapter {

      @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {


        byte[] req = ("唐子壕"+ System.getProperty("line.separator")).getBytes();

        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }


    }
}
