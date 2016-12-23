package com.it.nhozip.food.widgets;

import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;

/**
 * Created by huyen on 8/27/2016.
 */
public abstract class HidingScrollListener implements NestedScrollView.OnScrollChangeListener {
    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;


    public abstract void onHide();
    public abstract void onShow();

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
//            onHide();
//            controlsVisible = false;
//            scrolledDistance = 0;
//            Log.e("onScrollChange","Top");
//        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
//            onShow();
//            controlsVisible = true;
//            scrolledDistance = 0;
//            Log.e("onScrollChange","Up");
//        }
//
//        if((controlsVisible && scrollX>0) || (!controlsVisible && scrollY<0)) {
//            scrolledDistance += scrollY;
//            Log.e("onScrollChange","XXXX");
//        }
        if(scrollX <= 0){
            Log.e("onScrollChange","Top");
        }
        View view = (View) v.getChildAt(v.getChildCount() - 1);
        int diff = (view.getBottom() - (v.getHeight() + v.getScrollY()));
        if( diff <= 0 ){
            // if diff is zero, then the bottom has been reached
            Log.e("onScrollChange","Up");
        }
//        if(oldScrollX-scrollX>0){
//            Log.e("onScrollChange","Top");
//        }
//        else{
//            Log.e("onScrollChange","Up");
//        }
        Log.e("onScrollChange","onScrollChange");
    }
}