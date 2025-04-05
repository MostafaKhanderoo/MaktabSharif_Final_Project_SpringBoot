package com.example.maktabsharif.homeservices.authentication;

import com.example.maktabsharif.homeservices.entity.User;

public class AuthenticationUser {
    private static User loggedInUser;


    public static void setLoggedInUser(User user){

        loggedInUser = user;
    }
    public static User getLoggedInUser(){
        return loggedInUser;

    }
    public static void logout(){
        loggedInUser =null;
    }
}
