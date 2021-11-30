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
import com.etbakhly.databinding.CategoriesRowBinding;
import com.etbakhly.models.CategorDish;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CategorDish> list;
    private Context context;
    private LayoutInflater inflater;

    private int i = 0;

    public CategoriesAdapter(List<CategorDish> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        CategoriesRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.categories_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = holder.getLayoutPosition();
                notifyDataSetChanged();
            }
        });
        if (i == position) {
            ((MyHolder) holder).binding.card.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            ((MyHolder) holder).binding.tv.setTextColor(context.getResources().getColor(R.color.white));
            BanqueteBookActivity banqueteBookActivity=(BanqueteBookActivity) context;
            banqueteBookActivity.show(list.get(position));
        } else {
            ((MyHolder) holder).binding.card.setCardBackgroundColor(context.getResources().getColor(R.color.gray6));
            ((MyHolder) holder).binding.tv.setTextColor(context.getResources().getColor(R.color.black));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public CategoriesRowBinding binding;

        public MyHolder(@NonNull CategoriesRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
