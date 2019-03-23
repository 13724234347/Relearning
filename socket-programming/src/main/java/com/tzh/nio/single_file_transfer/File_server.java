package com.tzh.nio.single_file_transfer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class File_server {

//    https://www.cnblogs.com/tengpan-cn/p/5809273.html
    private static int port = 8000;
    //通道管理器
    private static Selector selector;
    private static ByteBuffer revBuffer = ByteBuffer.allocate(1024);
    private static FileChannel fileChannel;
    private static FileOutputStream fileOutputStream;

    public static void main(String[] args) throws Exception {
        new File_server().init().listen();
    }


    public File_server init() throws Exception {
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
                }
            }
        }
    }

    public static void accept(SelectionKey key) {
        try {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            //获得客户端连接通道0-=
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            //在与客户端连接成功后，为客户端通道注册SelectionKey.OP_READ事件。
            channel.register(selector, SelectionKey.OP_READ);
            System.out.println("客户端连接成功..........");
            fileOutputStream = new FileOutputStream("D:\\" + channel.hashCode() + ".java");
            fileChannel = fileOutputStream.getChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();


        revBuffer.clear();
        int count = client.read(revBuffer);
        int k = 0;
        // 循环读取缓存区的数据，
        while (count > 0) {
            System.out.println("k=" + (k++) + " 读取到数据量:" + count);
            revBuffer.flip();
            fileChannel.write(revBuffer);
            fileOutputStream.flush();
            revBuffer.clear();
            count = client.read(revBuffer);
        }
        if (count == -1) {
            client.close();
            fileChannel.close();
            fileOutputStream.close();
        }


    }

//    public static void init() throws Exception {
//        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//        serverSocketChannel.configureBlocking(false);
//        ServerSocket serverSocket = serverSocketChannel.socket();
//        serverSocket.bind(new InetSocketAddress(port));
//        selector = Selector.open();
//        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//        while (true) {
//            selector.select();   // 返回值为本次触发的事件数
//            Set<SelectionKey> selectionKeys = selector.selectedKeys();
//            for (SelectionKey key : selectionKeys) {
//                ServerSocketChannel server = null;
//                SocketChannel client = null;
//                int count = 0;
//                if (key.isAcceptable()) {
//                    server = (ServerSocketChannel) key.channel();
//                    System.out.println("有客户端连接进入==)");
//                    client = server.accept();
//                    client.configureBlocking(false);
//                    client.register(selector, SelectionKey.OP_READ);
//                    fileOutputStream = new FileOutputStream("D:\\" + client.hashCode() + ".java");
//                    fileChannel = fileOutputStream.getChannel();
//                } else if (key.isReadable()) {
//                    client = (SocketChannel) key.channel();
//                    revBuffer.clear();
//                    count = client.read(revBuffer);
//                    int k = 0;
//                    // 循环读取缓存区的数据，
//                    while (count > 0) {
//                        System.out.println("k=" + (k++) + " 读取到数据量:" + count);
//                        revBuffer.flip();
//                        fileChannel.write(revBuffer);
//                        fileOutputStream.flush();
//                        revBuffer.clear();
//                        count = client.read(revBuffer);
//                    }
//                    if (count == -1) {
//                        client.close();
//                        fileChannel.close();
//                        fileOutputStream.close();
//                    }
//                } else if (key.isWritable()) {
//                    System.out.println("selectionKey.isWritable()");
//                }
//            }
//        }
//
//    }
}
