package com.etbakhly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly.R;
import com.etbakhly.databinding.CartIndependentRowBinding;
import com.etbakhly.databinding.MostFamousChefRowBinding;

import java.util.List;

public class CartIndependentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;



    public CartIndependentAdapter(List<Object> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        CartIndependentRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.cart_independent_row, parent, false);
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
        public CartIndependentRowBinding binding;

        public MyHolder(@NonNull CartIndependentRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
