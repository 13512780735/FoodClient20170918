package com.wbteam.onesearch.app.weight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wbteam.onesearch.R;

/**
 * Created by george.yang on 16/11/16.
 */
@SuppressLint("NewApi") 
public class DSRefView extends FrameLayout {
    public DSRefView(Context context) {
        super(context);
    }

    public DSRefView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DSRefView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DSRefView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflate(getContext(),R.layout.layout_dsview,this);
        imgScrollToTop = (ImageView) findViewById(R.id.imgScrollToTop);
        imgScrollToTop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slideableView!=null) {
                    ViewUtil.scrollToTop(slideableView);
                }
                if (parentScrollView!=null) {
                    ViewUtil.scrollToTop(parentScrollView);
                }
                v.setVisibility(GONE);
            }
        });
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            parentScrollView = ViewUtil.findView(activity.getWindow().getDecorView(),DoubleScrollView.class);
        } else {
            parentScrollView = ViewUtil.findView(getRootView(),DoubleScrollView.class);
        }
    }

    private View slideableView;
    private ImageView imgScrollToTop;
    public boolean isReleaseTouch () {
        return imgScrollToTop.getVisibility()==GONE;
    }

    public void setSlideableView (View view) {
        slideableView = view;
    }

    private DoubleScrollView parentScrollView;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                imgScrollToTop.setVisibility(VISIBLE);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
