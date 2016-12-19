package com.android_project.kt.datrackchat.chat.dialogs;

/**
 * Created by garik on 14.12.16.
 */

public class DialogItem {

    private String name;
    private String dialog_uid;
    private String data;

    private String first_user_uid;
    private String second_user_uid;

    public DialogItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDialog_uid() {
        return dialog_uid;
    }

    public void setDialog_uid(String dialog_uid) {
        this.dialog_uid = dialog_uid;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getFirst_user_uid() {
        return first_user_uid;
    }

    public String getSecond_user_uid() {
        return second_user_uid;
    }

    public void setFirst_user_uid(String first_user_uid) {
        this.first_user_uid = first_user_uid;
    }

    public void setSecond_user_uid(String second_user_uid) {
        this.second_user_uid = second_user_uid;
    }
}
