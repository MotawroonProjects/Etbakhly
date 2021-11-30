package com.etbakhly.models;

import java.io.Serializable;

public class Dish implements Serializable {
    private int id;
    private int category_dishes_id;
    private String titel;
    private String photo;
    private int price;
    private String details;
    private int qty;
    private String created_at;
    private String updated_at;



    public void setQty(int qty) {
        this.qty = qty;
    }

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
