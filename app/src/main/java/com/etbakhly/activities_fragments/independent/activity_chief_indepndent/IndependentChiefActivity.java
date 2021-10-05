package com.etbakhly.activities_fragments.independent.activity_chief_indepndent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.independent.activity_chief_indepndent_detialsActivity.IndependentChiefDetialsActivity;
import com.etbakhly.adapters.CategoriesAdapter;
import com.etbakhly.adapters.IndependentChiefAdapter;
import com.etbakhly.databinding.ActivityIndependentChefBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class IndependentChiefActivity extends AppCompatActivity {

    private ActivityIndependentChefBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private List<Object> list;
    private String lang;
    private LinearLayoutManager layoutManager;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_independent_chef);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);


        list = new ArrayList<>();
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(new IndependentChiefAdapter(list, this));
       layoutManager =
                new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        binding.recviewCategories.setLayoutManager(layoutManager);
        binding.recviewCategories.setAdapter(new CategoriesAdapter(list, this));

        binding.flfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSheet();
            }
        });
        binding.flsortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSheet2();
            }
        });
        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeSheet();
            }
        });
        binding.btnOk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeSheet2();
            }
        });
        binding.imclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeSheet2();
            }
        });
    }

    public void show() {
        Intent intent = new Intent(this, IndependentChiefDetialsActivity.class);
        startActivity(intent);
    }

    public void openSheet() {
        binding.expandLayout.setExpanded(true, true);
    }

    public void closeSheet() {
        binding.expandLayout.collapse(true);

    }

    public void openSheet2() {
        binding.expandLayout2.setExpanded(true, true);
    }

    public void closeSheet2() {
        binding.expandLayout2.collapse(true);

    }
}
