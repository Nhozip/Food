package com.it.nhozip.food.google;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.TokenData;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.it.nhozip.food.MainActivity;
import com.it.nhozip.food.R;
import com.it.nhozip.food.mSharedPreferences.MyPre;
import com.it.nhozip.food.obj.You;
import com.it.nhozip.food.ui.Login_Activity;

/**
 * Created by huyen on 9/20/2016.
 */
public class MyGoogle {
    public static final String TAG = "MyGoogle";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IMG = "img";

    public static final int RC_SIGN_IN = 9001;
    public static FirebaseAuth mAuth;
    public static FirebaseAuth.AuthStateListener mAuthListener;
    public static GoogleApiClient mGoogleApiClient;
    private static You infor;

    public static void config(final Login_Activity activity, final ProgressBar loading, final TextView noti) {

        //config
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        try {
            mGoogleApiClient = new GoogleApiClient.Builder(activity.getApplicationContext())
                    .enableAutoManage(activity, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Log.d(TAG, "onConnectionFailed::" + connectionResult);
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            mAuth = FirebaseAuth.getInstance();

            mAuthListener = new FirebaseAuth.AuthStateListener() {

                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {

                        loading.setVisibility(View.VISIBLE);
                        noti.setVisibility(View.VISIBLE);

                        // User is signed in
                        You you = new You();
                        String name = user.getDisplayName();
                        String email = user.getEmail();
                        String photoUrl = user.getPhotoUrl().toString();
                        you.setName(name);
                        you.setAvata(photoUrl);
                        you.setId(user.getUid());


                        Log.e("MyGooogle", "" + user.getUid());
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + name);
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + email);
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + photoUrl);
                        // Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                        MyPre.setDataOFFGoogle(activity.getApplicationContext(), user.getUid(), name, photoUrl);
                        activity.startActivity(intent);
                        infor = you;
                        activity.finish();

                    } else {
                        // User is signed out

                        loading.setVisibility(View.GONE);
                        noti.setVisibility(View.GONE);
                        Log.e(TAG, " NULL onAuthStateChanged:signed_out");
                    }
                }
            };
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }



    }

    public static You getInfor() {
        return infor;

    }

    public static void login(final FragmentActivity activity) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);

        //


    }

    public static void signOut(Context context) {
        try{
            FirebaseAuth.getInstance().signOut();
        }
        catch (Exception e){
            Toast.makeText(context,"No netWork!",Toast.LENGTH_LONG).show();
        }


    }

    public static void firebaseAuthWithGoogle(GoogleSignInAccount acct, final Activity activity) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());


                        }
                    }
                });
    }


}
