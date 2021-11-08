package com.etbakhly.models;

import java.io.Serializable;
import java.util.List;

public class SliderDataModel extends ResponseModel implements Serializable {
    private List<SliderModel> data;

    public List<SliderModel> getData() {
        return data;
    }
}
