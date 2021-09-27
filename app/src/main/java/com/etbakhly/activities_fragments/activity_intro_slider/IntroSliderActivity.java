package com.etbakhly.activities_fragments.activity_intro_slider;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.activity_choose.ChooseActivity;
import com.etbakhly.activities_fragments.activity_login.LoginActivity;
import com.etbakhly.adapters.IntroAdapter;
import com.etbakhly.databinding.ActivityIntroSliderBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.DefaultSettings;
import com.etbakhly.preferences.Preferences;

import io.paperdb.Paper;

public class IntroSliderActivity extends AppCompatActivity {
    private ActivityIntroSliderBinding binding;
    private IntroAdapter adapter;
    private Preferences preferences;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = new Fade();
            transition.setInterpolator(new LinearInterpolator());
            transition.setDuration(1000);
            getWindow().setEnterTransition(transition);
            getWindow().setExitTransition(transition);

        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro_slider);
        initView();
    }


    private void initView() {

        preferences = Preferences.getInstance();
      //  binding.tab.setupWithViewPager(binding.pager);
        adapter = new IntroAdapter(this);
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(3);
//        for (int i = 0; i < binding.tab.getTabCount(); i++) {
//            View tab = ((ViewGroup) binding.tab.getChildAt(0)).getChildAt(i);
//            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
//            p.setMargins(10, 0, 10, 0);
//            tab.requestLayout();
//            Log.e("ldldl", binding.tab.getTabCount() + "");
//        }
//            binding.tvTitle.setText(Html.fromHtml(getString(R.string.welcome)));
//            binding.tvContent.setText(getString(R.string.the_best_center));


        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset != 0) {
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0, positionOffset);
                    //  binding.llContent.startAnimation(alphaAnimation);
                }
            }

            @Override
            public void onPageSelected(int position) {


                switch (position) {
                    case 0:
                        binding.btnNext.setVisibility(View.GONE);

                        binding.btnSkip.setVisibility(View.VISIBLE);
                    case 1:
                        //  binding.cons.setVisibility(View.VISIBLE);
                        binding.btnNext.setText(getResources().getString(R.string.next));
                        binding.btnNext.setVisibility(View.GONE);

                        binding.btnSkip.setVisibility(View.VISIBLE);
//                            binding.tvTitle.setText(Html.fromHtml(getString(R.string.welcome)));
//                            binding.tvContent.setText(getString(R.string.the_best_center));


                        break;

                    //  binding.cons.setVisibility(View.VISIBLE);

//                            binding.tvTitle.setText(Html.fromHtml(getString(R.string.who_are)));
//                            binding.tvContent.setText(getString(R.string.spicial_center));


                    case 2:
                      //  binding.cons.setVisibility(View.VISIBLE);

//                            binding.tvTitle.setText(Html.fromHtml(getString(R.string.doctors)));
//                            binding.tvContent.setText(getString(R.string.vision_world));
                        binding.btnNext.setText(getResources().getString(R.string.start));
                        binding.btnSkip.setVisibility(View.GONE);
                        binding.btnNext.setVisibility(View.VISIBLE);


                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        binding.btnSkip.setOnClickListener(v -> {
            start();


        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.pager.getCurrentItem()==2){
                    start();
                }
                else {
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem()+1);
                }
            }
        });


    }

    private void start() {
        DefaultSettings defaultSettings = preferences.getAppSetting(this);
        if (defaultSettings != null) {
           // Log.e("lsdkdk","dkkdkkd");
            defaultSettings.setShowIntroSlider(false);
            preferences.createUpdateAppSetting(this, defaultSettings);
        }else {
            defaultSettings=new DefaultSettings();
            defaultSettings.setShowIntroSlider(false);
            preferences.createUpdateAppSetting(this, defaultSettings);
        }

        Intent intent = new Intent(this, ChooseActivity.class);

        startActivity(intent);
        finish();


    }
}