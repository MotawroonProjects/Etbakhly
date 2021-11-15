package com.etbakhly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.etbakhly.R;
import com.etbakhly.databinding.SliderKitchenRowBinding;
import com.etbakhly.databinding.SliderRowBinding;
import com.etbakhly.models.KitchenModel;
import com.etbakhly.models.SliderModel;

import java.util.List;

public class SliderKitchenAdapter extends PagerAdapter {
    private List<KitchenModel.Photo> list;
    private Context context;
    private LayoutInflater inflater;

    public SliderKitchenAdapter(List<KitchenModel.Photo> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        SliderKitchenRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.slider_kitchen_row, container, false);
        binding.setModel(list.get(position));
        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
