package com.dzakdzaks.ta_umroh.user.response;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdate {

    @SerializedName("result")
    private String result;

    @SerializedName("msg")
    private String msg;

    @SerializedName("user")
    private User user;

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}