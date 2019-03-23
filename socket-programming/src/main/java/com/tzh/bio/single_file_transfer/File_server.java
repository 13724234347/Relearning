package com.tzh.bio.single_file_transfer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class File_server {


    public static void main(String[] args) throws Exception {
        String path = "F:\\test\\FLHS_GHOST_WIN7_SP1_X64_V2016_08.iso";
        File file = new File(path);
        uploadWitre(file);
    }

    public static void uploadWitre(File file) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            System.out.println("server started.........................");
            serverSocket = new ServerSocket(5088);
            socket = serverSocket.accept();
            System.out.println("连接客户端" + socket.getInetAddress() + "成功");// 得到客户端的ip
            dataInputStream = new DataInputStream(new DataInputStream(socket.getInputStream()));
            //得到文件名
            int fileNameLength = dataInputStream.readInt();//首先获取到客户端写过来文件名字的长度
            byte[] bytes = new byte[fileNameLength];
            int nameLength = 0;
            while (true) {
                nameLength += dataInputStream.read(bytes);
                if (fileNameLength == nameLength) {
                    break;
                }
            }
            String fileName = new String(bytes, "ISO-8859-1");

            //得到文件类型
            int fileTypeLength = dataInputStream.readInt();//获取到客户端写过来文件类型的长度
            byte[] bytes1 = new byte[fileTypeLength];
            dataInputStream.read(bytes1);
            String fileType = new String(bytes1);
            //得到文件内容长度
            long longLength = dataInputStream.readLong();// 得到客户端写过来的文件内容长度

            /**
             * 得到上传者
             */
            int uploaderNameLength = dataInputStream.readInt();// 得到上传者的的长度
            byte uploaderName[] = new byte[uploaderNameLength];// 转为byte数组
            dataInputStream.read(uploaderName);// 读取byte数组
            String uploader = new String(uploaderName);// 转为String得到上传的name

//        filePath += fileName;// 得到(盘符+文件名 )绝对路径

            System.out.print("[文件名:" + fileName + " | ");
            System.out.print("文件名长度:" + fileNameLength + " | ");
            System.out.print("文件类型:" + fileType + " | ");
            System.out.print("文件内容长度:" + longLength + " | ");
            System.out.print("上传者:" + uploader + " | ");


            dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            byte byt[] = new byte[1024 * 1024 * 20];// 每次读取20M
            long sumLength = 0;
            int len = 0;
            double schedule = 0;
            double oldSchedule = 0;
            long startTime = System.currentTimeMillis(); // 下载开始时间
            System.out.print("文件下载了:[");
            while (true) {
                if (longLength == sumLength) {// 如果客户端写过来的文件长度等于我们要读取的文件长度就结束循环
                    break;
                }
                len = dataInputStream.read(byt);
                sumLength += len;
                dataOutputStream.write(byt, 0, len);
                schedule = Math.round(sumLength / (double) longLength * 100);// 计算进度,进度等于文件的长度除以客户端写过来的长度*100取整
                if (schedule > oldSchedule) {// 只要下载进度大于之前的进度
                    oldSchedule = schedule;// 将当前进度赋给之前的进度
                    System.out.print("#");
                }
                dataOutputStream.flush();
            }
            System.out.println("]" + oldSchedule + "%");
            System.out.println("写入成功");
            long endTime = System.currentTimeMillis();// 下载结束时间
            long time = (endTime - startTime) / 1000;// 总耗时
            System.out.println("所用时间:" + time + "s");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {// 关闭连接

                if (null != dataOutputStream) {
                    dataOutputStream.close();
                }
                if (null != dataInputStream) {
                    dataInputStream.close();
                }
                if (null != socket) {
                    socket.close();
                }
                if (null != serverSocket) {
                    serverSocket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
