package com.etbakhly.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class KitchenDataModel extends ResponseModel implements Serializable {
    private List<KitchenModel> data;
    private List<KitchenModel.Photo> getPhoto;

    public List<KitchenModel.Photo> getGetPhoto() {
        return getPhoto;
    }

    public Collection<? extends KitchenModel> getData() {
        return data;
    }
}
