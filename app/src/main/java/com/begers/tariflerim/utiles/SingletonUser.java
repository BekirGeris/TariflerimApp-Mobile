package com.begers.tariflerim.utiles;

import com.begers.tariflerim.model.api.User;

public class SingletonUser {

    private static User sentUser;

    private static SingletonUser singletonUser;

    private SingletonUser(){

    }

    public User getSentUser(){
        if (sentUser == null){
            sentUser = new User("Default", "Default", "Default", "Default");
        }
        return sentUser;
    }

    public void setSentUser(User sentUser){
        this.sentUser = sentUser;
    }

    public static SingletonUser getInstance(){
        if (singletonUser == null){
            singletonUser = new SingletonUser();
        }
        return singletonUser;
    }

    public void clearUser(){
        this.sentUser = null;
    }
}
