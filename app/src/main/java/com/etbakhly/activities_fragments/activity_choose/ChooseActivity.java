package com.etbakhly.activities_fragments.activity_choose;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.etbakhly.R;

import com.etbakhly.activities_fragments.banquete.activity_home_banquete.HomeBanqueteActivity;
import com.etbakhly.activities_fragments.independent.activity_home_independent.HomeActivity;
import com.etbakhly.activities_fragments.independent.activity_map.MapActivity;
import com.etbakhly.databinding.ActivityChooseBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.SelectedLocation;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;

import io.paperdb.Paper;

public class ChooseActivity extends AppCompatActivity {

    private ActivityChooseBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private ActivityResultLauncher<Intent> launcher;
    private SelectedLocation selectedLocation;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {


                selectedLocation = (SelectedLocation) result.getData().getSerializableExtra("location");

                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);

            }
        });
        binding.flBanquet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, HomeBanqueteActivity.class);
                startActivity(intent);
            }
        });
        binding.flfoodcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, HomeBanqueteActivity.class);
                startActivity(intent);
            }
        });
        binding.flIndependent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, MapActivity.class);
                launcher.launch(intent);
            }
        });

    }
}
