package com.it.nhozip.food.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.it.nhozip.food.R;
import com.it.nhozip.food.fb.MyFaceBook;
import com.it.nhozip.food.firebase.MyData;
import com.it.nhozip.food.obj.You;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huyen on 9/21/2016.
 */
public class AcitvityRegister extends AppCompatActivity {
    private TextInputEditText email, pass, pass_again;
    private Button rgister;
    private  JSONObject response, profile_pic_data, profile_pic_url;
    private  You you = new You();
    String jsondata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_register);
        initView();
        SharedPreferences pre = getSharedPreferences
                (Login_Activity.AUTO_LOGIN, MODE_PRIVATE);
        int trangThai = pre.getInt("login", 0);
        Intent intent = getIntent();
        if (trangThai == 0) {
            jsondata = intent.getStringExtra("jsondata");
        } else {
            jsondata = pre.getString("data", "");

        }
        you=getnfor(jsondata,getApplicationContext());
//        rgister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MyData.createUser(you.getId(), you);
//                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
//                startActivity(intent);
//                Log.e("Dtaa", you.toString());
//                finish();
//                Log.e("Pass",pass.getText().toString());
//                Log.e("User",email.getText().toString());
//            }
//        });


    }
    public  You getnfor(String json_data, Context context) {
        FacebookSdk.sdkInitialize(context);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        try {
            response = new JSONObject(json_data);
            Log.e("name", response.get("name").toString());
            profile_pic_data = new JSONObject(response.get("picture").toString());
            profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
            you.setName(response.get("name").toString());
            you.setAvata(profile_pic_url.getString("url"));
            you.setId(accessToken.getUserId());
            you.setUser(email.getText().toString());
            you.setPass(pass.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("XXX", "ERRR");
        }
        return you;
    }

    private void initView() {
        email = (TextInputEditText) findViewById(R.id.edtEmail);
        pass = (TextInputEditText) findViewById(R.id.edtpass);
        pass_again = (TextInputEditText) findViewById(R.id.edtpass2);
        rgister = (Button) findViewById(R.id.btnrRgister);
    }

}
