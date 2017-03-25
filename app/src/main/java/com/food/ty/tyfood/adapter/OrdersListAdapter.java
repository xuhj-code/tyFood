package com.food.ty.tyfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.food.ty.tyfood.R;
import com.food.ty.tyfood.model.Order;
import com.food.ty.tyfood.model.Shop;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ty on 2017/3/6.
 */

public class OrdersListAdapter extends BaseAdapter {
    private List<Order> mListData;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private int mResource;
    private String shopName;
    private String totalMoney;
    private int orderStatus;
    static class ViewHolder {
        private TextView shopNameText;
        private TextView totalMonetText;
        private TextView orderStatusText;
    }
    public OrdersListAdapter(Context context, List<Order> listData,
                             int resource)
    {
        this.context=context;
        this.mListData=listData;
        this.mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mResource = resource;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int i) {
        return mListData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView==null)
        {
            convertView = mLayoutInflater.inflate(mResource, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.shopNameText=(TextView) convertView.
                    findViewById(R.id.tv_orders_shop_name);
            mViewHolder.totalMonetText=(TextView) convertView
                    .findViewById(R.id.tv_orders_total_money);
            mViewHolder.orderStatusText=(TextView)convertView
                    .findViewById(R.id.tv_orders_status);
            convertView.setTag(mViewHolder);

        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.shopNameText.setText(mListData.get(position).shopName);
        mViewHolder.totalMonetText.setText(mListData.get(position).orderTotalMoney);
        if (mListData.get(position).orderStatus==0)
            mViewHolder.orderStatusText.setText("未完成");
        else
            mViewHolder.orderStatusText.setText("已完成");

        return convertView;
    }


}
