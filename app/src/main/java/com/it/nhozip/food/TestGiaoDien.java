package com.it.nhozip.food;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by huyen on 10/22/2016.
 */
public class TestGiaoDien extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/VCHANCER.TTF");
        TextView tv = (TextView) findViewById(R.id.name);
        tv.setTypeface(tf);


        //

        Typeface tfWellcome = Typeface.createFromAsset(getAssets(),
                "fonts/VFFORMN.TTF");
        TextView tvWellcome = (TextView) findViewById(R.id.wellcome);
        tvWellcome.setTypeface(tfWellcome);
    }
}
