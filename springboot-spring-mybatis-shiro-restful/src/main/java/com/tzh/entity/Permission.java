package com.tzh.entity;

import java.util.HashSet;
import java.util.Set;

public class Permission {

    private Integer id;

    //请求路径
    private String url;

    //请求方式
    private String requestMode;

    //权限描述
    public String urlDescription;

    //状态(数据库内没有这个字段)
    private String sentence; //判断该角色是否有这个权限

    private String username;

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", requestMode='" + requestMode + '\'' +
                ", urlDescription='" + urlDescription + '\'' +
                ", sentence='" + sentence + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestMode() {
        return requestMode;
    }

    public void setRequestMode(String requestMode) {
        this.requestMode = requestMode;
    }

    public String getUrlDescription() {
        return urlDescription;
    }

    public void setUrlDescription(String urlDescription) {
        this.urlDescription = urlDescription;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
