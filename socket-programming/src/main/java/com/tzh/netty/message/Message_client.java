package com.tzh.netty.message;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Message_client {

    public static void main(String[] args)throws Exception {
        startClient();
    }

    private static void startClient()throws Exception {
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
                pipeline.addLast(new LineBasedFrameDecoder(1024));
//                pipeline.addLast(new StringDecoder());
                pipeline.addLast(new Message_client_handler());
                pipeline.addLast(new StringEncoder());
            }
        });
        //6.建立连接
        ChannelFuture channelFuture = clientBootstap.connect("localhost", 8080);
//        等待客户端链路关闭
        channelFuture.channel().closeFuture().sync();
//        try {
//            //7.测试输入
////            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
////            while (true) {
////                System.out.println("请输入：");
////                String msg = bufferedReader.readLine();
////                channelFuture.channel().writeAndFlush(msg);
////            }
//
//
//            for (int i=0; i<100; i++){
//                String msg = "唐子壕";
//                channelFuture.channel().writeAndFlush(msg);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            //8.关闭连接
//            worker.shutdownGracefully();
//        }
        //7.关闭连接
        worker.shutdownGracefully();
    }
}
