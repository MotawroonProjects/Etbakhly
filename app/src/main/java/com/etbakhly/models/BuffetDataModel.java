package com.etbakhly.models;

import java.io.Serializable;
import java.util.List;

public class BuffetDataModel extends ResponseModel implements Serializable {
    public List<BuffetModel> data;

    public List<BuffetModel> getData() {
        return data;
    }

}
