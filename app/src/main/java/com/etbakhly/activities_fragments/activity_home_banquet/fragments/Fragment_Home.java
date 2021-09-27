package com.etbakhly.activities_fragments.activity_home_banquet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.activity_home_banquet.HomeActivity;

import com.etbakhly.databinding.FragmnetMainBanquetBinding;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import io.paperdb.Paper;

public class Fragment_Home extends Fragment {

    private HomeActivity activity;
    private FragmnetMainBanquetBinding binding;
    private Preferences preferences;
    private String lang;
    private UserModel userModel;


    public static Fragment_Home newInstance() {
        return new Fragment_Home();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmnet_main_banquet, container, false);
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


    }



}
