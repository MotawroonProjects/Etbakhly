package com.etbakhly.activities_fragments.activity_home_banquet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.etbakhly.R;

import com.etbakhly.activities_fragments.activity_home_banquet.fragments.FragmentCart;
import com.etbakhly.activities_fragments.activity_home_banquet.fragments.FragmentMyOrders;
import com.etbakhly.activities_fragments.activity_home_banquet.fragments.FragmentOffers;
import com.etbakhly.activities_fragments.activity_home_banquet.fragments.Fragment_Home;
import com.etbakhly.activities_fragments.activity_home_banquet.fragments.Fragment_Profile;
import com.etbakhly.activities_fragments.activity_login.LoginActivity;
import com.etbakhly.databinding.ActivityHomeBanquetBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;
import com.etbakhly.remote.Api;
import com.etbakhly.share.Common;
import com.etbakhly.tags.Tags;
import com.google.firebase.iid.FirebaseInstanceId;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBanquetBinding binding;
    private Preferences preferences;
    private FragmentManager fragmentManager;
    private Fragment_Home fragment_home;
    private FragmentCart fragmentCart;

    private Fragment_Profile fragment_profile;
    private FragmentOffers fragmentOffers;
    private FragmentMyOrders fragmentMyOrders;
    private UserModel userModel;
    private String lang;
    private boolean backPressed = false;
    private String copun = "";


    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_banquet);
        initView();


    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);


        displayFragmentMain();

        if (userModel != null) {
            //   updateFirebaseToken();
        }

        setUpBottomNavigation();

    }


    public void displayFragmentMain() {
        try {
            if (fragment_home == null) {
                fragment_home = Fragment_Home.newInstance();
            }


            if (fragmentOffers != null && fragmentOffers.isAdded()) {
                fragmentManager.beginTransaction().hide(fragmentOffers).commit();
            }
            if (fragment_profile != null && fragment_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_profile).commit();
            }
            if (fragmentCart != null && fragmentCart.isAdded()) {
                fragmentManager.beginTransaction().hide(fragmentCart).commit();
            }
            if (fragmentMyOrders != null && fragmentMyOrders.isAdded()) {
                fragmentManager.beginTransaction().hide(fragmentMyOrders).commit();
            }
            if (fragment_home.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_home).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_home, "fragment_home").commit();

            }
            updateBottomNavigationPosition(0);
            binding.setTitle(getString(R.string.home));

        } catch (Exception e) {
        }

    }


    public void displayFragmentOffers() {
        try {


            if (fragmentOffers == null) {
                fragmentOffers = FragmentOffers.newInstance();
            }


            if (fragment_home != null && fragment_home.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_home).commit();
            }
            if (fragmentCart != null && fragmentCart.isAdded()) {
                fragmentManager.beginTransaction().hide(fragmentCart).commit();
            }
            if (fragmentMyOrders != null && fragmentMyOrders.isAdded()) {
                fragmentManager.beginTransaction().hide(fragmentMyOrders).commit();
            }

            if (fragment_profile != null && fragment_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_profile).commit();
            }


            if (fragmentOffers.isAdded()) {
                fragmentManager.beginTransaction().show(fragmentOffers).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragmentOffers, "fragment_offer").commit();

            }
            updateBottomNavigationPosition(1);
            binding.setTitle(getString(R.string.offers));
        } catch (Exception e) {
        }
    }

    public void displayFragmentCart() {
        try {
            if (fragmentCart == null) {
                fragmentCart = FragmentCart.newInstance();
            }


            if (fragment_home != null && fragment_home.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_home).commit();
            }
            if (fragmentOffers != null && fragmentOffers.isAdded()) {
                fragmentManager.beginTransaction().hide(fragmentOffers).commit();
            }
            if (fragmentMyOrders != null && fragmentMyOrders.isAdded()) {
                fragmentManager.beginTransaction().hide(fragmentMyOrders).commit();
            }
            if (fragment_profile != null && fragment_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_profile).commit();
            }


            if (fragmentCart.isAdded()) {
                fragmentManager.beginTransaction().show(fragmentCart).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragmentCart, "fragmentCart").addToBackStack("fragmentCart").commit();

            }
            updateBottomNavigationPosition(2);

            binding.setTitle(getString(R.string.cart));
        } catch (Exception e) {
        }
    }

    public void displayFragmentMOrders() {
        try {
            if (fragmentMyOrders == null) {
                fragmentMyOrders = FragmentMyOrders.newInstance();
            }


            if (fragment_home != null && fragment_home.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_home).commit();
            }
            if (fragmentOffers != null && fragmentOffers.isAdded()) {
                fragmentManager.beginTransaction().hide(fragmentOffers).commit();
            }
            if (fragmentCart != null && fragmentCart.isAdded()) {
                fragmentManager.beginTransaction().hide(fragmentCart).commit();
            }
            if (fragment_profile != null && fragment_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_profile).commit();
            }


            if (fragmentMyOrders.isAdded()) {
                fragmentManager.beginTransaction().show(fragmentMyOrders).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragmentMyOrders, "fragmentMyOrders").addToBackStack("fragmentMyOrders").commit();

            }
            updateBottomNavigationPosition(3);

            binding.setTitle(getString(R.string.orders));
        } catch (Exception e) {
        }
    }

    public void displayFragmentProfile() {
        try {

            if (fragment_profile == null) {
                fragment_profile = Fragment_Profile.newInstance();
            }


            if (fragment_home != null && fragment_home.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_home).commit();
            }
            if (fragmentCart != null && fragmentCart.isAdded()) {
                fragmentManager.beginTransaction().hide(fragmentCart).commit();
            }
            if (fragmentOffers != null && fragmentOffers.isAdded()) {
                fragmentManager.beginTransaction().hide(fragmentOffers).commit();
            }

            if (fragmentMyOrders != null && fragmentMyOrders.isAdded()) {
                fragmentManager.beginTransaction().hide(fragmentMyOrders).commit();
            }


            if (fragment_profile.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_profile).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_profile, "fragment_profile").commit();

            }
            updateBottomNavigationPosition(4);

            binding.setTitle(getString(R.string.profile));
        } catch (Exception e) {
        }
    }


    public void refreshActivity(String lang) {
        Paper.book().write("lang", lang);
        Language.setNewLocale(this, lang);
        new Handler()
                .postDelayed(() -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 500);


    }

    private void getNotificationCount() {

    }

    private void setUpBottomNavigation() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("", R.drawable.ic_home);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("", R.drawable.ic_offer);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("", R.drawable.ic_delivery_truck);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("", R.drawable.ic_calendar);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("", R.drawable.ic_user);

        binding.ahBottomNav.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        binding.ahBottomNav.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.white));
        binding.ahBottomNav.setTitleTextSizeInSp(14, 12);
        binding.ahBottomNav.setForceTint(true);
        binding.ahBottomNav.setAccentColor(ContextCompat.getColor(this, R.color.colorPrimary));
        binding.ahBottomNav.setInactiveColor(ContextCompat.getColor(this, R.color.icon));

        binding.ahBottomNav.addItem(item1);
        binding.ahBottomNav.addItem(item2);
        binding.ahBottomNav.addItem(item3);
        binding.ahBottomNav.addItem(item4);
        binding.ahBottomNav.addItem(item5);

        updateBottomNavigationPosition(0);

        binding.ahBottomNav.setOnTabSelectedListener((position, wasSelected) -> {

            switch (position) {
                case 0:
                    displayFragmentMain();
                    break;
                case 1:

                    displayFragmentOffers();


                    break;
                case 2:
                    displayFragmentCart();
                    break;
                case 3:
                    displayFragmentMOrders();
                    break;
                case 4:
                    displayFragmentProfile();
                    break;
            }
            return false;
        });


    }

    private void updateBottomNavigationPosition(int pos) {

        binding.ahBottomNav.setCurrentItem(pos, false);
    }

    @Override
    public void onBackPressed() {
        backPressed = true;
        binding.ahBottomNav.setCurrentItem(0);
        backPressed = false;

        if (fragment_home != null && fragment_home.isAdded() && fragment_home.isVisible()) {
            if (userModel != null) {
                finish();
            } else {
                navigateToSignInActivity();
            }
        } else {
            displayFragmentMain();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public void logout() {

        navigateToSignInActivity();
//        if (userModel==null){
//            finish();
//            return;
//        }
//        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//        Api.getService(Tags.base_url)
//                .logout("Bearer " + userModel.getData().getToken())
//                .enqueue(new Callback<StatusResponse>() {
//                    @Override
//                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
//                        dialog.dismiss();
//                        if (response.isSuccessful()) {
//                            if (response.body() != null && response.body().getStatus() == 200) {
//                                navigateToSignInActivity();
//                            }
//
//                        } else {
//                            dialog.dismiss();
//                            try {
//                                Log.e("error", response.code() + "__" + response.errorBody().string());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                            if (response.code() == 500) {
//                            } else {
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<StatusResponse> call, Throwable t) {
//                        try {
//                            dialog.dismiss();
//                            if (t.getMessage() != null) {
//                                Log.e("error", t.getMessage() + "__");
//
//                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
//                                } else {
//                                }
//                            }
//                        } catch (Exception e) {
//                            Log.e("Error", e.getMessage() + "__");
//                        }
//                    }
//                });

    }


    public void navigateToSignInActivity() {
        preferences.clear(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
