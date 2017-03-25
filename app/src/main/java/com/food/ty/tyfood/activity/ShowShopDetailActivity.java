package com.food.ty.tyfood.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.food.ty.tyfood.FoodClient;
import com.food.ty.tyfood.GlideUtil;
import com.food.ty.tyfood.R;
import com.food.ty.tyfood.adapter.CommonAdapter;
import com.food.ty.tyfood.adapter.FoodListAdapter;
import com.food.ty.tyfood.adapter.ViewHolder;
import com.food.ty.tyfood.model.Food;

import org.json.JSONArray;
import org.xutils.common.Callback;
import org.xutils.db.DbModelSelector;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ty on 2017/3/6.
 */

public class ShowShopDetailActivity extends AppCompatActivity implements FoodListAdapter.FoodActivityListenerCallback,Serializable{
    String shopid;
    String shopName;
    FoodClient foodClient = FoodClient.getInstance();
    private List<Food> mListData = new ArrayList<>();
    private ListView foodContentListView;
    private ListView orderContentListView;
    private ImageView shopCardImageView;
    private TextView totalPriceTextView;
    private TextView balanceImageView;
    private FoodListAdapter foodListAdapter;
    private List<Food> balanceFoodList = new ArrayList<>();
    private float totalPrice=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shop_detail);
        foodContentListView=(ListView) findViewById(R.id.food_info);
        orderContentListView=(ListView)findViewById(R.id.order_info);
        shopCardImageView=(ImageView)findViewById(R.id.iv_shop_card);
        totalPriceTextView=(TextView)findViewById(R.id.tv_total_price);
        balanceImageView=(TextView)findViewById(R.id.tv_balance);
        Intent intent=this.getIntent();
        shopid=intent.getStringExtra("shop_id");
        shopName=intent.getStringExtra("shop_name");
        getFoodInfo();
        foodListAdapter = new FoodListAdapter(this, mListData,
                R.layout.food_item);

        foodListAdapter.setListenerCallback(this);

        foodContentListView.setAdapter(foodListAdapter);
        shopCardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showShopInfo(v);
            }
        });
        balanceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balanceFoodList.clear();
                balanceCommit();

                if (balanceFoodList.isEmpty())
                    Toast.makeText(ShowShopDetailActivity.this,"请选择菜谱",Toast.LENGTH_SHORT).show();
                else
                {
                    Intent intent = new Intent(ShowShopDetailActivity.this,ShowOrderActivity.class);
                    intent.putExtra("shop_name",shopName);
                    intent.putExtra("order_list",(Serializable) balanceFoodList);
                    intent.putExtra("pay_money",totalPriceTextView.getText());
//                    startActivity(intent);
                    startActivityForResult(intent,1);
                }
            }
        });
    }
    public void getFoodInfo()
    {
        RequestParams params = new RequestParams(foodClient.API+"/FindFoodByShopId");
        params.addQueryStringParameter("shopId", shopid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try
                {
                    JSONArray jsonArray=new JSONArray(result);
                    Food foodData;
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        foodData = new Food();
                        foodData.foodid=jsonArray.getJSONObject(i).getString("id");
                        foodData.foodName = jsonArray.getJSONObject(i).getString("foodName");
                        foodData.foodPrice= jsonArray.getJSONObject(i).getString("foodPrice");
                        foodData.foodImage=foodClient.API+jsonArray.getJSONObject(i)
                                .getString("foodImage");
                        foodData.shopid=jsonArray.getJSONObject(i).getString("shopId");
                        mListData.add(foodData);
                        foodListAdapter.notifyDataSetChanged();
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

    @Override
    public void animClick(Food food,int flag) {
        if (foodCardCommonAdapter==null) {

        }else{
            foodCardCommonAdapter.notifyDataSetChanged();
        }
        if (flag==0)
            totalPrice+=Float.valueOf(food.foodPrice);
        else if (flag==1)
            totalPrice-=Float.valueOf(food.foodPrice);
        totalPriceTextView.setText(String.valueOf(totalPrice));

    }

    boolean isOpenCard=false;
    private FoodCardCommonAdapter foodCardCommonAdapter;
    public void showShopInfo(View view){
        if(!isOpenCard){
            orderContentListView.setVisibility(View.VISIBLE);
            foodCardCommonAdapter=new FoodCardCommonAdapter();
            orderContentListView.setAdapter(foodCardCommonAdapter);
        }else{
            orderContentListView.setVisibility(View.GONE);
        }
        isOpenCard=!isOpenCard;

    }

    private class FoodCardCommonAdapter extends CommonAdapter<Food> {
        public FoodCardCommonAdapter() {
            super(ShowShopDetailActivity.this, ShowShopDetailActivity.this.mListData, R.layout.shop_cart_item);
        }
        @Override
        public void convert(ViewHolder holder, Food food, int position) {
            if(mListData.get(position).mCount==0){
                holder.getConvertView().setVisibility(View.GONE);
            }else{
                holder.getConvertView().setVisibility(View.VISIBLE);
                holder.setText(R.id.tv_foodname,food.foodName);
                holder.setText(R.id.tv_food_total_price,    Float.valueOf(food.foodPrice)*food.mCount+"");
            }

        }
    }
    public void balanceCommit()
    {
        Food balancefood;
        for (Food food:mListData)
        {
            if (food.mCount!=0)
            {
                balancefood = new Food();
                balancefood.shopid = food.shopid;
                balancefood.foodid = food.foodid;
                balancefood.foodName = food.foodName;
                balancefood.foodPrice = food.foodPrice;
                balancefood.mCount = food.mCount;
                balanceFoodList.add(balancefood);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==1)
            finish();
    }
}
