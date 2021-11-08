package com.etbakhly.models;

import java.io.Serializable;
import java.util.List;

public class MostFamousDataModel extends ResponseModel implements Serializable {
    private List<MostFamousModel> data;

    public List<MostFamousModel> getData() {
        return data;
    }
}
