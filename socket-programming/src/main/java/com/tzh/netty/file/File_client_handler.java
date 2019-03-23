package com.tzh.netty.file;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.stream.ChunkedFile;

import java.io.*;

public class File_client_handler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String filePath = "F:\\系统\\FLHS_GHOST_WIN7_SP1_X64_V2016_08.iso";
        File file = new File(filePath);
        String fileName = file.getName();//获取到文件名
        File_entity file_entity = new File_entity();
        file_entity.setFileNameLength(fileName.getBytes().length);
        file_entity.setFileName(fileName.getBytes());
        System.out.println(file.length());
        file_entity.setFileContentLength(file.length());
        System.out.println(file_entity);
        ctx.writeAndFlush(file_entity);

        if (file.length() > 0) {//可读字节大于0，表示没有字节就不进入
            RandomAccessFile randomAccessFile = null;
            try {
                randomAccessFile = new RandomAccessFile(file.getPath(), "r");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                ctx.writeAndFlush(new ChunkedFile(randomAccessFile)).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        future.channel().close();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("发送完成");
    }
}
