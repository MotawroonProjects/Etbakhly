package com.etbakhly.models;

import java.io.Serializable;

public class Dish implements Serializable {
    public int id;
    public int category_dishes_id;
    public String titel;
    public String photo;
    public int price;
    public String details;
    public int qty;
    public String created_at;
    public String updated_at;

    public int getId() {
        return id;
    }

    public int getCategory_dishes_id() {
        return category_dishes_id;
    }

    public String getTitel() {
        return titel;
    }

    public String getPhoto() {
        return photo;
    }

    public int getPrice() {
        return price;
    }

    public String getDetails() {
        return details;
    }

    public int getQty() {
        return qty;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
