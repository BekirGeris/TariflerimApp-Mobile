package com.begers.tariflerim.model.dtos;

import com.begers.tariflerim.model.api.Image;
import com.begers.tariflerim.model.roomdb.User;
import com.google.gson.annotations.SerializedName;

public class ImageDto {

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private boolean success;

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private Image data;

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

    public Image getData() {
        return data;
    }

    public void setData(Image data) {
        this.data = data;
    }
}
