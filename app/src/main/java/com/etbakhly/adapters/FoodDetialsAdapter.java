package com.etbakhly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.banquete.activity_home_banquete.HomeBanqueteActivity;
import com.etbakhly.databinding.FoodDetialsRowBinding;
import com.etbakhly.models.Dish;

import java.util.List;

public class FoodDetialsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Dish> list;
    private Context context;
    private LayoutInflater inflater;



    public FoodDetialsAdapter(List<Dish> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        FoodDetialsRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.food_detials_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
myHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(context instanceof HomeBanqueteActivity){
            HomeBanqueteActivity activity=(HomeBanqueteActivity) context;
            activity.show();
        }
    }
});



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public FoodDetialsRowBinding binding;

        public MyHolder(@NonNull FoodDetialsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
