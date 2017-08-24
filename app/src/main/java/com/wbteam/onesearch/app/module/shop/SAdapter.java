package com.wbteam.onesearch.app.module.shop;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.wbteam.app.base.BaseFragmentV4;

/**
 * Created by yangsp on 2016/11/14.
 */
public class SAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragmentV4> list;
    private static final String[] titles = new String[]{"简介","必食推介","优惠","餐牌"};
    
    public SAdapter(FragmentManager fm,String id) {
        super(fm);
        list = new ArrayList<>();
        list.add(new ShopFragmentIndex01(id));
        list.add(new ShopFragmentIndex02(id));
        list.add(new ShopFragmentIndex03(id));
        list.add(new ShopFragmentIndex04(id));
    }

    public View getSlidableView (int index) {
        return list.get(index).getSlidableView();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
