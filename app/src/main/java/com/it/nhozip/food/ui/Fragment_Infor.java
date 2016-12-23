package com.it.nhozip.food.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.it.nhozip.food.R;
import com.it.nhozip.food.fb.MyFaceBook;
import com.it.nhozip.food.mSharedPreferences.MyPre;


/**
 * Created by huyen on 9/17/2016.
 */
public class Fragment_Infor extends Fragment {
    private ImageView imgAvata;
    private TextView txtName;
    private String jsondata;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_infor, container, false);
        intView(view);

        if (MyPre.getLoginWith(getContext()) == 1) {
            jsondata = MyPre.getDataOFFFB(getContext());
            txtName.setText(MyFaceBook.getnfor(jsondata, getContext()).getName());
            Glide.with(getContext()).load(MyFaceBook.getnfor(jsondata, getContext()).getAvata())
                    .thumbnail(1f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgAvata);
        }
        if (MyPre.getLoginWith(getContext()) == 2) {
            txtName.setText(MyPre.getInforGoogleOff(getContext()).getName());
            Glide.with(getContext()).load(MyPre.getInforGoogleOff(getContext()).getAvata())
                    .thumbnail(1f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgAvata);
        }
        return view;
    }

    private void intView(View hView) {
        txtName = (TextView) hView.findViewById(R.id.txtNameFB);
        imgAvata = (ImageView) hView.findViewById(R.id.imgAvata);

    }
}
