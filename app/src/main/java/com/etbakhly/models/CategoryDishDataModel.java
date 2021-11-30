package com.etbakhly.models;

import java.io.Serializable;
import java.util.List;

public class CategoryDishDataModel extends ResponseModel implements Serializable {
    private List<CategorDish> data;
    private List<Dish> dishes;

    public List<Dish> getDishes() {
        return dishes;
    }

    public List<CategorDish> getData() {
        return data;
    }
}
