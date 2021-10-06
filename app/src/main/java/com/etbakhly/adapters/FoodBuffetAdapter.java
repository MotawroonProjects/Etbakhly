package com.etbakhly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.banquete.activity_home_banquete.HomeBanqueteActivity;
import com.etbakhly.databinding.BuffetRowBinding;
import com.etbakhly.databinding.FoodbuffetRowBinding;

import java.util.List;

public class FoodBuffetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;
    private LinearLayoutManager layoutManager;


    public FoodBuffetAdapter(List<Object> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        FoodbuffetRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.foodbuffet_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;

        FoodDetialsAdapter foodDetialsAdapter = new FoodDetialsAdapter(list, context);
        layoutManager =
                new GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false);
        ((MyHolder) holder).binding.recView.setLayoutManager(layoutManager);
        ((MyHolder) holder).binding.recView.setAdapter(foodDetialsAdapter);

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public FoodbuffetRowBinding binding;

        public MyHolder(@NonNull FoodbuffetRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
