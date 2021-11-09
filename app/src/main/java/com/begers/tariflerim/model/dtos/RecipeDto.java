package com.begers.tariflerim.model.dtos;

import com.begers.tariflerim.model.Tarif;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeDto {

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private boolean success;

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private List<Tarif> data;

    public RecipeDto(String message, boolean success, int code, List<Tarif> data) {
        this.message = message;
        this.success = success;
        this.code = code;
        this.data = data;
    }

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

    public List<Tarif> getData() {
        return data;
    }

    public void setData(List<Tarif> data) {
        this.data = data;
    }
}
