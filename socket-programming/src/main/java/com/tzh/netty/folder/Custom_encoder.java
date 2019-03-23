package com.tzh.netty.folder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Custom_encoder extends MessageToByteEncoder<Folder_entity> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Folder_entity file_entity, ByteBuf byteBuf) throws Exception {
        if(file_entity.getFolderName() != null){//文件
            byteBuf.writeInt(0);
            byteBuf.writeInt(file_entity.getFolderNameLength());
            byteBuf.writeBytes(file_entity.getFolderName());
        }else{//文件夹
            byteBuf.writeInt(1);
            byteBuf.writeInt(file_entity.getFileNameLength());//先传文件名字的长度
            byteBuf.writeBytes(file_entity.getFileName());//再传文件名
            byteBuf.writeLong(file_entity.getFileContentLength());//先传文件内容的长度
        }


    }
}
