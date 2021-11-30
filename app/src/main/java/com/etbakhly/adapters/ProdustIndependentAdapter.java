package com.etbakhly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.banquete.activity_banquete_book.BanqueteBookActivity;
import com.etbakhly.databinding.ProductIndependentRowBinding;
import com.etbakhly.models.Dish;

import java.util.List;

public class ProdustIndependentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Dish> list;
    private Context context;
    private LayoutInflater inflater;



    public ProdustIndependentAdapter(List<Dish> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ProductIndependentRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.product_independent_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        ((MyHolder) holder).binding.tvAmount.setText("1");

        myHolder.binding.imageIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dish dish2=list.get(myHolder.getAdapterPosition());
                if (context instanceof BanqueteBookActivity){
                    BanqueteBookActivity bookActivity=(BanqueteBookActivity) context;
                    int amount=Integer.parseInt(((MyHolder) holder).binding.tvAmount.getText().toString())+1;
                    dish2.setQty(amount);
                    ((MyHolder) holder).binding.tvAmount.setText(amount+"");
                    bookActivity.increase_decrease(dish2,myHolder.getAdapterPosition());
                }

            }
        });

        myHolder.binding.imageDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dish dish2=list.get(myHolder.getAdapterPosition());
                if (context instanceof BanqueteBookActivity){
                    BanqueteBookActivity bookActivity=(BanqueteBookActivity) context;
                    int amount=Integer.parseInt(((MyHolder) holder).binding.tvAmount.getText().toString())-1;
                    dish2.setQty(amount);
                    ((MyHolder) holder).binding.tvAmount.setText(amount+"");

                    bookActivity.increase_decrease(dish2,myHolder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public ProductIndependentRowBinding binding;

        public MyHolder(@NonNull ProductIndependentRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
