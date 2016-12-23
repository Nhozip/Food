package com.it.nhozip.food.mSharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.it.nhozip.food.obj.You;

/**
 * Created by huyen on 9/22/2016.
 */
public class MyPre {
    public static final String DATA_FB = "fb";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IMG = "img";
    public static final String DATA_GOOGLE = "google";
    public static final String LOGIN_WITH = "login_with";
    public static final String LOGIN = "login";
    private static SharedPreferences pre;

    public static void setdataOffFB(Context context, String json_object) {
        pre = context.getSharedPreferences
                (DATA_FB, 0);
        SharedPreferences.Editor editor = pre.edit();
        //editor.clear();
        editor.putBoolean(LOGIN, true);
        editor.putString("data", json_object);
        editor.commit();
    }
    public static void updateTrangThai(Context context) {
         pre = context.getSharedPreferences
                (DATA_FB, 0);
        SharedPreferences.Editor editor = pre.edit();
      //  editor.clear();
        editor.putBoolean(LOGIN, false);
        editor.commit();
    }
    public static void setLoginWith(Context context,int with){
        pre = context.getSharedPreferences
                (DATA_FB, 0);
        SharedPreferences.Editor editor = pre.edit();
        //editor.clear();
        editor.putInt(LOGIN_WITH,with);
        editor.commit();
    }
    public static String getDataOFFFB(Context context){
         pre = context.getSharedPreferences
                (DATA_FB, 0);
        return pre.getString("data","");
    }
    public static void setDataOFFGoogle(Context context,String id,String name,String img){
        pre = context.getSharedPreferences
                (DATA_FB, 0);
        SharedPreferences.Editor editor = pre.edit();
       // editor.clear();
        editor.putBoolean(LOGIN, true);
        editor.putString(ID, id);
        editor.putString(NAME, name);
        editor.putString(IMG, img);
        editor.commit();
    }
    public  static You getInforGoogleOff(Context context){
        pre = context.getSharedPreferences
                (DATA_FB, 0);
        You you=new You();
        you.setId(pre.getString(ID,null));
        you.setName(pre.getString(NAME,null));
        you.setAvata(pre.getString(IMG,null));
        return you;
    }
    public  static  int getLoginWith(Context context){
        pre = context.getSharedPreferences
                (DATA_FB, 0);
        return pre.getInt(LOGIN_WITH,0);
    }
    public static boolean loginFirst(Context context) {
        pre = context.getSharedPreferences
                (DATA_FB, 0);
        return pre.getBoolean(LOGIN, false);
    }

}
