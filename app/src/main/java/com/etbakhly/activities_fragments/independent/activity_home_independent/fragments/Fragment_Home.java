package com.etbakhly.activities_fragments.independent.activity_home_independent.fragments;

import android.os.Bundle;
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
import com.etbakhly.activities_fragments.independent.activity_home_independent.HomeActivity;

import com.etbakhly.adapters.CategoryAdapter;
import com.etbakhly.adapters.MostFamousChefAdapter;
import com.etbakhly.adapters.NearProductAdapter;
import com.etbakhly.adapters.SliderAdapter;
import com.etbakhly.adapters.SpecialDishesProductAdapter;
import com.etbakhly.databinding.FragmnetMainIndependentBinding;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class Fragment_Home extends Fragment {

    private HomeActivity activity;
    private FragmnetMainIndependentBinding binding;
    private Preferences preferences;
    private String lang;
    private UserModel userModel;
    private List<Object> list;
    private List<Object> sliderModelList;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmnet_main_independent, container, false);
        initView();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initView() {

        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        list = new ArrayList<>();
        binding.recviewMostFamous.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recviewMostFamous.setAdapter(new MostFamousChefAdapter(list, activity));
        binding.recviewCategory.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
      //  binding.recviewCategory.setAdapter(new CategoryAdapter(list, activity));
        binding.recviewnear.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recviewnear.setAdapter(new NearProductAdapter(list, activity));
        binding.recviewSpecial.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
       // binding.recviewSpecial.setAdapter(new SpecialDishesProductAdapter(list, activity));
        updateSliderUi();




        getSlider();

    }


    private void getSlider() {
//        Api.getService(Tags.base_url)
//                .getSlider()
//                .enqueue(new Callback<SliderDataModel>() {
//                    @Override
//                    public void onResponse(Call<SliderDataModel> call, Response<SliderDataModel> response) {
//                        binding.progBarSlider.setVisibility(View.GONE);
//                        if (response.isSuccessful() && response.body() != null && response.body().getStatus() == 200) {
//
//                            if (response.body().getData().size() > 0) {
//                                updateSliderUi(response.body().getData());
//
//                            } else {
//
//                                binding.flslider.setVisibility(View.GONE);
//                                binding.progBarSlider.setVisibility(View.GONE);
//                            }
//
//                        } else {
//
//                            binding.flslider.setVisibility(View.GONE);
//                            binding.progBarSlider.setVisibility(View.GONE);
//
//
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<SliderDataModel> call, Throwable t) {
//                        try {
//                            Log.e("error", t.getMessage() + "__");
//                            binding.flslider.setVisibility(View.GONE);
//                            binding.progBarSlider.setVisibility(View.GONE);
//                        } catch (Exception e) {
//
//                        }
//                    }
//                });
    }


    private void updateSliderUi() {
        sliderModelList = new ArrayList<>();

        sliderModelList.clear();
        sliderModelList.add(new Object());
        sliderModelList.add(new Object());
        sliderModelList.add(new Object());
        sliderModelList.add(new Object());

      //  sliderAdapter = new SliderAdapter(sliderModelList, activity);
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
