package com.food.ty.tyfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.food.ty.tyfood.R;
import com.food.ty.tyfood.model.Shop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.attr.name;

import com.squareup.picasso.Picasso;

/**
 * Created by ty on 2017/3/6.
 */

public class ShopListAdapter extends BaseAdapter {
    private List<Shop> mListData;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private int mResource;
    private String shopName;
    private String shopInfo;
    private String shopImage;
    static class ViewHolder {
        private TextView idText;
        private TextView nameText;
        private TextView infoText;
        private ImageView photoImage;
    }
    public ShopListAdapter(Context context, List<Shop> listData,
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
            mViewHolder.idText=(TextView) convertView.
                    findViewById(R.id.tv_shop_id);
            mViewHolder.photoImage= (ImageView) convertView.
                    findViewById(R.id.iv_shopImage);
            mViewHolder.nameText=(TextView) convertView
                    .findViewById(R.id.tv_shop_name);
            mViewHolder.infoText=(TextView)convertView
                    .findViewById(R.id.tv_shop_info);
            convertView.setTag(mViewHolder);

        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(mListData.get(position).shopImage)
                .into(mViewHolder.photoImage);
        mViewHolder.idText.setText(mListData.get(position).shopid);
        mViewHolder.nameText.setText(mListData.get(position).shopName);
        mViewHolder.infoText.setText(mListData.get(position).shopInfo);

        return convertView;
    }


}
