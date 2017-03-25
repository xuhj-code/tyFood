package com.food.ty.tyfood.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.food.ty.tyfood.R;
import com.food.ty.tyfood.activity.MainActivity;

/**
 * Created by ty on 2017/3/2.
 */

public class OneFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        // 当Fragment重建之后向Activity返回新的Fragment
        ((MainActivity) getActivity()).refreshOneFragment(this);
    }
}
