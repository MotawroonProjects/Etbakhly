package com.etbakhly.models;

import java.io.Serializable;
import java.util.List;

public class CategoryDataModel extends ResponseModel implements Serializable {
    private List<CategoryModel> data;

    public List<CategoryModel> getData() {
        return data;
    }
}
