package com.etbakhly.activities_fragments.banquete.activity_buffet_detials;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.independent.activity_chief_indepndent_detialsActivity.IndependentChiefDetialsActivity;
import com.etbakhly.adapters.BuffetBanqueteAdapter;
import com.etbakhly.adapters.FoodBuffetAdapter;
import com.etbakhly.databinding.ActivityBuffetBinding;
import com.etbakhly.databinding.ActivityBuffetDetialsBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class BuffetDetialsActivity extends AppCompatActivity {

    private ActivityBuffetDetialsBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private List<Object> list;
    private String lang;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buffet_detials);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);


        list = new ArrayList<>();
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(new FoodBuffetAdapter(list, this));


    }

    public void show() {
        Intent intent = new Intent(this, IndependentChiefDetialsActivity.class);
        startActivity(intent);
    }


}
