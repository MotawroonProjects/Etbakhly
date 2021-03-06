package com.etbakhly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.etbakhly.R;

import java.util.ArrayList;
import java.util.List;


public class IntroAdapter extends PagerAdapter {
    private List<Integer> imagetopList;
    private List<String> title;
    private List<String> content;
    private Context context;
    private LayoutInflater inflater;

    public IntroAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        imagetopList = new ArrayList<>();
        title = new ArrayList<>();
        content = new ArrayList<>();
        imagetopList.add(R.drawable.backintro1);
        imagetopList.add(R.drawable.backintro2);
       imagetopList.add(R.drawable.backintro3);

        title.add(context.getResources().getString(R.string.select_order));
        title.add(context.getResources().getString(R.string.choose_food));
       title.add(context.getResources().getString(R.string.select_address));
        content.add(context.getResources().getString(R.string.select_order_content));
        content.add(context.getResources().getString(R.string.choose_kitchen));
        content.add(context.getResources().getString(R.string.choose_right_kitchen));

    }

    @Override
    public int getCount() {
        return imagetopList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object.equals(view);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.intro_row, container, false);
        ImageView imagetopView = view.findViewById(R.id.topimage);
        TextView tvtitle = view.findViewById(R.id.tvTitle);
        TextView tvcontent = view.findViewById(R.id.tvContent);

        imagetopView.setImageResource(imagetopList.get(position));

         tvtitle.setText(title.get(position));
        tvcontent.setText(content.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
