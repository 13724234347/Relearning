package com.tzh.netty.file;

import com.tzh.netty.folder.Folder_entity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class File_server_handler extends ChannelInboundHandlerAdapter {
    private static String path = "C:\\Users\\Administrator\\Desktop\\";
    private FileOutputStream fileOutputStream =null;
    private BufferedOutputStream bufferedOutputStream = null;
    private long contentLength = 0;
    private long contentSumLength = 0;//获得内容的字节数


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object object) throws Exception {
        if (object instanceof File_entity) {
            File_entity file_entity = (File_entity) object;
            String fileName = new String(file_entity.getFileName());
            System.out.println(fileName);
            contentSumLength = file_entity.getFileContentLength();
            File file = new File(path + fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (file_entity.getFileContentLength() > 0) {
                try {
                    fileOutputStream = new FileOutputStream(file);
                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
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

}


