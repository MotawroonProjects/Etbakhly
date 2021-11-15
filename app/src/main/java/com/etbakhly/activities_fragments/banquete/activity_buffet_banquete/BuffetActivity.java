package com.etbakhly.activities_fragments.banquete.activity_buffet_banquete;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.banquete.activity_buffet_detials.BuffetDetialsActivity;
import com.etbakhly.adapters.BuffetBanqueteAdapter;
import com.etbakhly.databinding.ActivityBuffetBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.BuffetDataModel;
import com.etbakhly.models.BuffetModel;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;
import com.etbakhly.remote.Api;
import com.etbakhly.tags.Tags;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuffetActivity extends AppCompatActivity {

    private ActivityBuffetBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private List<BuffetModel> list;
    BuffetBanqueteAdapter buffetBanqueteAdapter;
    private String lang;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buffet);

        initView();
        getBuffetList();
    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);


        list = new ArrayList<>();
        buffetBanqueteAdapter=new BuffetBanqueteAdapter(list,this,this);

        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(buffetBanqueteAdapter);
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBuffetList();
            }
        });

    }
    private void getBuffetList(){
        list.clear();
        buffetBanqueteAdapter.notifyDataSetChanged();
        binding.progBar.setVisibility(View.VISIBLE);
        binding.recView.setVisibility(View.VISIBLE);
        binding.tvNoData.setVisibility(View.GONE);

        Api.getService(Tags.base_url)
                .getBuffetList()
                .enqueue(new Callback<BuffetDataModel>() {
                    @Override
                    public void onResponse(Call<BuffetDataModel> call, Response<BuffetDataModel> response) {
                        binding.progBar.setVisibility(View.GONE);
                        binding.swipeRefresh.setRefreshing(false);
                        if (response.isSuccessful()){
                            if (response.body()!=null && response.body().getStatus()==200){
                                if (response.body().getData()!=null){
                                    if (response.body().getData().size()>0){
                                        list.addAll(response.body().getData());
                                        buffetBanqueteAdapter.notifyDataSetChanged();
                                    }else {
                                        binding.recView.setVisibility(View.GONE);
                                        binding.tvNoData.setVisibility(View.VISIBLE);
                                    }
                                }
                            }else {
                                binding.recView.setVisibility(View.GONE);
                                binding.tvNoData.setVisibility(View.VISIBLE);
                            }
                        }else {
                            binding.recView.setVisibility(View.GONE);
                            switch (response.code()){
                                case 500:
                                    break;
                                default:
                                    break;
                            }try {
                                Log.e("error_code", response.code() + "_");
                            } catch (NullPointerException e) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BuffetDataModel> call, Throwable t) {
                        try {

                            binding.progBar.setVisibility(View.GONE);
                            binding.recView.setVisibility(View.GONE);

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //     Toast.makeText(SignUpActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
                                } else {
                                    //  Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }catch (Exception e){

                        }
                    }
                });
    }

    public void showBuffet(BuffetModel buffetModel) {
        Intent intent = new Intent(this, BuffetDetialsActivity.class);
        intent.putExtra("data", buffetModel);
        startActivity(intent);
    }


}
