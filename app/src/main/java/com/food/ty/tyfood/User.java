package com.food.ty.tyfood;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ty on 2017/3/2.
 */

public class User {
    private static User singleton;
    private static SharedPreferences sp;
    //防止创建多个实例
    private User() {
    }

    public static User getInstance(Context context) {
        if (singleton == null) {
            singleton = new User();
        }
        // 考虑使用SharedPrefrences保存关键数据，防止activity重建导致数据丢失。调用系统摄像头就可能导致Activity重建
        // 使用savedInstanceState太麻烦了，凡是遇到可能重建的情况都需要savedInstanceState
        sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        return singleton;
    }



    public String getUserName(){
        return sp.getString("userName","");
    }

    public void setUserName(String userName)
    {
        sp.edit().putString("userName",userName).commit();
    }
    public String getUserPassword(){
        return sp.getString("userPassword","");
    }

    public void setUserPassword(String userPassword)
    {
        sp.edit().putString("userPassword",userPassword).commit();
    }



}
