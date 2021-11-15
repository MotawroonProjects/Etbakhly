package com.etbakhly.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.banquete.activity_buffet_banquete.BuffetActivity;
import com.etbakhly.databinding.BuffetRowBinding;
import com.etbakhly.models.BuffetModel;

import java.util.List;

public class BuffetBanqueteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BuffetModel> list;
    private Context context;
    private LayoutInflater inflater;
    Activity activity;



    public BuffetBanqueteAdapter(List<BuffetModel> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity=activity;
        inflater = LayoutInflater.from(context);


    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        BuffetRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.buffet_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(activity instanceof BuffetActivity){
            BuffetActivity activityBuffet=(BuffetActivity) activity;
            activityBuffet.showBuffet(list.get(holder.getLayoutPosition()));
        }
    }
});



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public BuffetRowBinding binding;

        public MyHolder(@NonNull BuffetRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
