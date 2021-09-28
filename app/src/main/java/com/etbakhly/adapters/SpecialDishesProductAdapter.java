package com.etbakhly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly.R;
import com.etbakhly.databinding.NearProductRowBinding;
import com.etbakhly.databinding.SpecialDishesProductRowBinding;

import java.util.List;

public class SpecialDishesProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;



    public SpecialDishesProductAdapter(List<Object> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        SpecialDishesProductRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.special_dishes_product_row, parent, false);
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
        public SpecialDishesProductRowBinding binding;

        public MyHolder(@NonNull SpecialDishesProductRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
