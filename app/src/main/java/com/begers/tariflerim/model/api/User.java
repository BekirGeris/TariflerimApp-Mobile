package com.begers.tariflerim.model.api;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class User {

    @SerializedName("userId")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("firstName")
    @ColumnInfo(name = "firstName")
    private String firstName;

    @SerializedName("lastName")
    @ColumnInfo(name = "lastName")
    private String lastName;

    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String email;

    @SerializedName("password")
    @ColumnInfo(name = "password")
    private String password;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
