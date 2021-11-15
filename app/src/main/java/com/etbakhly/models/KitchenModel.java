package com.etbakhly.models;

import java.io.Serializable;
import java.util.List;

public class KitchenModel implements Serializable {
    private int id;
    private String category_id;
    private String titel;
    private String notes;
    private int sex_type;
    private int is_delivry;
    private int is_delivry_freelance;
    private String delivry_time;
    private String processing_time;
    private String is_Special;
    private String free_delivery;
    private String longitude;
    private String latitude;
    private String sub_photo;
    private String created_at;
    private String updated_at;

    public List<Photo> getPhotos() {
        return photos;
    }

    public List<Photo> photos;

    public int getId() {
        return id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getTitel() {
        return titel;
    }

    public String getNotes() {
        return notes;
    }

    public int getSex_type() {
        return sex_type;
    }

    public int getIs_delivry() {
        return is_delivry;
    }

    public int getIs_delivry_freelance() {
        return is_delivry_freelance;
    }

    public String getDelivry_time() {
        return delivry_time;
    }

    public String getProcessing_time() {
        return processing_time;
    }

    public String getIs_Special() {
        return is_Special;
    }

    public String getFree_delivery() {
        return free_delivery;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getSub_photo() {
        return sub_photo;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public class Photo implements Serializable{
        public int id;
        public int caterers_id;
        public String photo;
        public String created_at;
        public String updated_at;

        public int getId() {
            return id;
        }

        public int getCaterers_id() {
            return caterers_id;
        }

        public String getPhoto() {
            return photo;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}

