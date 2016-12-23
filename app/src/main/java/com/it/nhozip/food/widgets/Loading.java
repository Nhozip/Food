package com.it.nhozip.food.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.it.nhozip.food.R;

/**
 * Created by huyen on 10/26/2016.
 */
public class Loading  {
    private static Dialog pd;
    private static ProgressBar loading;
    private static  TextView noti;
    public static Dialog LoadingSpinner(Context mContext){
         pd = new Dialog(mContext, android.R.style.Theme_Black);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
         loading= (ProgressBar) view.findViewById(R.id.loading);

        loading.getIndeterminateDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);

         noti= (TextView) view.findViewById(R.id.noti);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.getWindow().setBackgroundDrawableResource(R.color.bg_nav);
        pd.setContentView(view);
        return pd;
    }
    public  static  void beginLoading(String content){
        noti.setText(content);
        pd.show();

    }
    public  static  void finishLoading(String content){
        noti.setText(content);
        pd.dismiss();

    }

}
