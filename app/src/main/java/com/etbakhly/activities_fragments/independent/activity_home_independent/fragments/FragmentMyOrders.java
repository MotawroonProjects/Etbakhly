package com.etbakhly.activities_fragments.independent.activity_home_independent.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.independent.activity_home_independent.HomeActivity;

import com.etbakhly.adapters.MyPagerAdapter;
import com.etbakhly.databinding.FragmnetMyordersIndependentBinding;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FragmentMyOrders extends Fragment {

    private HomeActivity activity;
    private FragmnetMyordersIndependentBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;
    private MyPagerAdapter adapter;
    private List<String> titles;
    private List<Fragment> fragmentList;

    public static FragmentMyOrders newInstance() {
        return new FragmentMyOrders();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmnet_myorders_independent, container, false);
        initView();
        return binding.getRoot();
    }


    private void initView() {

        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        Log.e("dkkdk",lang);
        titles.add(getString(R.string.current_orders));
        titles.add(getString(R.string.previous_orders));

        fragmentList.add(FragmentCurrentOrders.newInstance());
        fragmentList.add(FragmentPreviousOrder.newInstance());

        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(fragmentList.size());

        adapter = new MyPagerAdapter(getChildFragmentManager(), PagerAdapter.POSITION_UNCHANGED, titles, fragmentList);
        binding.pager.setAdapter(adapter);



    }


}
