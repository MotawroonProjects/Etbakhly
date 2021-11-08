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
import com.etbakhly.databinding.KitvhenBanqueteRowBinding;
import com.etbakhly.models.MostFamousModel;

import java.util.List;

public class KitchenBanqueteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MostFamousModel> list;
    private Context context;
    private LayoutInflater inflater;



    public KitchenBanqueteAdapter(List<MostFamousModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        KitvhenBanqueteRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.kitvhen_banquete_row, parent, false);
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
        public KitvhenBanqueteRowBinding binding;

        public MyHolder(@NonNull KitvhenBanqueteRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
