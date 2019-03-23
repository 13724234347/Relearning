package com.tzh.netty.file;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Custom_encoder extends MessageToByteEncoder<File_entity> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, File_entity file_entity, ByteBuf byteBuf) throws Exception {
        if (null == file_entity) {
            throw new Exception("entity is null");
        } else {
            byteBuf.writeInt(file_entity.getFileNameLength());//先传文件名字的长度
            byteBuf.writeBytes(file_entity.getFileName());//再传文件名
            byteBuf.writeLong(file_entity.getFileContentLength());//先传文件内容的长度
        }
    }
}
