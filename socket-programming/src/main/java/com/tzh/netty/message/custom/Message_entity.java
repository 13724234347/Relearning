package com.tzh.netty.message.custom;

public class Message_entity {
    private Integer messageLength;//消息长度


    private String messageContent;//消息内容

    public Message_entity(Integer messageLength, String messageContent) {
        this.messageLength = messageLength;
        this.messageContent = messageContent;
    }

    @Override
    public String toString() {
        return "Message_entity{" +
                "messageLength=" + messageLength +
                ", messageContent='" + messageContent + '\'' +
                '}';
    }

    public Integer getMessageLength() {
        return messageLength;
    }

    public void setMessageLength(Integer messageLength) {
        this.messageLength = messageLength;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
