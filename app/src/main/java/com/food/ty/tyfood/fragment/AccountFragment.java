package com.food.ty.tyfood.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.food.ty.tyfood.FoodClient;
import com.food.ty.tyfood.GlideUtil;
import com.food.ty.tyfood.R;
import com.food.ty.tyfood.User;
import com.food.ty.tyfood.activity.LoginActivity;
import com.food.ty.tyfood.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ty on 2017/3/2.
 */

public class AccountFragment extends Fragment {
    FoodClient foodClient = FoodClient.getInstance();
    protected static User user;

    ImageView ihead;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        // 当Fragment重建之后向Activity返回新的Fragment
        ((MainActivity) getActivity()).refreshAccountFragment(this);

        ihead=(ImageView)getActivity().findViewById(R.id.iv_head);
        user = User.getInstance(getActivity());


        getUserInfo();
    }
    public void getUserInfo()
    {

        RequestParams params = new RequestParams(foodClient.API+"/FindUserByName");
        params.addQueryStringParameter("name", user.getUserName());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try
                {
                    JSONObject jsonObject=new JSONObject(result);
                    final String url = foodClient.API+jsonObject.getString("userImage");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GlideUtil.getInstance().loadImage(getContext(),ihead,url,true);
                        }
                    });


                }
                catch(Exception ex)
                {

                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }



}
