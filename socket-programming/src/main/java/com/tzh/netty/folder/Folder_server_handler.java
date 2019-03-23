package com.tzh.netty.folder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Folder_server_handler extends ChannelInboundHandlerAdapter {
    private BufferedOutputStream bufferedOutputStream = null;
    private FileOutputStream fileOutputStream = null;
    private String filePath = "C:\\Users\\Administrator\\Desktop\\netty_folder\\";

    private long contentLength = 0;
    private long contentSumLength = 0;//获得内容的字节数

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object object) throws Exception {
        if (object instanceof Folder_entity) {
            Folder_entity folder_entity = (Folder_entity) object;
            if (folder_entity.getFolderName() != null) {
                String folderName = new String(folder_entity.getFolderName());
                File file = new File(filePath + folderName);
                if (!file.exists()) {//如果该文件夹不存在
                    file.mkdirs();//创建文件夹
                }
            } else {
                String fileName = new String(folder_entity.getFileName());
                contentSumLength = folder_entity.getFileContentLength();
                File file = new File(filePath + fileName);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (folder_entity.getFileContentLength() > 0) {
                    try {
                        fileOutputStream = new FileOutputStream(file);
                        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
        }
        try {
            byte[] bytes = (byte[]) object;
            contentLength = contentLength + bytes.length;
            bufferedOutputStream.write(bytes, 0, bytes.length);
            bufferedOutputStream.flush();
            if (contentLength == contentSumLength) {
                contentLength = 0;
                contentSumLength = 0;
                System.out.println("**********************");
                bufferedOutputStream.close();
                Custom_decoder.mark = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }
}

