package com.tzh.netty.folder;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Folder_client_handler extends ChannelInboundHandlerAdapter {

    private static String path = "F:\\系统"; //要发送的文件夹路劲


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        File filePath = new File(path);
        recursionDirectory(ctx, filePath);
    }


    public void recursionDirectory(ChannelHandlerContext channelHandlerContext, File filePath) {
        File[] files = filePath.listFiles();//获得file对象下的所有文件、文件夹数组
        for (File file : files) {
            if (file.isDirectory()) {//判断是否是文件夹
                System.out.println("文件夹路径:" + file);
                Folder_entity folder_entity = new Folder_entity();
                byte[] folderName = file.getName().getBytes();
                folder_entity.setFolderName(folderName);
                folder_entity.setFolderNameLength(folderName.length);
                channelHandlerContext.writeAndFlush(folder_entity);
                recursionDirectory(channelHandlerContext, file);
                System.out.println("发送完成");
            } else {//文件就进入这里面
                System.out.println("文件路径:" + file);
                Folder_entity folder_entity = new Folder_entity();
                byte[] fileName = file.getName().getBytes();
                folder_entity.setFileNameLength(fileName.length);
                folder_entity.setFileName(fileName);
                System.out.println(file.length());
                folder_entity.setFileContentLength(file.length());
                channelHandlerContext.writeAndFlush(folder_entity);
                if (file.length() > 0) {//可读字节大于0，表示没有字节就不进入
                    RandomAccessFile randomAccessFile = null;
                    try {
                        randomAccessFile = new RandomAccessFile(file.getPath(), "r");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        channelHandlerContext.writeAndFlush(new ChunkedFile(randomAccessFile)).addListener(new ChannelFutureListener() {
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
    }
}
