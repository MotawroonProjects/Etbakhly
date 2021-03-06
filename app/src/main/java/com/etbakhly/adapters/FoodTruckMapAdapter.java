package com.etbakhly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.food_truck.activity_map_food_truck.MapFoodTruckActivity;
import com.etbakhly.databinding.FoodTruckMapRowBinding;

import java.util.List;

public class FoodTruckMapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;



    public FoodTruckMapAdapter(List<Object> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        FoodTruckMapRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.food_truck_map_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
myHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(context instanceof MapFoodTruckActivity){
            MapFoodTruckActivity activity=(MapFoodTruckActivity) context;
            activity.show();
        }
    }
});



    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public FoodTruckMapRowBinding binding;

        public MyHolder(@NonNull FoodTruckMapRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
