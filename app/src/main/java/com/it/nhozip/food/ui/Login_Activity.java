package com.it.nhozip.food.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.it.nhozip.food.MainActivity;
import com.it.nhozip.food.R;
import com.it.nhozip.food.fb.MyFaceBook;
import com.it.nhozip.food.google.MyGoogle;
import com.it.nhozip.food.mSharedPreferences.MyPre;

import java.security.MessageDigest;

/**
 * Created by huyen on 8/12/2016.
 */

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {
    public CallbackManager callbackManager;
    public Button login_fb;
    public Button login_google;
    private ProgressBar loading;
    private TextView noti;
    public static final String AUTO_LOGIN = "autoLogin";
    public String jsondata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();

//        try {
//            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.it.nhozip.food", PackageManager.GET_SIGNATURES);
//            for (Signature signature : packageInfo.signatures) {
//                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
//                messageDigest.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT));
//            }
//        } catch (Exception e) {
//        }


        if (MyPre.loginFirst(this)) {
            jsondata = MyPre.getDataOFFFB(this);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            setContentView(R.layout.activity_login);
            initView();
            MyGoogle.config(this,loading,noti);
            login_fb.setOnClickListener(this);
            login_google.setOnClickListener(this);
        }


    }

    private void initView() {

        login_fb = (Button) findViewById(R.id.login_fb);
        login_google = (Button) findViewById(R.id.login_google);


        loading= (ProgressBar) findViewById(R.id.loading);
        loading.getIndeterminateDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        noti= (TextView) findViewById(R.id.noti);

//        Typeface tf = Typeface.createFromAsset(getAssets(),
//                "fonts/VCHANCER.TTF");
//        TextView tv = (TextView) findViewById(R.id.name);
//        tv.setTypeface(tf);
//        //
//
//        Typeface tfWellcome = Typeface.createFromAsset(getAssets(),
//                "fonts/VFFORMN.TTF");
//        TextView tvWellcome = (TextView) findViewById(R.id.wellcome);
//        tvWellcome.setTypeface(tfWellcome);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MyGoogle.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                MyGoogle.firebaseAuthWithGoogle(account, this);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
        Log.e("data", data.toString());
    }


    @Override
    public void onStart() {
        super.onStart();
        //MyGoogle.config(this);
         MyGoogle.mAuth.addAuthStateListener(MyGoogle.mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();

            if (MyGoogle.mAuthListener != null) {
                MyGoogle.mAuth.removeAuthStateListener(MyGoogle.mAuthListener);
            }


    }

    protected void facebookSDKInitialize() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.login_fb:
               // lnLogin.setVisibility(View.GONE);
                MyPre.setLoginWith(this, 1);
                MyFaceBook.login(callbackManager, Login_Activity.this, getApplicationContext(),loading,noti);

                break;
            case R.id.login_google:
                //lnLogin.setVisibility(View.GONE);
                MyPre.setLoginWith(this, 2);
                MyGoogle.login(Login_Activity.this);
                break;
        }
        Log.e("LoginWith", MyPre.getLoginWith(this) + "");


    }
}
