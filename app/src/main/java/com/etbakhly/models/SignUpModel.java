package com.etbakhly.models;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import com.etbakhly.R;

public class SignUpModel extends BaseObservable {
    private String name;
    private  String email;

    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_email = new ObservableField<>();



    public boolean isDataValid(Context context){
        if (!name.trim().isEmpty() && !email.trim().isEmpty()) {

            error_name.set(null);
            error_email.set(null);

            return true;
        } else {

            if (name.trim().isEmpty()) {
                error_name.set(context.getString(R.string.field_req));

            } else {
                error_name.set(null);

            }
            if (email.trim().isEmpty()) {
                error_email.set(context.getString(R.string.field_req));

            } else {
                error_email.set(null);

            }
            return false;
        }
    }
    public SignUpModel() {
        setName("");
        setEmail("");

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
