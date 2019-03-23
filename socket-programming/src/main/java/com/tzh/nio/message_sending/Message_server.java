package com.tzh.nio.message_sending;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Message_server {

    private static ServerSocketChannel serverSocketChannel = null;
    private static ByteBuffer cacheBuffer = ByteBuffer.allocate(100);
    private static boolean cache = false;

    //通道管理器
    private static Selector selector;

    public static void main(String[] args) {

        try {
            new Message_server().init(8080).listen();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //获取一个ServerSocket通道，并初始化通道
    public Message_server init(int port) throws IOException {
        //获取一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        //是否阻塞()
        serverChannel.configureBlocking(false);

        serverChannel.socket().bind(new InetSocketAddress(port));
        //获取通道管理器
        selector = Selector.open();
        //将通道管理器与通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件，
        //只有当该事件到达时，Selector.select()会返回，否则一直阻塞。
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        return this;
    }


    public void listen() throws IOException {
        System.out.println("服务器端启动成功");
        //使用轮询访问selector
        while (true) {
            //当有注册的事件到达时，方法返回，否则阻塞。
            selector.select();

            //获取selector中的迭代器，选中项为注册的事件
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();

            while (ite.hasNext()) {
                SelectionKey key = ite.next();
                //删除已选key，防止重复处理
                ite.remove();
                //客户端请求连接事件
                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable()) {//有可读数据事件
                  read(key);
//                    //获取客户端传输数据可读取消息通道。
//                    SocketChannel channel = (SocketChannel)key.channel();
//                    //创建读取数据缓冲器
//                    ByteBuffer buffer = ByteBuffer.allocate(10);
//                    int read = channel.read(buffer);
//                    byte[] data = buffer.array();
//                    String message = new String(data);
//                    System.out.println("获取到客户端消息为: " + message);
////                    ByteBuffer outbuffer = ByteBuffer.wrap(("server.".concat(msg)).getBytes());
////                    channel.write(outbuffer);
                }
            }
        }
    }

    public static void accept(SelectionKey key) {
        try {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            //获得客户端连接通道
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            //在与客户端连接成功后，为客户端通道注册SelectionKey.OP_READ事件。
            channel.register(selector, SelectionKey.OP_READ);
            System.out.println("客户端请求连接事件");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    // 一个client的write事件不一定唯一对应server的read事件，所以需要缓存不完整的包，以便拼接成完整的包
    //包协议：包=包头(4byte)+包体，包头内容为包体的数据长度
    public void read(SelectionKey key) {
        System.out.println("read事件");
        int head_length = 4;//数据包长度
        byte[] headByte = new byte[4];
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(100);
            int bodyLen = -1;
            if (cache) {
                cacheBuffer.flip();
                byteBuffer.put(cacheBuffer);
            }
            channel.read(byteBuffer);// 当前read事件
            byteBuffer.flip();// write mode to read mode
            while (byteBuffer.remaining() > 0) {
                if (bodyLen == -1) {// 还没有读出包头，先读出包头
                    if (byteBuffer.remaining() >= head_length) {// 可以读出包头，否则缓存
                        byteBuffer.mark();
                        byteBuffer.get(headByte);
                        bodyLen = byteArrayToInt(headByte);
                    } else {
                        byteBuffer.reset();
                        cache = true;
                        cacheBuffer.clear();
                        cacheBuffer.put(byteBuffer);
                        break;
                    }
                } else {// 已经读出包头
                    if (byteBuffer.remaining() >= bodyLen) {// 大于等于一个包，否则缓存
                        byte[] bodyByte = new byte[bodyLen];
                        byteBuffer.get(bodyByte, 0, bodyLen);
                        bodyLen = -1;
                        System.out.println("获取到客户端消息为: " + new String(bodyByte));
                    } else {
                        byteBuffer.reset();
                        cacheBuffer.clear();
                        cacheBuffer.put(byteBuffer);
                        cache = true;
                        break;
                    }
                }
            }
            key.interestOps(SelectionKey.OP_READ);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            try {
                serverSocketChannel.close();
                key.cancel();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    /**
     * byte[]转int
     *
     * @param bytes
     * @return
     */
    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        // 由高位到低位
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;// 往高位游
        }
        System.out.println(value);
        return value;
    }





}
