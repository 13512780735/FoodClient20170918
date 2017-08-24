package com.wbteam.onesearch.app.module.shoptype;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.model.StyleInfo;

/**
 *  
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-13  下午8:34:08
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class ShopStyleItem implements OnPageChangeListener {

    private ViewPager vpAdvertise;
    private Context context;
    private LayoutInflater inflater;

    List<View> views;
    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    int count = 0;

    public ShopStyleItem(Context context, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
    }

    public View initView(final List<StyleInfo> styleInfos) {
        View view = inflater.inflate(R.layout.advertisement_board, null);
        vpAdvertise = (ViewPager) view.findViewById(R.id.vpAdvertise);
        vpAdvertise.setOnPageChangeListener(this);
        views = new ArrayList<View>();
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);//获取轮播图片的点的parent，用于动态添加要显示的点
		int len = 0;
		if (styleInfos.size() % 5 == 0) {
			len = styleInfos.size() / 5;
		} else {
			len = styleInfos.size() / 5 + 1;
		}
        
        for (int i = 0; i < len; i++) {
            views.add(inflater.inflate(R.layout.item_styleinfo_layout, null));
            ll.addView(inflater.inflate(R.layout.advertisement_board_dot, null));
        }
        if (len == 1) {
            ll.setVisibility(View.GONE);
        }
        initDots(view, ll);

        ShopStyleItemAdapter adapter = new ShopStyleItemAdapter(context, views, styleInfos);
        vpAdvertise.setOffscreenPageLimit(3);
        vpAdvertise.setAdapter(adapter);
        return view;
    }


    private void initDots(View view, LinearLayout ll) {


        dots = new ImageView[views.size()];

        // 循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为黄色，即选中状态
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        count = position;
        setCurrentDot(position);
    }

}
