package com.tzh.bio.single_file_transfer;

import java.io.*;
import java.net.Socket;

public class File_client {

    public static void main(String[] args)throws Exception {
        String path = "F:\\系统\\FLHS_GHOST_WIN7_SP1_X64_V2016_08.iso";
        String uploaderName = "唐子壕";
        File file = new File(path);
        upload(file,uploaderName);

    }

    public static void upload(File file,String uploaderName ){
        Socket socket = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            socket = new Socket("192.168.1.106", 5088);
            dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            String fileName = file.getName();//得到本地文件名
            dataOutputStream.writeInt(new String(fileName.getBytes(), "ISO-8859-1").length());//将文件名的长度写入服务端
            dataOutputStream.write(fileName.getBytes());//将文件名写入服务端
            dataOutputStream.flush();//将文件名的长度和文件名强制刷新到服务端

            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);// 得到本地文件类型
            dataOutputStream.writeInt(new String(fileType.getBytes(), "ISO-8859-1").length());// 将文件类型的长度写入服务端
            dataOutputStream.write(fileType.getBytes());// 将文件类型写入服务端
            dataOutputStream.flush();//将文件类型长度和文件类型强制刷新到服务端


            dataOutputStream.writeLong(file.length()); // 将本地文件内容长度写入服务端
            dataOutputStream.flush();//将文件内容长度强制刷新到服务端


            dataOutputStream.writeInt(new String(uploaderName.getBytes(), "ISO-8859-1").length());//将上传者名字长度写入到服务端
            dataOutputStream.write(uploaderName.getBytes());//将上传者名字写入到服务端
            dataOutputStream.flush();//将上传者名字长度和上传者名字强制刷新到服务端

            System.out.print("[文件名:" + fileName + " | ");
            System.out.print("文件名长度:" + file.getName().length() + " | ");
            System.out.print("文件类型:" + fileType + " | ");
            System.out.print("文件内容长度:" + file.length() + " | ");
            System.out.print("上传者:" + uploaderName + "]");

            byte[] bytes = new byte[1024];// new一个byte数组
            int len = 0;
            while ((len = dataInputStream.read(bytes)) > 0) {// 只要文件内容的长度大于0,就一直读取
                System.out.println(len);
                dataOutputStream.write(bytes,0,len);// 将读取的文件内容写过去
                dataOutputStream.flush();
            }
            System.out.println("传入到服务端成功");// 读取成功
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (null != dataOutputStream) {
                    dataOutputStream.close();
                }
                if (null != dataInputStream) {
                    dataInputStream.close();
                }
                if (null != socket) {
                    socket.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
