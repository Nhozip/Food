package com.it.nhozip.food.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.it.nhozip.food.R;
import com.it.nhozip.food.adapter.FoodAdapter;
import com.it.nhozip.food.database.DatabaseManager;
import com.it.nhozip.food.firebase.MyData;
import com.it.nhozip.food.google.MyGoogle;
import com.it.nhozip.food.mSharedPreferences.MyPre;
import com.it.nhozip.food.obj.Food;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huyen on 9/17/2016.
 */
public class Fragment_Like extends Fragment {
    private RecyclerView rYFood;
    private DatabaseManager databaseManager;
    public FoodAdapter foodAdapter;
    private ArrayList<Food> foods;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_like, container, false);
        initView(view);
        foods = new ArrayList<>();
        databaseManager = new DatabaseManager(getContext());
        foods = databaseManager.getListNote();

        foodAdapter = new FoodAdapter(foods, getContext(), 0);
        rYFood.setAdapter(foodAdapter);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        databaseManager = new DatabaseManager(getContext());
        foodAdapter = new FoodAdapter(databaseManager.getListNote(), getContext(),0);
        rYFood.setAdapter(foodAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initView(View view) {
        rYFood = (RecyclerView) view.findViewById(R.id.rVFood);
        GridLayoutManager lLayout = new GridLayoutManager(getContext(), 2);
        //
        rYFood.setLayoutManager(lLayout);
    }

}
