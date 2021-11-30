package com.etbakhly.activity_verification_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.etbakhly.R;
import com.etbakhly.activities_fragments.activity_choose.ChooseActivity;
import com.etbakhly.activity_SignUp.SignUpActivity;
import com.etbakhly.databinding.ActivityVerificationCodeBinding;
import com.etbakhly.language.Language;
import com.etbakhly.models.UserModel;
import com.etbakhly.preferences.Preferences;
import com.etbakhly.remote.Api;
import com.etbakhly.share.Common;
import com.etbakhly.tags.Tags;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationCodeActivity extends AppCompatActivity {

    private ActivityVerificationCodeBinding binding;
    private String lang;
    private String phone_code;
    private String phone;
    private CountDownTimer timer;
    private FirebaseAuth mAuth;
    private String verificationId;
    private String smsCode;
    private Preferences preferences;
    private boolean canSend = false;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verification_code);
        getDataFromIntent();
        initView();

    }
    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            phone_code = intent.getStringExtra("phone_code");
            phone = intent.getStringExtra("phone");

        }
    }

    private void initView() {
        preferences = Preferences.getInstance();

        mAuth = FirebaseAuth.getInstance();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        String phone = phone_code + this.phone;
        binding.setPhone(phone);
        binding.tvResend.setOnClickListener(view -> {
            if (canSend) {
                sendSmsCode();
            }
        });
        binding.loginBtn.setOnClickListener(view -> {
            String code = binding.edtCode.getText().toString().trim();
//            Intent intent=getIntent();
//            setResult(RESULT_OK,intent);
//            finish();
//            login();
//            // navigateToSignUpActivity();
            if (!code.isEmpty()) {
                binding.edtCode.setError(null);
                Common.CloseKeyBoard(this, binding.edtCode);
                checkValidCode(code);
            } else {
                binding.edtCode.setError(getString(R.string.field_req));
                Toast.makeText(VerificationCodeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }

        });
        sendSmsCode();
    }

    private void sendSmsCode() {
        startTimer();

        mAuth.setLanguageCode(lang);
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                smsCode = phoneAuthCredential.getSmsCode();
                binding.edtCode.setText(smsCode);
                checkValidCode(smsCode);
            }

            @Override
            public void onCodeSent(@NonNull String verification_id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verification_id, forceResendingToken);
                VerificationCodeActivity.this.verificationId = verification_id;

            }


            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                if (e.getMessage() != null) {
                    Toast.makeText(VerificationCodeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    //Common.CreateDialogAlert(VerificationCodeActivity.this, e.getMessage());
                } else {
                    Toast.makeText(VerificationCodeActivity.this, "failed", Toast.LENGTH_SHORT).show();
                   // Common.CreateDialogAlert(VerificationCodeActivity.this, getString(R.string.failed));

                }
            }

            @Override
            public void onCodeAutoRetrievalTimeOut( String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Log.e("dldll",s);
            }
        };
        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(
                        phone_code + phone,
                        120,
                        TimeUnit.SECONDS,
                        this,
                        mCallBack

                );
    }

    private void startTimer() {
        canSend = false;
        binding.tvResend.setEnabled(false);
        timer = new CountDownTimer(120 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.ENGLISH);
                String time = format.format(new Date(l));
                binding.tvCounter.setText(time);
            }

            @Override
            public void onFinish() {
                canSend = true;
                binding.tvCounter.setText("00:00");
                binding.tvResend.setEnabled(true);
            }
        };
        timer.start();
    }


    private void checkValidCode(String code) {

        if (verificationId != null) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            mAuth.signInWithCredential(credential)
                    .addOnSuccessListener(authResult -> {
//                        Intent intent = getIntent();
//                        setResult(RESULT_OK, intent);
//                        finish();
                        login();
                    }).addOnFailureListener(e -> {
            });
        }

    }

    private void login() {

        Api.getService(Tags.base_url)
                .login(phone_code,phone,"1")
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()){
                            if (response.body() !=null && response.body().getStatus()==200){
                                if (response.body()!=null && response.body().getUser()!=null){
                                    Preferences preferences = Preferences.getInstance();
                                    preferences.create_update_userdata(VerificationCodeActivity.this,response.body());
                                    navigateUpToChooseActivity();
                                }
                            }else if (response.body() !=null && response.body().getStatus()==404){
                                navigateUpToSignUp();
                            }
                        }else {
                            switch (response.code()) {
                                case 500:
                                    //   Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    // Toast.makeText(LoginActivity.this,getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            try {
                                Log.e("error_code", response.code() + "_"+response.errorBody().string());
                            } catch (NullPointerException | IOException e) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    // Toast.makeText(LoginActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
                                } else {
                                    //  Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void navigateUpToChooseActivity() {
        Intent intent=new Intent(VerificationCodeActivity.this, ChooseActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateUpToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("phone_code", phone_code);
        startActivity(intent);
        finish();
    }
}