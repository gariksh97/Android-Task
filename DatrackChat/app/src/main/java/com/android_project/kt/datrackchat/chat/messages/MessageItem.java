package com.android_project.kt.datrackchat.chat.messages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MessageItem {

    private String text;
    private String name;
    private String data;

    public MessageItem() {}

    public MessageItem(String text, String name) {
        this.text = text;
        this.name = name;
        SimpleDateFormat moscowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        moscowTime.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        this.data = moscowTime.format(new Date());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}