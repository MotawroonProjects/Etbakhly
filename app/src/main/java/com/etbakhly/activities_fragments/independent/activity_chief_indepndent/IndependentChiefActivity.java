package com.etbakhly.activities_fragments.independent.activity_chief_indepndent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.independent.activity_chief_indepndent_detialsActivity.IndependentChiefDetialsActivity;
import com.etbakhly.adapters.IndependentChiefAdapter;
import com.etbakhly.databinding.ActivityIndependentChefBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.CategoryModel;
import com.etbakhly.models.KitchenDataModel;
import com.etbakhly.models.KitchenModel;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;
import com.etbakhly.remote.Api;
import com.etbakhly.tags.Tags;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndependentChiefActivity extends AppCompatActivity {

    private ActivityIndependentChefBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private List<KitchenModel> chiefModelList;
    private IndependentChiefAdapter independentChiefAdapter;
    private String lang;
    private CategoryModel categoryModel;
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
       initView();
        getKitchenCategory();
        getDataFromIntent();
    }
    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);


        chiefModelList = new ArrayList<>();
        independentChiefAdapter=new IndependentChiefAdapter(chiefModelList,this);

        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(independentChiefAdapter);
        layoutManager =
                new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        binding.recviewCategories.setLayoutManager(layoutManager);
        // binding.recviewCategories.setAdapter(new CategoriesAdapter(list, this));

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

    private void getDataFromIntent(){
        Intent intent=getIntent();
        if (intent!=null){
            categoryModel=(CategoryModel) intent.getSerializableExtra("data");
        }
    }

    private void getKitchenCategory() {
        chiefModelList.clear();
        independentChiefAdapter.notifyDataSetChanged();
        binding.progBar.setVisibility(View.VISIBLE);

        Api.getService(Tags.base_url)
                .getKitchenCategory("all")
                .enqueue(new Callback<KitchenDataModel>() {
                    @Override
                    public void onResponse(Call<KitchenDataModel> call, Response<KitchenDataModel> response) {
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                if (response.body().getData() != null) {
                                    if (response.body().getData().size() > 0) {
                                        chiefModelList.addAll(response.body().getData());
                                        independentChiefAdapter.notifyDataSetChanged();
                                    } else {
                                        binding.recView.setVisibility(View.GONE);
                                    }
                                }
                            } else {
                                binding.recView.setVisibility(View.GONE);
                            }
                        } else {
                            binding.recView.setVisibility(View.GONE);
                            switch (response.code()) {
                                case 500:
                                    break;
                                default:
                                    break;
                            }
                            try {
                                Log.e("error_code", response.code() + "_");
                            } catch (NullPointerException e) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<KitchenDataModel> call, Throwable t) {
                        try {

                            binding.progBar.setVisibility(View.GONE);
                            binding.recView.setVisibility(View.GONE);

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //     Toast.makeText(SignUpActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
                                } else {
                                    //  Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                });
    }



    public void show() {
        Intent intent = new Intent(this, IndependentChiefDetialsActivity.class);
        startActivity(intent);
    }

    private void openSheet() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        binding.expandLayout.clearAnimation();
        binding.expandLayout.startAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.expandLayout.setExpanded(true,true);


            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void closeSheet() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        binding.expandLayout.clearAnimation();
        binding.expandLayout.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.expandLayout.collapse(true);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void openSheet2() {
        binding.expandLayout2.setExpanded(true, true);
    }

    public void closeSheet2() {
        binding.expandLayout2.collapse(true);

    }
}
