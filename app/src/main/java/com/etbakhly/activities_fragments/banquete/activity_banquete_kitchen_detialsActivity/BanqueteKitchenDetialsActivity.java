package com.etbakhly.activities_fragments.banquete.activity_banquete_kitchen_detialsActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.food_truck.activity_home_food_truck.fragments.Fragment_Home;
import com.etbakhly.adapters.MyPagerAdapter;
import com.etbakhly.adapters.SliderKitchenAdapter;
import com.etbakhly.databinding.ActivityKitchenDetialsBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.CategoryModel;
import com.etbakhly.models.KitchenDataModel;
import com.etbakhly.models.KitchenModel;
import com.etbakhly.models.SliderDataModel;
import com.etbakhly.models.SliderModel;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class BanqueteKitchenDetialsActivity extends AppCompatActivity {

    private ActivityKitchenDetialsBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;
    private MyPagerAdapter adapter;
    private List<String> titles;
    private List<Fragment> fragmentList;
    private KitchenModel kitchenModel;

    private List<KitchenModel.Photo> sliderModelList;
    private SliderKitchenAdapter sliderKitchenAdapter;
    private Timer timer;
    private TimerTask timerTask;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kitchen_detials);
        getDataFromIntent();

        initView();
        getSlider();
    }

    private void getSlider() {
        sliderModelList=new ArrayList<>();
        sliderModelList.addAll(kitchenModel.getPhotos());

        sliderKitchenAdapter=new SliderKitchenAdapter(sliderModelList,this);
        binding.pager1.setAdapter(sliderKitchenAdapter);


    }


    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
         binding.setModel(kitchenModel);
        // Log.e("dkkdk",lang);
        titles.add(getString(R.string.services));

        titles.add(getString(R.string.galarys));
        titles.add(getString(R.string.rate));

        fragmentList.add(FragmentServices.newInstance());
        fragmentList.add(FragmentOffers.newInstance(kitchenModel));

        fragmentList.add(FragmentRate.newInstance());

        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(fragmentList.size());

        adapter = new MyPagerAdapter(getSupportFragmentManager(), PagerAdapter.POSITION_UNCHANGED, titles, fragmentList);
        binding.pager.setAdapter(adapter);
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
            kitchenModel=(KitchenModel) intent.getSerializableExtra("data");
        }
    }
}
