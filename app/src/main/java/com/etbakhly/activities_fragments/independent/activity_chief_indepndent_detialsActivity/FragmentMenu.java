package com.etbakhly.activities_fragments.independent.activity_chief_indepndent_detialsActivity;

import android.content.Context;
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
import com.etbakhly.adapters.ProdustIndependentAdapter;
import com.etbakhly.databinding.FragmnetMenuIndependentBinding;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FragmentMenu extends Fragment {

    private IndependentChiefDetialsActivity activity;
    private FragmnetMenuIndependentBinding binding;
    private Preferences preferences;
    private String lang;
    private UserModel userModel;
    private List<Object> list;


    public static FragmentMenu newInstance() {
        return new FragmentMenu();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmnet_menu_independent, container, false);
        initView();

        return binding.getRoot();
    }


    private void initView() {
        list = new ArrayList<>();
        activity = (IndependentChiefDetialsActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        binding.recViewMost.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewOffers.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewMainDishes.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewMost.setAdapter(new ProdustIndependentAdapter(list, activity));
        binding.recViewOffers.setAdapter(new ProdustIndependentAdapter(list, activity));
        binding.recViewMainDishes.setAdapter(new ProdustIndependentAdapter(list, activity));
        binding.llMainDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.elexpendMainDishes.isExpanded()) {
                    binding.elexpendMainDishes.setExpanded(false);
                    if (lang.equals("en")) {
                        binding.arrow3.setRotation(180);
                    } else {
                        binding.arrow3.setRotation(0);
                    }
                } else {
                    binding.elexpendMainDishes.setExpanded(true);
                    binding.arrow3.setRotation(-90);
                }
            }
        });
        binding.llOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.elexpendOffers.isExpanded()) {
                    binding.elexpendOffers.setExpanded(false);
                    if (lang.equals("en")) {
                        binding.arrow2.setRotation(180);
                    } else {
                        binding.arrow2.setRotation(0);
                    }
                } else {
                    binding.elexpendOffers.setExpanded(true);
                    binding.arrow2.setRotation(-90);
                }
            }
        });
        binding.llMost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.elexpendMost.isExpanded()) {
                    binding.elexpendMost.setExpanded(false);
                    if (lang.equals("en")) {
                        binding.arrow.setRotation(180);
                    } else {
                        binding.arrow.setRotation(0);
                    }
                } else {
                    binding.elexpendMost.setExpanded(true);
                    binding.arrow.setRotation(-90);


                }
            }
        });

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }


}
