package com.food.ty.tyfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.food.ty.tyfood.R;
import com.food.ty.tyfood.activity.ShowOrderActivity;
import com.food.ty.tyfood.model.Food;

import java.util.List;

/**
 * Created by ty on 2017/3/20.
 */

public class OrderListAdapter extends BaseAdapter {
    private List<Food> mListData;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private int mResource;
    private String orderName;
    private String orderPrice;
    private int orderNum;

    static class ViewHolder {
        private TextView nameText;
        private TextView priceText;
        private TextView numText;
    }
    public OrderListAdapter(Context context, List<Food> listData,
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

            mViewHolder.nameText=(TextView) convertView
                    .findViewById(R.id.tv_order_food_name);
            mViewHolder.priceText=(TextView) convertView
                    .findViewById(R.id.tv_order_food_price);
            mViewHolder.numText=(TextView) convertView
                    .findViewById(R.id.tv_order_food_num);
            convertView.setTag(mViewHolder);

        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }


        mViewHolder.nameText.setText(mListData.get(position).foodName);
        mViewHolder.priceText.setText(mListData.get(position).foodPrice);
        mViewHolder.numText.setText("x"+mListData.get(position).mCount);

        return convertView;
    }



}
