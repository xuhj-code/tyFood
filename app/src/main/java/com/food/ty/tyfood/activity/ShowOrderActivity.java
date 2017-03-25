package com.food.ty.tyfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.food.ty.tyfood.FoodClient;
import com.food.ty.tyfood.R;
import com.food.ty.tyfood.User;
import com.food.ty.tyfood.adapter.OrderListAdapter;
import com.food.ty.tyfood.model.Food;
import com.food.ty.tyfood.model.Order;
import com.food.ty.tyfood.model.OrderDetail;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ty on 2017/3/20.
 */

public class ShowOrderActivity extends AppCompatActivity {
    FoodClient foodClient = FoodClient.getInstance();
    private TextView shopNameTextView;
    private ListView orderListView;
    private TextView payMoneyTextView;
    private TextView submitOrderTextView;
    private String shopName;
    private List<Food> foodList;
    private String money;
    private Order order;
    private List<OrderDetail> orderDetailList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);
        Intent intent = this.getIntent();
        shopName=intent.getStringExtra("shop_name");
        foodList = (List<Food>)intent.getSerializableExtra("order_list");
        money=intent.getStringExtra("pay_money");

        shopNameTextView = (TextView) findViewById(R.id.tv_order_shop_name);
        shopNameTextView.setText(shopName);

        orderListView = (ListView) findViewById(R.id.lv_order_food_info);
        OrderListAdapter orderListAdapter=new OrderListAdapter(this,foodList,R.layout.order_item);
        orderListView.setAdapter(orderListAdapter);

        payMoneyTextView = (TextView) findViewById(R.id.tv_pay_money);
        payMoneyTextView.setText(money);

        order = new Order();
        order.userName = User.getInstance(this).getUserName();
        order.shopId = foodList.get(0).shopid;
        order.orderTotalMoney = money;

        orderDetailList = new ArrayList<OrderDetail>();
        for (Food food:foodList)
        {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.foodId = food.foodid;
            orderDetail.foodNum = food.mCount;
            orderDetailList.add(orderDetail);
        }

        submitOrderTextView = (TextView)findViewById(R.id.tv_submit_order);
        submitOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });



    }

    public void submitOrder()
    {
        RequestParams params = new RequestParams(foodClient.API+"/SubmitOrderServlet");

        params.addBodyParameter("orderInfo",new Gson().toJson(order));
        params.addBodyParameter("orderDetailInfo",new Gson().toJson(orderDetailList));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result.equals("success"))
                {
                    Toast.makeText(ShowOrderActivity.this,"提交成功", Toast.LENGTH_SHORT).show();
                    setResult(1);
                    finish();
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
