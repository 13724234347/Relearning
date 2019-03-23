package com.tzh.nio.single_file_transfer;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class File_client {


    private static int port = 8000;
    /* 发送数据缓冲区 */
    private static ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    private static InetSocketAddress inetSocketAddress;
    private static Selector selector;
    private static SocketChannel socketChannel;



    public static void main(String[] args) {
        init();
    }

    private static void init(){
        try {
            inetSocketAddress = new InetSocketAddress("localhost", port);
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(inetSocketAddress);
            while (true) {
                selector.select();
                Set<SelectionKey> keySet = selector.selectedKeys();
                for (final SelectionKey key : keySet) {
                    if(key.isConnectable()){
                        socketChannel = (SocketChannel)key.channel();
                        socketChannel.finishConnect();
                        socketChannel.register(selector, SelectionKey.OP_WRITE);
                    }
                    else if(key.isWritable()){
                        sendFile(socketChannel);
                    }
                }
                keySet.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void sendFile(SocketChannel client) {
        FileInputStream fis = null;
        FileChannel channel = null;
        try {
//            E:\tzh\git\Git的使用.doc
            fis = new FileInputStream("E:\\tzh\\socket\\socket\\nio\\Netty框架\\Netty\\src\\file\\FileClient.java");
            channel = fis.getChannel();
            int i = 1;
            int count = 0;
            while((count = channel.read(sendBuffer)) != -1) {
                sendBuffer.flip();
//                Thread.sleep(20000);
                int send = client.write(sendBuffer);
                System.out.println("i===========" + (i++) + "   count:" + count + " send:" + send);
                // 服务器端可能因为缓存区满，而导致数据传输失败，需要重新发送
                while(send == 0){
//                    Thread.sleep(10);
                    Thread.sleep(1000000);
                    send = client.write(sendBuffer);
                    System.out.println("i重新传输====" + i + "   count:" + count + " send:" + send);
                }
                sendBuffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
                fis.close();
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
