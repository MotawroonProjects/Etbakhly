package com.etbakhly.models;

import java.io.Serializable;

public class UserModel {
    private int status;
    private User data;

    public User getUser() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public static class User implements Serializable {

    }


}
