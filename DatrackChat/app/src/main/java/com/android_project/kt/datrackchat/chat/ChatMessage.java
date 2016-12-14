package com.android_project.kt.datrackchat.chat;

public class ChatMessage {

    private String text;
    private String name;

    public ChatMessage() {
    }

    public ChatMessage(String text, String name) {
        this.text = text;
        this.name = name;
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
}