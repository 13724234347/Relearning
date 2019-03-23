package com.tzh.netty.file;

import java.io.Serializable;
import java.util.Arrays;

public class File_entity implements Serializable {

    private Integer fileNameLength;//文件名字长度

    private byte[] fileName;//文件名字

    private Long fileContentLength;//文件内容长度

    @Override
    public String toString() {
        return "Folder_entity{" +
                "fileNameLength=" + fileNameLength +
                ", fileName=" + Arrays.toString(fileName) +
                ", fileContentLength=" + fileContentLength +
                '}';
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
