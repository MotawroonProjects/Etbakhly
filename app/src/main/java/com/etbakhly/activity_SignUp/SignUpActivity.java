package com.etbakhly.activity_SignUp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.activity_choose.ChooseActivity;
import com.etbakhly.databinding.ActivitySignUpBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.SignUpModel;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;
import com.etbakhly.remote.Api;
import com.etbakhly.share.Common;
import com.etbakhly.tags.Tags;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

import io.paperdb.Paper;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private String lang;
    private SignUpModel model;
    private ActivityResultLauncher<Intent> launcher;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private int selectedReq = 0;
    private Uri uri = null;
    private String phone_code,phone;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        getDataFromIntent();
        initView();
      //  signUpWithoutImage();
        //signUpWithImage();
    }

    private void initView() {
        Paper.init(this);
        lang=Paper.book().read("lang","ar");

        model=new SignUpModel();
        binding.setModel(model);

        launcher =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result -> {
            if (result.getResultCode()==RESULT_OK && result.getData() !=null){
                if (selectedReq ==READ_REQ){
                    binding.icon.setVisibility(View.GONE);
                    uri=result.getData().getData();
                    File file=new File(Common.getImagePath(this,uri));
                    Picasso.get().load(file).fit().into(binding.image);

                }else if (selectedReq==CAMERA_REQ){
                    Bitmap bitmap= (Bitmap) result.getData().getExtras().get("data");
                    binding.icon.setVisibility(View.GONE);
                    uri=getUriFromBitmap(bitmap);

                    if (uri!=null){
                        String path=Common.getImagePath(this,uri);
                        if (path!=null){
                            Picasso.get().load(new File(path)).fit().into(binding.image);
                        }else {
                            Picasso.get().load(uri).fit().into(binding.image);
                        }
                    }
                }
            }
        });
        binding.flImage.setOnClickListener(view -> openSheet());
        binding.flGallery.setOnClickListener(view -> {
            closeSheet();
            checkReadPermission();
        });
        binding.flCamera.setOnClickListener(view -> {
            closeSheet();
            checkCameraPermission();
        });
        binding.btnCancel.setOnClickListener(view -> {
            closeSheet();
        });

        binding.btnConfirm.setOnClickListener(view -> {
            if (model.isDataValid(this)){
                Common.CloseKeyBoard(this,binding.btnConfirm);
                if (uri == null){
                    signUpWithoutImage();
                }else{
                    signUpWithImage();
                }
            }
        });
    }


    private void openSheet() {
        binding.expandLayout.setExpanded(true, true);
    }
    private void closeSheet() {
        binding.expandLayout.collapse(true);
    }

    private void checkReadPermission() {
        closeSheet();
        if (ActivityCompat.checkSelfPermission(this, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_PERM}, READ_REQ);
        } else {
            SelectImage(READ_REQ);
        }
    }


    private void checkCameraPermission() {
        closeSheet();

        if (ContextCompat.checkSelfPermission(this, write_permission) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, camera_permission) == PackageManager.PERMISSION_GRANTED
        ) {
            SelectImage(CAMERA_REQ);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{camera_permission, write_permission}, CAMERA_REQ);
        }
    }

    private void SelectImage(int req) {
        selectedReq=req;
        Intent intent=new Intent();

        if (req ==READ_REQ){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            }else{
                intent.setAction(Intent.ACTION_GET_CONTENT);
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");

            launcher.launch(intent);
        }
        else if (req==CAMERA_REQ){
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher.launch(intent);
            } catch (SecurityException e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_REQ) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == CAMERA_REQ) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Uri getUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "", ""));
    }

    private void getDataFromIntent(){
        Intent intent=getIntent();
        if (intent!=null){
            phone=intent.getStringExtra("phone");
            phone_code=intent.getStringExtra("phone_code");
        }
    }

    private void signUpWithoutImage(){

        Api.getService(Tags.base_url)
                .signUpWithoutImage(model.getName(),phone,phone_code,model.getEmail(),"0","0")
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()){
                            if (response.body()!=null && response.body().getStatus()==200){
                                if (response.body()!=null && response.body().getUser()!=null){
                                    Preferences preferences=Preferences.getInstance();
                                    preferences.create_update_userdata(SignUpActivity.this,response.body());
                                    navigateUpToChooseActivity();
                                }
                            }else if (response.body() != null && response.body().getStatus() == 404){
                                Toast.makeText(SignUpActivity.this, R.string.user_found, Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            switch (response.code()) {
                                case 500:
                                    //  Toast.makeText(SignUpActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    //      Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            try {
                                Log.e("error_code", response.code() + "_");
                            } catch (NullPointerException e) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {

                        try {
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
                                } else {
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                });
    }


    private void signUpWithImage(){
        RequestBody part_name = Common.getRequestBodyText(model.getName());
        RequestBody part_email = Common.getRequestBodyText(model.getEmail());
        RequestBody phone_part = Common.getRequestBodyText(phone);
        RequestBody phone_code_part = Common.getRequestBodyText(phone_code);
        RequestBody longitude_part = Common.getRequestBodyText("0");
        RequestBody latitude_part = Common.getRequestBodyText("0");

        MultipartBody.Part image = Common.getMultiPart(this, uri, "photo");

        Api.getService(Tags.base_url)
                .signUpWithImage(part_name,phone_part,phone_code_part,part_email,longitude_part,latitude_part,image)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()){
                            if (response.body()!=null && response.body().getStatus()==200){
                                if (response.body()!=null &&response.body().getUser()!=null){
                                    Preferences preferences=Preferences.getInstance();
                                    preferences.create_update_userdata(SignUpActivity.this,response.body());
                                    navigateUpToChooseActivity();
                                }
                            }else if (response.body()!=null && response.body().getStatus()==404){
                                Toast.makeText(SignUpActivity.this, R.string.user_found, Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            switch (response.code()) {
                                case 500:
                                    break;
                                default:
                                    break;
                            }
                            try {
                                Log.e("error_code", response.code() + "_");
                            } catch (NullPointerException e) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                } else {
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

    private void navigateUpToChooseActivity() {
        Intent intent=new Intent(SignUpActivity.this, ChooseActivity.class);
        startActivity(intent);
        finish();
    }
}