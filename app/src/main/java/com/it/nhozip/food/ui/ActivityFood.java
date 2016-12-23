package com.it.nhozip.food.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.it.nhozip.food.R;
import com.it.nhozip.food.firebase.MyData;

/**
 * Created by huyen on 11/1/2016.
 */
public class ActivityFood extends AppCompatActivity {
    private RecyclerView rYFood;
    private Toolbar toolbar;
    private  int key=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_load_more_food);
        initView();
        Bundle bundle=getIntent().getBundleExtra(FragmnetHome.KEY_PK);
        key=bundle.getInt(FragmnetHome.KEY_FOOD);

        if(key==1){
            setTitle("Food New");
            MyData.getDataaRecyView(MyData.FOOD_NEW, rYFood,this,0);
        }
        if(key==2){
            setTitle("Miền Bắc");
            MyData.getDataaRecyView(MyData.M_BAC, rYFood,this,0);
        }
        if(key==3){
            setTitle("Miền Trung");
            MyData.getDataaRecyView(MyData.M_TRUNG, rYFood,this,0);
        }
        if(key==4){
            setTitle("Miền Nam");
            MyData.getDataaRecyView(MyData.M_NAM, rYFood,this,0);
        }
        if(key==5){
            setTitle("Sinh viên");
            MyData.getDataaRecyView(MyData.STUDENT, rYFood,this,0);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rYFood= (RecyclerView) findViewById(R.id.rVFood);
        GridLayoutManager lLayout =new GridLayoutManager(getApplicationContext(),2);
        //
        rYFood.setLayoutManager(lLayout);
    }
}
