package com.etbakhly.activities_fragments.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.etbakhly.R;

import com.etbakhly.activities_fragments.activity_choose.ChooseActivity;
import com.etbakhly.activities_fragments.activity_home_banquet.HomeActivity;
import com.etbakhly.activities_fragments.activity_intro_slider.IntroSliderActivity;
import com.etbakhly.activities_fragments.activity_login.LoginActivity;
import com.etbakhly.databinding.ActivitySplashBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private Preferences preferences;
    private UserModel userModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        new Handler()
                .postDelayed(() -> {
                    if (preferences.getAppSetting(this) == null || preferences.getAppSetting(SplashActivity.this).isShowIntroSlider()) {
                        Intent intent = new Intent(this, IntroSliderActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent;

                        if (userModel == null) {
                            intent = new Intent(this, ChooseActivity.class);
                        } else {
                            intent = new Intent(this, HomeActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }
                }, 2000);


    }
}
