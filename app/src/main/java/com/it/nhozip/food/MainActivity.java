package com.it.nhozip.food;

import android.content.Intent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.it.nhozip.food.database.DatabaseManager;
import com.it.nhozip.food.fAB.OnClickFAB;
import com.it.nhozip.food.fb.MyFaceBook;
import com.it.nhozip.food.firebase.MyData;
import com.it.nhozip.food.google.MyGoogle;
import com.it.nhozip.food.mSharedPreferences.MyPre;
import com.it.nhozip.food.ui.Fragment_Infor;
import com.it.nhozip.food.ui.Fragment_Like;
import com.it.nhozip.food.ui.FragmnetHome;
import com.it.nhozip.food.ui.Login_Activity;
import com.it.nhozip.food.widgets.AutoScrollPagerFragment;
import com.it.nhozip.food.widgets.TextFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String key_food = null;
    String jsondata;
    private CircleImageView imgAvata;
    private ImageView imgBia;
    private TextView txtName;
    private CallbackManager callbackManager;
    private  ViewPager contentPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        setContentView(R.layout.activity_main);
        initView();



        if (savedInstanceState == null) {

            ///


            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).build();
            ImageLoader.getInstance().init(configuration);

             contentPager = (ViewPager)findViewById(R.id.pager);

            contentPager.setOffscreenPageLimit(2);
            MyPagerAdapter adapter=new MyPagerAdapter(getSupportFragmentManager());
            contentPager.setAdapter(adapter);

            Fragment fragment = null;
            try {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragment = FragmnetHome.class.newInstance();
                fragmentManager.beginTransaction().replace(R.id.fContent, fragment).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (MyPre.getLoginWith(this) == 1) {
            if (!MyPre.loginFirst(this)) {
                jsondata = getIntent().getStringExtra("jsondata");

            } else {
                jsondata = MyPre.getDataOFFFB(this);
            }
            txtName.setText(MyFaceBook.getnfor(jsondata, getApplicationContext()).getName());
            Glide.with(getApplicationContext()).load(MyFaceBook.getnfor(jsondata, getApplicationContext()).getAvata())
                    .thumbnail(1f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgAvata);
        }
        if (MyPre.getLoginWith(this) == 2) {
            try {
                Log.e("MyGoogle", MyGoogle.getInfor().toString());
                txtName.setText(MyGoogle.getInfor().getName());
                Glide.with(getApplicationContext()).load(MyGoogle.getInfor().getAvata())
                        .thumbnail(1f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgAvata);
            } catch (Exception e) {
                Log.e("MyGoogle", MyPre.getInforGoogleOff(this).toString());
                txtName.setText(MyPre.getInforGoogleOff(this).getName());
                Glide.with(getApplicationContext()).load(MyPre.getInforGoogleOff(this).getAvata())
                        .thumbnail(1f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgAvata);
            }

        }


    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    protected void facebookSDKInitialize() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        imgBia = (ImageView) hView.findViewById(R.id.imgBia);


        txtName = (TextView) hView.findViewById(R.id.txtNameFB);
        Typeface tfWellcome = Typeface.createFromAsset(getAssets(),
                "fonts/VFFORMN.TTF");
        txtName.setTypeface(tfWellcome);


        imgAvata = (CircleImageView) hView.findViewById(R.id.imgAvata);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        //



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClickPAB(View v) {
        switch (v.getId()) {
            case R.id.fAbShare:
                OnClickFAB.shareFB(this);
                break;
            case R.id.fAbYKien:
                OnClickFAB.yKien(this);
                break;
            case R.id.fABDanhGia:
                OnClickFAB.rateApp(this);
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (id) {
            case R.id.nav_Home:
                key_food = "Home";
                contentPager.setVisibility(View.VISIBLE);

                ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).build();
                ImageLoader.getInstance().init(configuration);

                contentPager = (ViewPager)findViewById(R.id.pager);

                contentPager.setOffscreenPageLimit(2);
                MyPagerAdapter adapter=new MyPagerAdapter(getSupportFragmentManager());
                contentPager.setAdapter(adapter);


                fragmentClass = FragmnetHome.class;
                break;
            case R.id.nav_Like:
                key_food = "Yêu Thích";
                contentPager.setVisibility(View.GONE);
                fragmentClass = Fragment_Like.class;
                break;
            case R.id.nav_Infor:
                contentPager.setVisibility(View.GONE);
                key_food = "Thông tin ";
                fragmentClass = Fragment_Infor.class;
                break;

            case R.id.navExit:
                contentPager.setVisibility(View.GONE);
                MyPre.updateTrangThai(this);

                if (MyPre.getLoginWith(this) == 1) {
                    FacebookSdk.sdkInitialize(this);
                  //  AccessToken accessToken = AccessToken.getCurrentAccessToken();
                  //  MyData.createUser(accessToken.getUserId(), databaseManager.getListNote());
                    LoginManager.getInstance().logOut();
                }
                if (MyPre.getLoginWith(this) == 2) {
                  //  MyData.createUser(MyPre.getInforGoogleOff(this).getId(), databaseManager.getListNote());
                    MyGoogle.signOut(getApplicationContext());
                }
                //databaseManager.dellAll();
                Intent intent = new Intent(this, Login_Activity.class);
                startActivity(intent);
                finish();
                break;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fContent, fragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle(key_food);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
