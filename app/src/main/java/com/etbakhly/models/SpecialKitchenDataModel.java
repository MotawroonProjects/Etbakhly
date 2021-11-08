package com.etbakhly.models;

import java.io.Serializable;
import java.util.List;

public class SpecialKitchenDataModel extends ResponseModel implements Serializable {
    private List<SpecialKitchenModel> data;

    public List<SpecialKitchenModel> getData() {
        return data;
    }
}
