package com.tzh.netty.folder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Custom_decoder extends ByteToMessageDecoder {
    int HEAD_LENGTH = 8;
    private long contentSumLength = 0;
    private long fileContentLength = 0;
    public static boolean mark = true;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (mark) {
            byteBuf.markReaderIndex();
            int state = byteBuf.readInt();
            if (state == 0) {//文件夹信息接收
                try {
                    Folder_entity folder_entity = new Folder_entity();

                    if (byteBuf.readableBytes() < HEAD_LENGTH) { // 这个HEAD_LENGTH是我们用于表示头长度的字节数。
                        System.out.println("可读信息段比头部信息都小，你在逗我？");
                        return;
                    }

                    int folderNameLength = byteBuf.readInt();
                    if (folderNameLength < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
                        channelHandlerContext.close();
                    }

                    if (byteBuf.readableBytes() < folderNameLength) { //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
                        byteBuf.resetReaderIndex();//返回以前标记,到时再重新读取.还会与下次的接收值累加
                        return;
                    }

                    byte[] folderName = new byte[folderNameLength];
                    byteBuf.readBytes(folderName);
                    folder_entity.setFolderNameLength(folderNameLength);
                    folder_entity.setFolderName(folderName);
                    list.add(folder_entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {//文件信息接收
                try {
                    Folder_entity folder_entity = new Folder_entity();
                    if (byteBuf.readableBytes() < HEAD_LENGTH) { // 这个HEAD_LENGTH是我们用于表示头长度的字节数。
                        System.out.println("可读信息段比头部信息都小，你在逗我？");
                        return;
                    }

                    int fileNameLength = byteBuf.readInt(); // 读取传送过来的名字的长度。ByteBuf
                    if (fileNameLength < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
                        channelHandlerContext.close();
                    }

                    if (byteBuf.readableBytes() < fileNameLength) { //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
                        byteBuf.resetReaderIndex();//返回以前标记,到时再重新读取.还会与下次的接收值累加
                        return;
                    }

                    byte[] fileName = new byte[fileNameLength];
                    byteBuf.readBytes(fileName);
                    folder_entity.setFileName(fileName);

                    long fileContentLength = byteBuf.readLong();
                    System.out.println(fileContentLength);
                    folder_entity.setFileContentLength(fileContentLength);
                    contentSumLength = fileContentLength;
                    if (fileContentLength != 0) {
                        mark = false;
                    } else {
                        System.out.println(" +++++ ");
                    }
                    list.add(folder_entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {//发送文件内容
            //如果我们总长度减去累加的长度,如果小于可读长度(可读长度里面有一部分是其它的文件.我们这个时候,只读当前这个文件自己的长度)
            if (byteBuf.readableBytes() > contentSumLength - fileContentLength) {
//                byteBuf.skipBytes(byteBuf.readableBytes());
                byte[] bytes = new byte[(int) (contentSumLength - fileContentLength)];
                byteBuf.readBytes(bytes);
                contentSumLength = 0;
                fileContentLength = 0;
                list.add(bytes);
            } else {//如果文件的总长度,大于我们可读与累加的长度
                byte[] bytes = new byte[byteBuf.readableBytes()];
                fileContentLength = fileContentLength + bytes.length;
                byteBuf.readBytes(bytes);
                if (fileContentLength == contentSumLength) {
                    fileContentLength = 0;
                    contentSumLength = 0;
                }
                list.add(bytes);
            }

        }
    }
}