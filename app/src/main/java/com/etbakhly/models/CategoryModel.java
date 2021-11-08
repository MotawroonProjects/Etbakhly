package com.etbakhly.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private int id;
    private String titel;
    private String photo;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getPhoto() {
        return photo;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
