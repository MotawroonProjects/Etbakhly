package com.etbakhly.models;

import java.io.Serializable;
import java.util.List;

public class CategorDish implements Serializable {
    public int id;
    public String titel;
    public String created_at;
    public String updated_at;
    public List<Dish> dishes;

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
