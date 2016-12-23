package com.it.nhozip.food.fb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.it.nhozip.food.MainActivity;
import com.it.nhozip.food.mSharedPreferences.MyPre;
import com.it.nhozip.food.obj.You;
import com.it.nhozip.food.widgets.Loading;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by huyen on 9/14/2016.
 */
public class MyFaceBook {
    private static JSONObject response, profile_pic_data, profile_pic_url;
    private static You you = new You();


    public static You getnfor(String json_data, Context context) {
       // FacebookSdk.sdkInitialize(context);
        try {

            response = new JSONObject(json_data);
            Log.e("name", response.get("name").toString());
            profile_pic_data = new JSONObject(response.get("picture").toString());
            profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
            you.setName(response.get("name").toString());
            you.setAvata(profile_pic_url.getString("url"));
            you.setId(response.get("id").toString());

        } catch (JSONException e) {
            e.printStackTrace();
            MyPre.setdataOffFB(context, json_data.toString());
            Log.e("XXX", "ERRR");
        }
        return you;
    }

    public static void login(CallbackManager callbackManager, final Activity activity, final Context context, final ProgressBar loading, final TextView noti) {


        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                       // getUserInfo(loginResult, context, activity);

                        loading.setVisibility(View.VISIBLE);
                        noti.setVisibility(View.VISIBLE);
                        GraphRequest data_request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject json_object,
                                            GraphResponse response) {
                                        Log.e("data", json_object.toString());
                                        Intent intent = new Intent(context, MainActivity.class);
                                        MyPre.setdataOffFB(context, json_object.toString());
                                        // Intent intent = new Intent(context, AcitvityRegister.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("jsondata", json_object.toString());
                                        context.startActivity(intent);
                                        activity.finish();

                                    }
                                });
                        Bundle permission_param = new Bundle();
                        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
                        data_request.setParameters(permission_param);
                        data_request.executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(context, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {

                        Toast.makeText(context, "Kết nối mạng để đăng nhập", Toast.LENGTH_LONG).show();
                        Log.e("ERR",exception.toString());
                    }
                });
    }

    public static void getUserInfo(final LoginResult login_result, final Context context, final Activity activity) {

        GraphRequest data_request = GraphRequest.newMeRequest(
                login_result.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {
                        Log.e("data", json_object.toString());
                        Intent intent = new Intent(context, MainActivity.class);
                        MyPre.setdataOffFB(context, json_object.toString());
                        // Intent intent = new Intent(context, AcitvityRegister.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("jsondata", json_object.toString());
                        context.startActivity(intent);
                        activity.finish();

                    }
                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();

    }
}
