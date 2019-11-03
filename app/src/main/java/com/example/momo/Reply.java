package com.example.momo;

public class Reply {

    String userID;
    String create_at;
    String content;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Reply(String userID, String create_at, String content) {
        this.userID = userID;
        this.create_at = create_at;
        this.content = content;
    }
}

