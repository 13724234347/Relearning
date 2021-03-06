package com.tzh.netty.message;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

//网络文档
//https://blog.csdn.net/maoyuanming0806/article/details/81097083

public class Message_server {

    public static void main(String[] args) {
        startServer();
    }

    private static void startServer() {
        System.out.println("启动服务端");
        //1.定义server启动类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //2.定义工作组:boss分发请求给各个worker:boss负责监听端口请求，worker负责处理请求（读写）
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        //3.定义工作组
        serverBootstrap.group(boss, worker);

        //4.设置通道channel
        serverBootstrap.channel(NioServerSocketChannel.class);
        // 旧版本的写法，但是此过程在上面的通道有同样过程
        //serverBootstrap.channelFactory(new ReflectiveChannelFactory(NioServerSocketChannel.class));

        System.out.println("正在等待客户端链接.......");
        //5.添加handler，管道中的处理器，通过ChannelInitializer来构造
        serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                //此方法每次客户端连接都会调用，是为通道初始化的方法
                // 获得通道channel中的管道链（执行链、handler链）
                ChannelPipeline pipeline = channel.pipeline();
                //根据换行符解析为一条数据发送(有多种解析方式,一般都用自定义解析方式)
                pipeline.addLast(new LineBasedFrameDecoder(1024));
                pipeline.addLast(new Message_server_handler());
                pipeline.addLast(new StringEncoder());
                pipeline.addLast(new StringDecoder());
            }
        });

        //6.设置参数
        // 设置参数，TCP参数
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 2048);
        //连接缓冲池的大小
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        //维持链接的活跃，清除死链接
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        //关闭延迟发送
        try {
            //7.绑定ip和port
            ChannelFuture channelFuture = serverBootstrap.bind("localhost", 8080).sync();
            //Future模式的channel对象额
            // 监听关闭
            channelFuture.channel().closeFuture().sync();
            //等待服务关闭，关闭后应该释放资源
        } catch (InterruptedException e) {
            System.out.println("server springboot_start got exception!");
            e.printStackTrace();
        } finally {
            //8.优雅的关闭资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
