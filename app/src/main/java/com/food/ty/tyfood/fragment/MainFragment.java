package com.food.ty.tyfood.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.food.ty.tyfood.FoodClient;
import com.food.ty.tyfood.R;
import com.food.ty.tyfood.activity.MainActivity;
import com.food.ty.tyfood.activity.ShowShopDetailActivity;
import com.food.ty.tyfood.adapter.ShopListAdapter;
import com.food.ty.tyfood.model.Shop;

import org.json.JSONArray;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
/**
 * Created by ty on 2017/3/2.
 */

public class MainFragment extends Fragment {
    FoodClient foodClient = FoodClient.getInstance();
    private ListView shopContentListView;
    private ShopListAdapter shopListAdapter;
    private List<Shop> mListData = new ArrayList<>();
//    private RollPagerView mRollViewPager;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        // 当Fragment重建之后向Activity返回新的Fragment
        ((MainActivity) getActivity()).refreshMainFragment(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
//
//        mRollViewPager = (RollPagerView) getActivity().findViewById(R.id.roll_view_pager);
//
//        //设置播放时间间隔
//        mRollViewPager.setPlayDelay(1000);
//        //设置透明度
//        mRollViewPager.setAnimationDurtion(500);
//        //设置适配器
//        mRollViewPager.setAdapter(new TestNormalAdapter());
//
//        //设置指示器（顺序依次）
//        //自定义指示器图片
//        //设置圆点指示器颜色
//        //设置文字指示器
//        //隐藏指示器
//        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
//        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW,Color.WHITE));


        shopContentListView=(ListView)getActivity().findViewById(
                R.id.shop_info);

        getShopInfo();
        shopListAdapter = new ShopListAdapter(getActivity(), mListData,
                R.layout.shop_item);
        shopContentListView.setAdapter(shopListAdapter);

        shopContentListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),
                        ShowShopDetailActivity.class);
                intent.putExtra("shop_id", mListData.get(position).shopid);
                intent.putExtra("shop_name",mListData.get(position).shopName);
                startActivity(intent);


            }
        });

    }


    public void getShopInfo()
    {
        RequestParams params = new RequestParams(foodClient.API+"/ShopServlet");
        params.addQueryStringParameter("method", "list");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try
                {

                    JSONArray jsonArray=new JSONArray(result);
                    Shop shopData;
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        shopData = new Shop();
                        shopData.shopid=jsonArray.getJSONObject(i).getString("id");
                        shopData.shopName = jsonArray.getJSONObject(i).getString("shopName");
                        shopData.shopInfo=jsonArray.getJSONObject(i).getString("shopInfo");
                        shopData.shopImage=foodClient.API+jsonArray.getJSONObject(i)
                                .getString("shopImage");
                        mListData.add(shopData);
                        shopListAdapter.notifyDataSetChanged();

                    }

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

//    private class TestNormalAdapter extends StaticPagerAdapter {
//        private int[] imgs = {
//                R.drawable.bg_one,
//                R.drawable.bg_two,
//                R.drawable.bg_three,
//                R.drawable.bg_four,
//        };
//
//
//        @Override
//        public View getView(ViewGroup container, int position) {
//            ImageView view = new ImageView(container.getContext());
//            view.setImageResource(imgs[position]);
//            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            return view;
//        }
//
//
//        @Override
//        public int getCount() {
//            return imgs.length;
//        }
//    }
//
////    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        return super.onOptionsItemSelected(item);
//    }




}
