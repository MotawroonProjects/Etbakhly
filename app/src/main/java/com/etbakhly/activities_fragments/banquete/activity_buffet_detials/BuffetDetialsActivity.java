package com.etbakhly.activities_fragments.banquete.activity_buffet_detials;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.independent.activity_chief_indepndent_detialsActivity.IndependentChiefDetialsActivity;
import com.etbakhly.adapters.FoodBuffetAdapter;
import com.etbakhly.databinding.ActivityBuffetDetialsBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.BuffetModel;
import com.etbakhly.models.CategorDish;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class BuffetDetialsActivity extends AppCompatActivity {

    private ActivityBuffetDetialsBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private List<CategorDish> list;
    BuffetModel buffetModel;
    FoodBuffetAdapter foodBuffetAdapter;
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

        getDataFromIntent();
        initView();

    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setModel(buffetModel);


        list = new ArrayList<>();
        list.addAll(buffetModel.getCategor_dishes());
        foodBuffetAdapter=new FoodBuffetAdapter(list,this);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
         binding.recView.setAdapter(foodBuffetAdapter);
         binding.llBack.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });
    }
    private void getDataFromIntent(){
        Intent intent=getIntent();
        if (intent!=null){
            buffetModel = (BuffetModel) intent.getSerializableExtra("data");
        }
    }

    public void show() {
        Intent intent = new Intent(this, IndependentChiefDetialsActivity.class);
        startActivity(intent);
    }


}
