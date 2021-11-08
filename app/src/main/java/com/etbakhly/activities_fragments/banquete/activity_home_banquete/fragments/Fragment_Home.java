package com.etbakhly.activities_fragments.banquete.activity_home_banquete.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.banquete.activity_home_banquete.HomeBanqueteActivity;
import com.etbakhly.adapters.CategoryAdapter;
import com.etbakhly.adapters.KitchenBanqueteAdapter;
import com.etbakhly.adapters.SliderAdapter;
import com.etbakhly.adapters.SpecialDishesProductAdapter;
import com.etbakhly.databinding.FragmnetMainBanqueteBinding;
import com.etbakhly.models.CategoryDataModel;
import com.etbakhly.models.CategoryModel;
import com.etbakhly.models.MostFamousDataModel;
import com.etbakhly.models.MostFamousModel;
import com.etbakhly.models.SliderDataModel;
import com.etbakhly.models.SliderModel;
import com.etbakhly.models.UserModel;
import com.etbakhly.models.SpecialKitchenDataModel;
import com.etbakhly.models.SpecialKitchenModel;
import com.etbakhly.preferences.Preferences;
import com.etbakhly.remote.Api;
import com.etbakhly.tags.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Home extends Fragment {

    private HomeBanqueteActivity activity;
    private FragmnetMainBanqueteBinding binding;
    private Preferences preferences;
    private String lang;
    private UserModel userModel;
    private List<CategoryModel> categoryModelList;
    private CategoryAdapter categoryAdapter;
    private List<MostFamousModel> mostFamousModelList;
    private KitchenBanqueteAdapter kitchenBanqueteAdapter;
    private List<SpecialKitchenModel> specialKitchenModelList;
    private SpecialDishesProductAdapter dishesProductAdapter;
    private List<SpecialKitchenModel> provideFreeDelivery;
    private SpecialDishesProductAdapter dishesProductAdapter2;

    private List<SliderModel> sliderModelList;

    //    private SliderAdapter sliderAdapter;
//    private List<SliderModel> sliderModelList;
//    private Timer timer;
//    private TimerTask timerTask;
    private Timer timer;
    private TimerTask timerTask;
    private SliderAdapter sliderAdapter;
    public static Fragment_Home newInstance() {
        return new Fragment_Home();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmnet_main_banquete, container, false);
        initView();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initView() {

        activity = (HomeBanqueteActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        categoryModelList = new ArrayList<>();
        categoryAdapter=new CategoryAdapter(categoryModelList,activity);

        mostFamousModelList=new ArrayList<>();
        kitchenBanqueteAdapter=new KitchenBanqueteAdapter(mostFamousModelList,activity);

        specialKitchenModelList=new ArrayList<>();
        dishesProductAdapter=new SpecialDishesProductAdapter(specialKitchenModelList,activity);

        provideFreeDelivery=new ArrayList<>();
        dishesProductAdapter2=new SpecialDishesProductAdapter(provideFreeDelivery,activity);

        binding.recviewCategory.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recviewCategory.setAdapter(categoryAdapter);

        binding.recviewnear.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recviewnear.setAdapter(kitchenBanqueteAdapter);

        binding.recviewSpecial.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recviewSpecial.setAdapter(dishesProductAdapter);

        binding.recviewdelivry.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recviewdelivry.setAdapter(dishesProductAdapter2);
        getCategory();
        getFamous();
        getSpecialKitchen();
        getFreeDelivery();
        getSlider();

    }
    private void updateSliderUi(SliderDataModel body) {
        sliderModelList = new ArrayList<>();

sliderModelList.addAll(body.getData());

        sliderAdapter = new SliderAdapter(sliderModelList, activity);
        binding.pager.setAdapter(sliderAdapter);
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setClipToPadding(false);
        binding.pager.setOffscreenPageLimit(sliderModelList.size());
        //binding.flslider.setVisibility(View.VISIBLE);
        binding.pager.setVisibility(View.VISIBLE);

        if (sliderModelList.size() > 1) {
            timer = new Timer();
            timerTask = new MyTask();
            timer.scheduleAtFixedRate(timerTask, 6000, 6000);
        }
    }
    private void getCategory(){
        categoryModelList.clear();
        categoryAdapter.notifyDataSetChanged();
        binding.progBarCategory.setVisibility(View.VISIBLE);

        binding.flCategory.setVisibility(View.VISIBLE);

        Api.getService(Tags.base_url)
                .getCategory()
                .enqueue(new Callback<CategoryDataModel>() {
                    @Override
                    public void onResponse(Call<CategoryDataModel> call, Response<CategoryDataModel> response) {
                        binding.progBarCategory.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body()!=null && response.body().getStatus()==200){
                                if (response.body().getData()!=null){
                                    if (response.body().getData().size()>0){
                                        categoryModelList.addAll(response.body().getData());
                                        categoryAdapter.notifyDataSetChanged();
                                    }else {
                                        binding.flCategory.setVisibility(View.GONE);
                                    }
                                }
                            }else {
                                binding.flCategory.setVisibility(View.GONE);
                            }
                        }else {
                            binding.flCategory.setVisibility(View.GONE);
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
                    public void onFailure(Call<CategoryDataModel> call, Throwable t) {
                        try {

                            binding.progBarCategory.setVisibility(View.GONE);
                            binding.flCategory.setVisibility(View.GONE);

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
    private void getFamous(){
        mostFamousModelList.clear();
        kitchenBanqueteAdapter.notifyDataSetChanged();
        binding.progBarMostFamous.setVisibility(View.VISIBLE);

        binding.flMostFamous.setVisibility(View.VISIBLE);

        Api.getService(Tags.base_url)
                .getFamous("30.561057958676955","31.008107521307437")
                .enqueue(new Callback<MostFamousDataModel>() {
                    @Override
                    public void onResponse(Call<MostFamousDataModel> call, Response<MostFamousDataModel> response) {
                        binding.progBarMostFamous.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body()!=null && response.body().getStatus()==200){
                                if (response.body().getData()!=null){
                                    if (response.body().getData().size()>0){
                                        mostFamousModelList.addAll(response.body().getData());
                                        kitchenBanqueteAdapter.notifyDataSetChanged();
                                    }else {
                                        binding.flMostFamous.setVisibility(View.GONE);
                                    }
                                }
                            }else {
                                binding.flMostFamous.setVisibility(View.GONE);

                            }
                        }else {
                            binding.flMostFamous.setVisibility(View.GONE);
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
                    public void onFailure(Call<MostFamousDataModel> call, Throwable t) {

                        try {

                            binding.progBarMostFamous.setVisibility(View.GONE);
                            binding.flMostFamous.setVisibility(View.GONE);

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
    private void getSpecialKitchen(){
        specialKitchenModelList.clear();
        dishesProductAdapter.notifyDataSetChanged();

        binding.progBarSpecial.setVisibility(View.VISIBLE);

        binding.flSpecial.setVisibility(View.VISIBLE);
        Api.getService(Tags.base_url)
                .getSpecialKitchen()
                .enqueue(new Callback<SpecialKitchenDataModel>() {
                    @Override
                    public void onResponse(Call<SpecialKitchenDataModel> call, Response<SpecialKitchenDataModel> response) {
                        binding.progBarSpecial.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body()!=null && response.body().getStatus()==200){
                                if (response.body().getData()!=null){
                                    if (response.body().getData().size()>0){
                                        specialKitchenModelList.addAll(response.body().getData());
                                        dishesProductAdapter.notifyDataSetChanged();
                                    }else {
                                        binding.flSpecial.setVisibility(View.GONE);
                                    }
                                }
                            }else {
                                binding.flSpecial.setVisibility(View.GONE);
                            }
                        }else {
                            binding.flSpecial.setVisibility(View.GONE);
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
                    public void onFailure(Call<SpecialKitchenDataModel> call, Throwable t) {

                        try {

                            binding.progBarSpecial.setVisibility(View.GONE);
                            binding.flSpecial.setVisibility(View.GONE);

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
    private void getFreeDelivery(){
        provideFreeDelivery.clear();
        dishesProductAdapter2.notifyDataSetChanged();
        binding.progBarDelivery.setVisibility(View.VISIBLE);
        binding.flDelivery.setVisibility(View.VISIBLE);
        Api.getService(Tags.base_url)
                .getFreeDelivery()
                .enqueue(new Callback<SpecialKitchenDataModel>() {
                    @Override
                    public void onResponse(Call<SpecialKitchenDataModel> call, Response<SpecialKitchenDataModel> response) {
                        binding.progBarDelivery.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body()!=null && response.body().getStatus()==200){
                                if (response.body().getData()!=null){
                                    if (response.body().getData().size()>0){
                                        provideFreeDelivery.addAll(response.body().getData());
                                        dishesProductAdapter2.notifyDataSetChanged();
                                    }else {
                                        binding.flDelivery.setVisibility(View.GONE);
                                    }
                                }
                            }else {
                                binding.flDelivery.setVisibility(View.GONE);
                            }
                        }else {
                            binding.flDelivery.setVisibility(View.GONE);
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
                    public void onFailure(Call<SpecialKitchenDataModel> call, Throwable t) {

                        try {

                            binding.progBarDelivery.setVisibility(View.GONE);
                            binding.flDelivery.setVisibility(View.GONE);

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


    private void getSlider() {
      Api.getService(Tags.base_url)
              .getSlider()
              .enqueue(new Callback<SliderDataModel>() {
                  @Override
                  public void onResponse(Call<SliderDataModel> call, Response<SliderDataModel> response) {
                      binding.progBarSlider.setVisibility(View.GONE);
                      if (response.isSuccessful() && response.body() != null && response.body().getStatus() == 200) {

                          if (response.body().getData().size() > 0) {
                              updateSliderUi(response.body());

                          } else {

                              binding.flSlider.setVisibility(View.GONE);
                              binding.progBarSlider.setVisibility(View.GONE);
                          }

                      } else {

                          binding.flSlider.setVisibility(View.GONE);
                          binding.progBarSlider.setVisibility(View.GONE);


                      }


                  }

                  @Override
                  public void onFailure(Call<SliderDataModel> call, Throwable t) {
                      try {
                          Log.e("error", t.getMessage() + "__");
                          binding.flSlider.setVisibility(View.GONE);
                          binding.progBarSlider.setVisibility(View.GONE);
                      } catch (Exception e) {

                      }
                  }
              });
    }



    public class MyTask extends TimerTask {
        @Override
        public void run() {
            activity.runOnUiThread(() -> {
                int current_page = binding.pager.getCurrentItem();
                if (current_page < sliderAdapter.getCount() - 1) {
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem() + 1);
                } else {
                    binding.pager.setCurrentItem(0);

                }
            });

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.purge();
            timer.cancel();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }

    }

}
