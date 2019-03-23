package com.tzh.netty.folder;

import java.io.Serializable;
import java.util.Arrays;

public class Folder_entity implements Serializable {

    private Integer fileNameLength; //文件名字长度

    private byte[] fileName; //文件名字

    private Long fileContentLength; //文件内容长度

    private int folderNameLength; //文件夹名字长度

    private byte[] folderName; //文件名字

    @Override
    public String toString() {
        return "Folder_entity{" +
                "fileNameLength=" + fileNameLength +
                ", fileName=" + Arrays.toString(fileName) +
                ", fileContentLength=" + fileContentLength +
                ", folderNameLength=" + folderNameLength +
                ", folderName=" + Arrays.toString(folderName) +
                '}';
    }

    public int getFolderNameLength() {
        return folderNameLength;
    }

    public void setFolderNameLength(int folderNameLength) {
        this.folderNameLength = folderNameLength;
    }

    public byte[] getFolderName() {
        return folderName;
    }

    public void setFolderName(byte[] folderName) {
        this.folderName = folderName;
    }

    public Integer getFileNameLength() {
        return fileNameLength;
    }

    public void setFileNameLength(Integer fileNameLength) {
        this.fileNameLength = fileNameLength;
    }

    public byte[] getFileName() {
        return fileName;
    }

    public void setFileName(byte[] fileName) {
        this.fileName = fileName;
    }

    public long getFileContentLength() {
        return fileContentLength;
    }

    public void setFileContentLength(Long fileContentLength) {
        this.fileContentLength = fileContentLength;
    }
}
