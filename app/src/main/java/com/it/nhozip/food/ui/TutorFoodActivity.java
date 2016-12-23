package com.it.nhozip.food.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.it.nhozip.food.R;
import com.it.nhozip.food.adapter.FoodAdapter;
import com.it.nhozip.food.database.DatabaseManager;
import com.it.nhozip.food.obj.Food;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.sackcentury.shinebuttonlib.ShineButton;

/**
 * Created by huyen on 9/13/2016.
 */
public class TutorFoodActivity extends SwipeBackActivity implements YouTubePlayer.OnInitializedListener{
    private ImageView imgFood;
    private TextView txtNguyenLieu, txtHuongDan;
    private Toolbar toolbar;
    private ShineButton imgLike;

    private DatabaseManager databaseManager;

    private String img;
    private static final String l = "TutorFoodActivity";
    private static final String KEY_YOUTOBE = "AIzaSyDpygTbGQFycVStuKuP85BZDbGqHxIV3VE";
    private Food food;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        initView();



//        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
//        youTubeView.initialize(KEY_YOUTOBE, this);
        try{
            YouTubePlayerSupportFragment frag =
                    (YouTubePlayerSupportFragment)getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
            frag.initialize(KEY_YOUTOBE, this);
        }catch (Exception e){
            Log.e(l,e.toString());
        }


        databaseManager = new DatabaseManager(this);
        Intent i = getIntent();
        food = (Food) i.getSerializableExtra(FoodAdapter.FOOD_OBJ);
        Log.e("food", food.toString());
        setTitle(food.getName());

        Glide.with(getApplicationContext()).load(food.getImg().toString())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgFood);
        txtNguyenLieu.setText(getConten(food.getNguyenlieu()));
        txtHuongDan.setText(getConten(food.getHuongdan()));
        //   boolean checked=false;
        onClickStar(food);
        imgLike.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            public boolean checked = false;
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                this.checked = checked;
                onClickStar(food);

            }
        });
    }


    public void onClickStar(final Food food) {
        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(food);
            }
        });
    }


    private void save(Food food) {
        String name = getConten(food.getName());
        img = food.getImg();

        String nguyenlieu = getConten(food.getNguyenlieu());
        String video = food.getVideo();
        String huongdan = getConten(food.getHuongdan());
        Food f = new Food(name, img, nguyenlieu, video, huongdan);
        if (databaseManager.getNotIMG(img).compareTo(img) == 0) {
            databaseManager.dellNote(img);
            Toast.makeText(getApplicationContext(), "Xóa khỏi danh sách thích", Toast.LENGTH_LONG).show();

        } else {
            databaseManager.insertNote(f);
            Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_LONG).show();
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgLike = (ShineButton) findViewById(R.id.like);
        imgLike.init(this);
       setSupportActionBar(toolbar);
        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgFood = (ImageView) findViewById(R.id.image);
        txtNguyenLieu = (TextView) findViewById(R.id.txtNguyeLieu);
        txtHuongDan = (TextView) findViewById(R.id.txtCachLam);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  overridePendingTransition(R.anim.right_end,R.anim.left_end);
    }

    private String getConten(String c) {

        String[] data = c.split("-");
        StringBuilder content = new StringBuilder();
        for (String s : data) {
            content.append(s + "\n");
        }
        return content.toString();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        if(!b){
//            Log.e("food", food.getVideo());
            youTubePlayer.cueVideo(food.getVideo());
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.e("YouTubePlayer","ERRR");
    }
    private YouTubePlayer.PlaybackEventListener playbackEventListener=new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener=new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
}
