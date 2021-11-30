package com.etbakhly.activities_fragments.banquete.activity_banquete_book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.banquete.activity_buffet_detials.BuffetDetialsActivity;
import com.etbakhly.adapters.BuffetBanqueteAdapter;
import com.etbakhly.adapters.CategoriesAdapter;
import com.etbakhly.adapters.ProdustIndependentAdapter;
import com.etbakhly.databinding.ActivityBanqueteBookBinding;
import com.etbakhly.databinding.ActivityBuffetBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.CategorDish;
import com.etbakhly.models.CategoryDataModel;
import com.etbakhly.models.CategoryDishDataModel;
import com.etbakhly.models.Dish;
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

public class BanqueteBookActivity extends AppCompatActivity {

    private ActivityBanqueteBookBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private List<CategorDish> categorDishList;
    private CategoriesAdapter categoriesAdapter;
    private List<Dish> dishList;
    private CategorDish categorDishModel;
    private ProdustIndependentAdapter independentAdapter;
    private String lang;
    private double total;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_banquete_book);

        initView();
        getCategory();

    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        categorDishList = new ArrayList<>();
        categoriesAdapter=new CategoriesAdapter(categorDishList,this);

        dishList=new ArrayList<>();
        independentAdapter=new ProdustIndependentAdapter(dishList,this);

        binding.recviewCategories.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.recviewCategories.setAdapter(categoriesAdapter);

        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(independentAdapter);
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategory();
            }
        });
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void calculateTotal() {
        total = 0;
        for (Dish model : dishList) {

            total += model.getPrice()*model.getQty();

        }

        binding.tvTotal.setText(total + "");
    }

    private void getCategory(){
        categorDishList.clear();
        dishList.clear();
        independentAdapter.notifyDataSetChanged();
        categoriesAdapter.notifyDataSetChanged();
        binding.recviewCategories.setVisibility(View.VISIBLE);

        binding.progBar.setVisibility(View.VISIBLE);

        Api.getService(Tags.base_url)
                .getDishes("all")
                .enqueue(new Callback<CategoryDishDataModel>() {
                    @Override
                    public void onResponse(Call<CategoryDishDataModel> call, Response<CategoryDishDataModel> response) {
                        binding.progBar.setVisibility(View.GONE);
                        binding.swipeRefresh.setRefreshing(false);
                        if (response.isSuccessful()){
                            if (response.body()!=null && response.body().getStatus()==200){
                                if (response.body().getData()!=null) {
                                    if (response.body().getData().size() > 0) {
                                        categorDishList.addAll(response.body().getData());
                                        categoriesAdapter.notifyDataSetChanged();
                                        binding.tvNoData.setVisibility(View.GONE);
                                    } else {
                                        binding.recviewCategories.setVisibility(View.GONE);
                                        binding.tvNoData.setVisibility(View.VISIBLE
                                        );
                                    }
                                }
                            }else {
                                binding.recviewCategories.setVisibility(View.GONE);
                            }
                        }else {
                            binding.recviewCategories.setVisibility(View.GONE);
                            switch (response.code()){
                                case 500:
                                    break;
                                default:
                                    break;
                            }try {
                                Log.e("error_code", response.code() + "_");
                            } catch (NullPointerException e) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryDishDataModel> call, Throwable t) {
                        try {

                            binding.progBar.setVisibility(View.GONE);
                            binding.recviewCategories.setVisibility(View.GONE);

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //     Toast.makeText(SignUpActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
                                } else {
                                    //  Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }catch (Exception e){

                        }
                    }
                });
    }



    public void show(CategorDish categorDish) {
        dishList.clear();
        dishList.addAll(categorDish.getDishes());
        independentAdapter.notifyDataSetChanged();
        if (dishList.size()<=0){
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
        else{
            binding.tvNoData.setVisibility(View.GONE);
        }
       // Intent intent = new Intent(this, BuffetDetialsActivity.class);
        //startActivity(intent);
    }


    public void increase_decrease(Dish dish2, int adapterPosition) {
        calculateTotal();

            }
}
