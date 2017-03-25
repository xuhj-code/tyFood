package com.food.ty.tyfood.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.food.ty.tyfood.FoodClient;
import com.food.ty.tyfood.R;
import com.food.ty.tyfood.User;
import com.food.ty.tyfood.activity.MainActivity;
import com.food.ty.tyfood.adapter.OrdersListAdapter;
import com.food.ty.tyfood.model.Order;

import org.json.JSONArray;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ty on 2017/3/2.
 */

public class OrderFragment extends Fragment {
    private ListView orderContentListView;
    private List<Order> mListData = new ArrayList<>();
    FoodClient foodClient = FoodClient.getInstance();
    private OrdersListAdapter ordersListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        // 当Fragment重建之后向Activity返回新的Fragment
        ((MainActivity) getActivity()).refreshTwoFragment(this);
        orderContentListView = (ListView) getActivity().findViewById(R.id.lv_order_info);
        getOrderInfo();
        ordersListAdapter = new OrdersListAdapter(getActivity(), mListData,
                R.layout.orders_item);
        orderContentListView.setAdapter(ordersListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).refreshTwoFragment(this);

    }

    public void getOrderInfo()
    {
        RequestParams params = new RequestParams(foodClient.API+"/GetAllOrderByUserNameServlet");
        params.addQueryStringParameter("userName", User.getInstance(getActivity()).getUserName());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try
                {

                    JSONArray jsonArray=new JSONArray(result);
                    Order orderData;
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        orderData = new Order();
                        orderData.shopName = jsonArray.getJSONObject(i).getString("shopName");
                        orderData.orderTotalMoney = jsonArray.getJSONObject(i).getString("orderTotalMoney");
                        orderData.orderStatus = jsonArray.getJSONObject(i).getInt("orderStatus");
                        mListData.add(orderData);
                        ordersListAdapter.notifyDataSetChanged();

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
}
