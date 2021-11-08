package com.etbakhly.models;

import java.io.Serializable;

public class SliderModel implements Serializable {
    private int id;
    private String photo;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
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
