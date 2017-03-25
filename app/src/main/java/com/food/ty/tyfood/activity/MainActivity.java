package com.food.ty.tyfood.activity;

/**
 * Created by ty on 2017/3/2.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.food.ty.tyfood.R;
import com.food.ty.tyfood.fragment.AccountFragment;
import com.food.ty.tyfood.fragment.MainFragment;
import com.food.ty.tyfood.fragment.OneFragment;
import com.food.ty.tyfood.fragment.OrderFragment;


public class MainActivity extends FragmentActivity {

    private MainFragment mainFragment = new MainFragment();
    private OneFragment oneFragment = new OneFragment();
    private OrderFragment twoFragment = new OrderFragment();
    private AccountFragment accountFragment = new AccountFragment();



    private Fragment[] fragmentList = {mainFragment, oneFragment,
            twoFragment, accountFragment};
    // fragment是否重建的标志，默认false
    private boolean[] fragmentRecreate = new boolean[4];
    private int[] imageviewId;
    private int[] imageGreyRes;
    private int[] imageBlackRes;
    private int[] textviewId;
    private int currentFragment = 0;
    public LinearLayout navigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navigationBar = (LinearLayout) findViewById(R.id.main_menu);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mainFragment).commit();
        } else {
            fragmentRecreate = savedInstanceState
                    .getBooleanArray("fragmentRecreate");
        }
        currentFragment = 0;

        ((ImageView) findViewById(R.id.iv_menu_main))
                .setImageResource(R.drawable.ic_timeline_pressed);
        ((TextView) findViewById(R.id.tv_menu_main))
                .setTextColor(getResources().getColor(R.color.black));

        imageviewId = new int[]{R.id.iv_menu_main, R.id.iv_navi_circle,
                R.id.iv_navi_order, R.id.iv_navi_me};

        imageGreyRes = new int[]{R.drawable.ic_timeline,
                R.drawable.ic_circle, R.drawable.ic_order,
                R.drawable.ic_account};

        imageBlackRes = new int[]{R.drawable.ic_timeline_pressed,
                R.drawable.ic_circle_pressed, R.drawable.ic_order_pressed,
                R.drawable.ic_account_pressed};

        textviewId = new int[]{R.id.tv_menu_main, R.id.tv_navi_circle,
                R.id.tv_navi_order, R.id.tv_navi_me};


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putBooleanArray("fragmentRecreate", fragmentRecreate);
    }

    public int getCurrentFragment() {
        return currentFragment;
    }

    /**
     * fragment重建之后重新给fragment列表赋值
     *
     * @param context
     */
    public void refreshMainFragment(MainFragment context) {
        mainFragment = context;
        fragmentList[0] = mainFragment;
    }

    /**
     * fragment重建之后重新给fragment列表赋值
     * 此方法在fragment的onCreate方法中调用，第一次之后的调用认为是fragment重建
     *
     * @param context
     */
    public void refreshOneFragment(OneFragment context) {
        if (fragmentRecreate[1]) {
            oneFragment = context;
            fragmentList[1] = oneFragment;
            getSupportFragmentManager().beginTransaction().hide(oneFragment)
                    .commit();
        }
        fragmentRecreate[1] = true;
    }
    public void refreshTwoFragment(OrderFragment context) {
        if (fragmentRecreate[2]) {
            twoFragment = context;
            fragmentList[2] = twoFragment;
            getSupportFragmentManager().beginTransaction()
                    .hide(twoFragment).commit();
        }
        fragmentRecreate[2] = true;
    }
    public void refreshAccountFragment(AccountFragment context) {
        if (fragmentRecreate[3]) {
            accountFragment = context;
            fragmentList[3] = accountFragment;
            getSupportFragmentManager().beginTransaction()
                    .hide(accountFragment).commit();
        }
        fragmentRecreate[3] = true;
    }
    /**
     * 主页按钮
     *
     * @param v
     */
    public void timeline(View v) {
        switchFragment(0);
    }

    /**
     * 做菜按钮
     *
     * @param v
     */
    public void circle(View v) {
        switchFragment(1);
    }

    /**
     * 订单按钮
     *
     * @param v
     */
    public void order(View v) {
        switchFragment(2);
    }

    /**
     * 我的按钮
     *
     * @param v
     */
    public void account(View v) {
        switchFragment(3);
    }

    /**
     * 切换四个Fragment
     *
     * @param index 序号分别为0,1,2,3
     */
    private void switchFragment(int index) {
        if (currentFragment == index)
            return;
        // 切换Fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(fragmentList[currentFragment]);
        if (fragmentList[index].isAdded()) {
            ft.show(fragmentList[index]).commit();
        } else {
            ft.add(R.id.container, fragmentList[index]).commit();

        }
        // 修改导航按钮状态
        ((ImageView) findViewById(imageviewId[currentFragment]))
                .setImageResource(imageGreyRes[currentFragment]);
        ((ImageView) findViewById(imageviewId[index]))
                .setImageResource(imageBlackRes[index]);

        ((TextView) findViewById(textviewId[currentFragment]))
                .setTextColor(getResources().getColor(R.color.dark_grey));
        ((TextView) findViewById(textviewId[index]))
                .setTextColor(getResources().getColor(R.color.black));

        currentFragment = index;
    }


}