package com.it.nhozip.food.firebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.it.nhozip.food.adapter.FoodAdapter;
import com.it.nhozip.food.database.DatabaseManager;
import com.it.nhozip.food.obj.Food;

import java.util.ArrayList;

/**
 * Created by huyen on 9/16/2016.
 */
public class MyData {
    private static DatabaseReference mDatabase;
    public static final String FOOD_NEW = "FoodNew";
    public static final String M_BAC = "MBac";
    public static final String M_TRUNG = "MTrung";
    public static final String M_NAM = "MNam";
    public static final String STUDENT = "Student";
    public static final String User = "User";
    public static final String HOT = "Hot";
    private static ArrayList<Food> foods;
    private static FoodAdapter foodAdapter;
    private static Firebase firebase;
    private static final String URL_USER = "https://myfoo-d.firebaseio.com/User";


    public static ArrayList<Food> getDataa(String key) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(key);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foods = new ArrayList<Food>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Food food;
                    food = snapshot.getValue(Food.class);
                    foods.add(food);
                }
                Log.e("dtaa", foods.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return foods;
    }
    public static ArrayList<Food> getDataa2(String key) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(User).child(key);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foods = new ArrayList<Food>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Food food;
                    food = snapshot.getValue(Food.class);
                    foods.add(food);
                }
                Log.e("dtaa", foods.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return foods;
    }
    public static ArrayList<String> getListKey(){
        final ArrayList<String> keys=new ArrayList<>();
        firebase=new Firebase(URL_USER);
        firebase.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                for (com.firebase.client.DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    keys.add(snapshot.getKey());
                    Log.e("XXXX",keys.toString());
                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return keys;

    }

    public static void getDataaRecyView(String key, final RecyclerView recyclerView, final Context context, final int with) {

        mDatabase = FirebaseDatabase.getInstance().getReference().child(key);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foods = new ArrayList<Food>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Food food;
                    food = snapshot.getValue(Food.class);
                    foods.add(food);
                }
                Log.e("dtaa", foods.toString());
                foodAdapter = new FoodAdapter(foods, context,with);
                recyclerView.setAdapter(foodAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void createUser(final String id, final ArrayList<Food> list) {
        firebase = new Firebase(URL_USER);
      //  DatabaseManager databaseManager = new DatabaseManager(context);
        //final ArrayList<Food> list = databaseManager.getListNote();
        Log.e("Data", list.toString());
        firebase.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                    firebase.child(id).setValue(list);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(User);
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                mDatabase.child(id).setValue(o);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e("MyData", "id da ton tại");
//
//            }
//        });
    }
}
