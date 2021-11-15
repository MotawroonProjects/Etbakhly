
package com.etbakhly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.independent.activity_chief_indepndent.IndependentChiefActivity;
import com.etbakhly.databinding.OfferImageRowBinding;
import com.etbakhly.models.KitchenModel;

import java.util.List;

public class OfferImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<KitchenModel.Photo> list;
    private Context context;
    private LayoutInflater inflater;



    public OfferImagesAdapter(List<KitchenModel.Photo> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        OfferImageRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.offer_image_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof IndependentChiefActivity){
                    IndependentChiefActivity activity=(IndependentChiefActivity) context;
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
        public OfferImageRowBinding binding;

        public MyHolder(@NonNull OfferImageRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
