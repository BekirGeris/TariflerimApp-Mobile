package com.begers.tariflerim.utiles;

import com.begers.tariflerim.model.User;

public class SingletonUser {

    private User sentUser;

    private static SingletonUser singletonUser;

    private SingletonUser(){

    }

    public User getSentUser(){
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
}
