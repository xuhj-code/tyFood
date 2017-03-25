package com.food.ty.tyfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.food.ty.tyfood.R;
import com.food.ty.tyfood.model.Food;
import com.food.ty.tyfood.view.IOnAddDelListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ty on 2017/3/6.
 */

public class FoodListAdapter extends BaseAdapter {
    private List<Food> mListData;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private int mResource;
    private String foodName;
    private String foodImage;
    private String foodPrice;
    private String foodid;
    private String shopid;

    static class ViewHolder {
        private TextView nameText;
        private TextView priceText;
        private ImageView photoImage;
        private TextView foodIdText;
        private TextView shopIdText;

        private com.food.ty.tyfood.view.AnimShopButton btnEleListText;
    }
    public FoodListAdapter(Context context, List<Food> listData,
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView==null)
        {
            convertView = mLayoutInflater.inflate(mResource, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.photoImage= (ImageView) convertView.
                    findViewById(R.id.iv_foodImage);
            mViewHolder.nameText=(TextView) convertView
                    .findViewById(R.id.tv_food_name);
            mViewHolder.priceText=(TextView)convertView
                    .findViewById(R.id.tv_food_price);
            mViewHolder.foodIdText=(TextView)convertView
                    .findViewById(R.id.tv_food_id);
            mViewHolder.shopIdText=(TextView)convertView
                    .findViewById(R.id.tv_shop_id);
            mViewHolder.btnEleListText=(com.food.ty.tyfood.view.AnimShopButton)convertView
                    .findViewById(R.id.btnEleList);
            convertView.setTag(mViewHolder);

        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(mListData.get(position).foodImage)
                .into(mViewHolder.photoImage);
        mViewHolder.nameText.setText(mListData.get(position).foodName);
        mViewHolder.priceText.setText(mListData.get(position).foodPrice);
        mViewHolder.foodIdText.setText(mListData.get(position).foodid);
        mViewHolder.shopIdText.setText(mListData.get(position).shopid);


        mViewHolder.btnEleListText.setOnAddDelListener(new IOnAddDelListener() {
            @Override
            public void onAddSuccess(int count) {
                //点击加号后会只i系那个这个方法并把count值传回到这里 你的对象
                mListData.get(position).mCount=count;
                listenerCallback.animClick(mListData.get(position),0);
            }

            @Override
            public void onAddFailed(int count, FailType failType) {
                mListData.get(position).mCount=count;
             //   listenerCallback.animClick(mListData.get(position));
            }

            @Override
            public void onDelSuccess(int count)
            {
                mListData.get(position).mCount=count;
                listenerCallback.animClick(mListData.get(position),1);
            }

            @Override
            public void onDelFaild(int count, FailType failType) {
                mListData.get(position).mCount=count;

            }
        });

        return convertView;
    }
    public interface FoodActivityListenerCallback{
        void animClick(Food food,int flag);
    };
    public FoodActivityListenerCallback getListenerCallback() {
        return listenerCallback;
    }

    public void setListenerCallback(FoodActivityListenerCallback listenerCallback) {
        this.listenerCallback = listenerCallback;
    }
    public FoodActivityListenerCallback listenerCallback;

}
