package com.etbakhly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.banquete.activity_home_banquete.HomeBanqueteActivity;
import com.etbakhly.activities_fragments.banquete.activity_home_banquete.fragments.Fragment_Home;
import com.etbakhly.activities_fragments.food_truck.activity_home_food_truck.HomeFoodTruckActivity;
import com.etbakhly.databinding.CategoryRowBinding;
import com.etbakhly.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CategoryModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public CategoryAdapter(List<CategoryModel> list, Context context, Fragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment=fragment;
        inflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        CategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.category_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragment instanceof Fragment_Home) {
                    Fragment_Home fragment_home = (Fragment_Home) fragment;
                    fragment_home.showCategory(list.get(holder.getLayoutPosition()));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public CategoryRowBinding binding;

        public MyHolder(@NonNull CategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
