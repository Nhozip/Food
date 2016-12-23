package com.it.nhozip.food.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it.nhozip.food.R;
import com.it.nhozip.food.firebase.MyData;
import com.it.nhozip.food.widgets.AutoScrollPagerFragment;
import com.it.nhozip.food.widgets.TextFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by huyen on 9/15/2016.
 */
public class FragmnetHome extends Fragment implements View.OnClickListener {
    private RecyclerView rvHot, rvMBac, rVMTrung, rVMNam, rVNew;
    public  static  final String KEY_FOOD="key_food";
    public  static  final String KEY_PK="pk_food";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_home, container, false);
        intView(view);



        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getContext()).build();
        ImageLoader.getInstance().init(configuration);

        ViewPager contentPager = (ViewPager) view.findViewById(R.id.pager);

        contentPager.setOffscreenPageLimit(2);
        MyPagerAdapter adapter=new MyPagerAdapter(getFragmentManager());
        contentPager.setAdapter(adapter);

        MyData.getDataaRecyView(MyData.FOOD_NEW, rVNew, getContext(),1);
        MyData.getDataaRecyView(MyData.M_BAC, rvMBac, getContext(),1);
        MyData.getDataaRecyView(MyData.M_TRUNG, rVMTrung, getContext(),1);
        MyData.getDataaRecyView(MyData.M_NAM, rVMNam, getContext(),1);
        MyData.getDataaRecyView(MyData.STUDENT, rvHot, getContext(),1);

        view.findViewById(R.id.more_new).setOnClickListener(this);
        view.findViewById(R.id.more_bac).setOnClickListener(this);
        view.findViewById(R.id.more_trung).setOnClickListener(this);
        view.findViewById(R.id.more_nam).setOnClickListener(this);
        view.findViewById(R.id.more_student).setOnClickListener(this);



        return view;
    }
    private void intView(View view) {


        rvHot = (RecyclerView) view.findViewById(R.id.rVHit);
        rvMBac = (RecyclerView) view.findViewById(R.id.rVBac);
        rVMTrung = (RecyclerView) view.findViewById(R.id.rVTrung);
        rVNew = (RecyclerView) view.findViewById(R.id.rVNew);
        rVMNam = (RecyclerView) view.findViewById(R.id.rVNam);
        //

        //
        rvHot.setHasFixedSize(false);
        rvMBac.setHasFixedSize(false);
        rVMTrung.setHasFixedSize(false);
        rVMNam.setHasFixedSize(false);
        rVNew.setHasFixedSize(false);
        //
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //
        rvHot.setLayoutManager(layoutManager);
        rvMBac.setLayoutManager(layoutManager1);
        rVMTrung.setLayoutManager(layoutManager2);
        rVMNam.setLayoutManager(layoutManager3);
        rVNew.setLayoutManager(layoutManager4);

       // initRecyclerView(view,rvHot,layoutManager);
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.e("XXXXXXXXXXXXXXX","onStart");
    }

    @Override
    public void onResume() {
        Log.e("XXXXXXXXXXXXXXX","onResume");
        super.onResume();
    }


    @Override
    public void onClick(View view) {

        Bundle bundle=new Bundle();
        switch (view.getId()) {
            case R.id.more_new:
                bundle.putInt(KEY_FOOD,1);
                break;
            case R.id.more_bac:
                bundle.putInt(KEY_FOOD,2);
                break;
            case R.id.more_trung:
                bundle.putInt(KEY_FOOD,3);
                break;
            case R.id.more_nam:
                bundle.putInt(KEY_FOOD,4);
                break;
            case R.id.more_student:
                bundle.putInt(KEY_FOOD,5);
                break;
        }
        Intent intent = new Intent(getContext(), ActivityFood.class);
        intent.putExtra(KEY_PK,bundle);
        startActivity(intent);
    }
    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                Log.e("XXXXXXXXX1",position+"");
                return new AutoScrollPagerFragment();

            }
            Log.e("XXXXXXXXX2",position+"");
            return TextFragment.newInstance("Fragment " + position);
            }

    }

}
