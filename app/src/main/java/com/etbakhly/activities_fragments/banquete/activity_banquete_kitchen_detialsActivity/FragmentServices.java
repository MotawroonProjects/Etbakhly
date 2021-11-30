package com.etbakhly.activities_fragments.banquete.activity_banquete_kitchen_detialsActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.banquete.activity_banquete_book.BanqueteBookActivity;
import com.etbakhly.activities_fragments.banquete.activity_buffet_banquete.BuffetActivity;
import com.etbakhly.databinding.DialogLayoutBinding;
import com.etbakhly.databinding.FragmnetServicesBanqueteBinding;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FragmentServices extends Fragment {

    private BanqueteKitchenDetialsActivity activity;
    private FragmnetServicesBanqueteBinding binding;
    private Preferences preferences;
    private String lang;
    private UserModel userModel;
    private List<Object> list;


    public static FragmentServices newInstance() {
        return new FragmentServices();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmnet_services_banquete, container, false);
        initView();

        return binding.getRoot();
    }


    private void createAlertDialog(Context context){
        final AlertDialog dialog =new AlertDialog.Builder(context)
                .create();
        DialogLayoutBinding binding=DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_layout,null,false);

        binding.card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent=new Intent(activity, BuffetActivity.class);
                startActivity(intent);
            }
        });

        binding.card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent=new Intent(activity, BanqueteBookActivity.class);
                startActivity(intent);

            }
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_dialog));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }
    private void initView() {
        list = new ArrayList<>();
        activity = (BanqueteKitchenDetialsActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");

binding.cardBuffet.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        createAlertDialog(activity);


    }
});
        binding.cardBanquete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }


}
