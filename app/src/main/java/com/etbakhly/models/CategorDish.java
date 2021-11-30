package com.etbakhly.models;

import java.io.Serializable;
import java.util.List;

public class CategorDish implements Serializable {
    private int id;
    private String titel;
    private String created_at;
    private String updated_at;
    private List<Dish> dishes;
    private double total;

    public double getTotal() {
        return total;
    }

    public int getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public List<Dish> getDishes() {
        return dishes;
    }
}
