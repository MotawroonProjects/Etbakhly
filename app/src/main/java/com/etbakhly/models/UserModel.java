package com.etbakhly.models;

import java.io.Serializable;

public class UserModel {
    private int status;
    private User data;

    public User getUser() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public static class User implements Serializable {
        public int id;
        public String name;
        public int phone;
        public String phone_code;
        public String email;
        public String photo;
        public String yes_i_read_it;
        public String longitude;
        public String latitude;
        public String created_at;
        public String updated_at;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPhone() {
            return phone;
        }

        public String getPhone_code() {
            return phone_code;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoto() {
            return photo;
        }

        public String getYes_i_read_it() {
            return yes_i_read_it;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }


}
