package com.it.nhozip.food.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.it.nhozip.food.R;
import com.it.nhozip.food.obj.Food;
import com.it.nhozip.food.ui.TutorFoodActivity;

import java.util.ArrayList;

/**
 * Created by huyen on 9/13/2016.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.RecyclerViewHolder> {
    private ArrayList<Food> arrFoods;
    private Context _context;
    public static final String FOOD_OBJ = "food_obj";
    private int with;
    public FoodAdapter(ArrayList<Food> arrFoods, Context context) {
        this.arrFoods = arrFoods;
        this._context = context;

    }

    public FoodAdapter(ArrayList<Food> arrFoods, Context context,int with) {
        this.arrFoods = arrFoods;
        this._context = context;
        this.with=with;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView=null;
        if(with==1)  itemView= inflater.inflate(R.layout.item_food_home, parent, false);
        else itemView= inflater.inflate(R.layout.item_food_detail, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        final Food food = arrFoods.get(position);
        Glide.with(_context).load(food.getImg().toString())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgHinh);
//        Typeface tf = Typeface.createFromAsset(_context.getAssets(),
//                "fonts/VBOOKMB.TTF");

        holder.txtName.setText(food.getName());
       // holder.txtName.setTypeface(tf);
        Log.e("img", food.getImg().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, TutorFoodActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(FOOD_OBJ, food);
                _context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrFoods.size();
    }

    @Override
    public String toString() {
        return "FoodAdapter{" +
                "arrFoods=" + arrFoods +
                ", _context=" + _context +
                '}';
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private ImageView imgHinh;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            imgHinh = (ImageView) itemView.findViewById(R.id.img_food);
        }
    }
}
