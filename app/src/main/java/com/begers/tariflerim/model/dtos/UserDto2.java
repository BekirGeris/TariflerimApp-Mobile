package com.begers.tariflerim.model.dtos;

import com.begers.tariflerim.model.roomdb.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDto2 {

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private boolean success;

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private User data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}