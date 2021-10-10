package com.etbakhly.activities_fragments.food_truck.activity_home_food_truck.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.food_truck.activity_home_food_truck.HomeFoodTruckActivity;
import com.etbakhly.adapters.CartIndependentAdapter;
import com.etbakhly.databinding.FragmnetCartIndependentBinding;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FragmentCart extends Fragment {

    private HomeFoodTruckActivity activity;
    private FragmnetCartIndependentBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;
    private List<Object> list;

    public static FragmentCart newInstance() {
        return new FragmentCart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmnet_cart_independent, container, false);
        initView();
        return binding.getRoot();
    }


    private void initView() {
        list = new ArrayList<>();
        activity = (HomeFoodTruckActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        binding.recviewCart.setLayoutManager(new LinearLayoutManager(activity));
        binding.recviewCart.setAdapter(new CartIndependentAdapter(list, activity));
        binding.recviewCart2.setLayoutManager(new LinearLayoutManager(activity));
        binding.recviewCart2.setAdapter(new CartIndependentAdapter(list, activity));
        binding.recviewCart3.setLayoutManager(new LinearLayoutManager(activity));
        binding.recviewCart3.setAdapter(new CartIndependentAdapter(list, activity));
        binding.llCart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.elexpendcart1.isExpanded()) {
                    binding.elexpendcart1.setExpanded(false);
                    if (lang.equals("en")) {
                        binding.arrow1.setRotation(180);
                    } else {
                        binding.arrow1.setRotation(0);
                    }
                } else {
                    binding.elexpendcart1.setExpanded(true);
                    binding.arrow1.setRotation(-90);
                }
            }
        });
        binding.llCart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.elexpendcart2.isExpanded()) {
                    binding.elexpendcart2.setExpanded(false);
                    if (lang.equals("en")) {
                        binding.arrow2.setRotation(180);
                    } else {
                        binding.arrow2.setRotation(0);
                    }
                } else {
                    binding.elexpendcart2.setExpanded(true);
                    binding.arrow2.setRotation(-90);
                }
            }
        });
        binding.llCart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.elexpendcart3.isExpanded()) {
                    binding.elexpendcart3.setExpanded(false);
                    if (lang.equals("en")) {
                        binding.arrow3.setRotation(180);
                    } else {
                        binding.arrow3.setRotation(0);
                    }
                } else {
                    binding.elexpendcart3.setExpanded(true);
                    binding.arrow3.setRotation(-90);


                }
            }
        });
    }


}
