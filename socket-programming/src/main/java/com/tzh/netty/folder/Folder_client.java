package com.tzh.netty.folder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Folder_client {

    public static void main(String[] args) throws Exception {
        startClient();
    }

    private static void startClient() throws Exception {
        System.out.println("启动客户端");
        //1.定义服务类
        Bootstrap clientBootstap = new Bootstrap();
        //2.定义执行线程组
        EventLoopGroup worker = new NioEventLoopGroup();
        //3.设置线程池
        clientBootstap.group(worker);
        //4.设置通道
        clientBootstap.channel(NioSocketChannel.class);

        //5.添加Handler
        clientBootstap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                System.out.println("正在尝试链接服务端!");
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast(new ByteArrayEncoder());
                pipeline.addLast(new ChunkedWriteHandler());
                pipeline.addLast(new Custom_encoder());
                pipeline.addLast(new Folder_client_handler());
            }
        });
        //6.建立连接
        ChannelFuture channelFuture = clientBootstap.connect("localhost", 8080);
//        等待客户端链路关闭
        channelFuture.channel().closeFuture().sync();
        //7.关闭连接
        worker.shutdownGracefully();
    }
}
