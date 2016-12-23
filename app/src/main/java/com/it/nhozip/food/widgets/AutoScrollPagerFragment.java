package com.it.nhozip.food.widgets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.it.nhozip.food.R;
import com.it.nhozip.food.adapter.FoodAdapter;
import com.it.nhozip.food.firebase.MyData;
import com.it.nhozip.food.obj.Food;
import com.it.nhozip.food.ui.TutorFoodActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

/**
 * Created by huyen on 11/2/2016.
 */
public class AutoScrollPagerFragment extends Fragment {
    private static DatabaseReference mDatabasee;

    private ArrayList<Food> foods;

    private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
            .cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_page, container, false);

        final AutoScrollViewPager pager = (AutoScrollViewPager)view.findViewById(R.id.scroll_pager);
        final TextView title = (TextView) view.findViewById(R.id.title);
        final CirclePageIndicator indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);


        mDatabasee = FirebaseDatabase.getInstance().getReference().child(MyData.HOT);
        mDatabasee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foods = new ArrayList<Food>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Food food;
                    food = snapshot.getValue(Food.class);
                    foods.add(food);
                }
                Log.e("AutoScrollPagerFragment", foods.toString());

                indicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

                    @Override
                    public void onPageSelected(int i) {
                        title.setText(foods.get(i).getName());
                    }
                });

                pager.setAdapter(new PagerAdapter() {
                    @Override
                    public int getCount() {
                        return foods.size();
                    }

                    @Override
                    public boolean isViewFromObject(View view, Object o) {
                        return view == o;
                    }

                    @Override
                    public Object instantiateItem(ViewGroup container, int position) {
                        ImageView view = new ImageView(container.getContext());
                        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ImageLoader.getInstance().displayImage(foods.get(position).getImg(), view, options);
                        container.addView(view);
                        return view;
                    }

                    @Override
                    public void destroyItem(ViewGroup container, int position, Object object) {
                        container.removeView((View) object);
                    }
                });

                indicator.setViewPager(pager);
                indicator.setSnap(true);

                pager.setScrollFactgor(5);
                pager.setOffscreenPageLimit(4);
                pager.startAutoScroll(2000);
                pager.setOnPageClickListener(new AutoScrollViewPager.OnPageClickListener() {
                    @Override
                    public void onPageClick(AutoScrollViewPager autoScrollPager, int position) {
                        Intent intent = new Intent(getContext(), TutorFoodActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(FoodAdapter.FOOD_OBJ, foods.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}