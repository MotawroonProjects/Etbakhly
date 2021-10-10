package com.etbakhly.activities_fragments.food_truck.activity_food_truck_detialsActivity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.etbakhly.R;
import com.etbakhly.adapters.MyPagerAdapter;
import com.etbakhly.databinding.ActivityFoodTruckDetialsBinding;
import com.etbakhly.databinding.ActivityKitchenDetialsBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FoodTruckDetialsActivity extends AppCompatActivity {

    private ActivityFoodTruckDetialsBinding binding;
    private Preferences preferences;
    private UserModel userModel;
private String lang;
    private MyPagerAdapter adapter;
    private List<String> titles;
    private List<Fragment> fragmentList;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food_truck_detials);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
       // Log.e("dkkdk",lang);
        titles.add(getString(R.string.services));

        titles.add(getString(R.string.galarys));
        titles.add(getString(R.string.rate));

        fragmentList.add(FragmentServices.newInstance());
        fragmentList.add(FragmentOffers.newInstance());

        fragmentList.add(FragmentRate.newInstance());

        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(fragmentList.size());

        adapter = new MyPagerAdapter(getSupportFragmentManager(), PagerAdapter.POSITION_UNCHANGED, titles, fragmentList);
        binding.pager.setAdapter(adapter);


    }
}
