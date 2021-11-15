package com.etbakhly.activities_fragments.food_truck.activity_food_truck_detialsActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.etbakhly.R;
import com.etbakhly.adapters.OfferImagesAdapter;
import com.etbakhly.databinding.FragmentOrdersBinding;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FragmentOffers extends Fragment {

    private FoodTruckDetialsActivity activity;
    private FragmentOrdersBinding binding;
    private Preferences preferences;
    private String lang;
    private UserModel userModel;
private List<Object> list;


    public static FragmentOffers newInstance() {
        return new FragmentOffers();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);
        initView();

        return binding.getRoot();
    }


    private void initView() {
        list=new ArrayList<>();
        activity = (FoodTruckDetialsActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        binding.recView.setLayoutManager(new GridLayoutManager(activity,3));
       // binding.recView.setAdapter(new OfferImagesAdapter(list, activity));
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }


}
