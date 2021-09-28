package com.etbakhly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly.R;
import com.etbakhly.databinding.MostFamousChefRowBinding;

import java.util.List;

public class MostFamousChefAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;



    public MostFamousChefAdapter(List<Object> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        MostFamousChefRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.most_famous_chef_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;




    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public MostFamousChefRowBinding binding;

        public MyHolder(@NonNull MostFamousChefRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
