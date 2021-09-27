package com.etbakhly.models;

import java.io.Serializable;
import java.util.List;

public class UserDataModel extends StatusResponse implements Serializable {
    private List<UserModel.User> data;

    public List<UserModel.User> getData() {
        return data;
    }
}
