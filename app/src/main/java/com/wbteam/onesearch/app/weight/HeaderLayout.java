package com.wbteam.onesearch.app.weight;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wbteam.onesearch.R;


/**
 * 整个应用标题栏
 * anthor:qz 时间：2015年9月28日09:36:40
 **/
public class HeaderLayout extends RelativeLayout {
	
    public HeaderLayout(Context context) {
        super(context);
        init(context);
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HeaderLayout);
        right_text = (String) array.getText(R.styleable.HeaderLayout_right_text);
        center_text = (String) array.getText(R.styleable.HeaderLayout_center_text);
        left_img = array.getDrawable(R.styleable.HeaderLayout_left_image);
        right_img = array.getDrawable(R.styleable.HeaderLayout_right_image);
        color = array.getColor(R.styleable.HeaderLayout_head_back, 0xFFFFFFFF);
        array.recycle();
        init(context);
    }


    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mHeader = mInflater.inflate(R.layout.common_header, null);
        addView(mHeader);
        initViews();
    }

    private void initViews() {
        linearHeaderRight = (LinearLayout) findViewByHeaderId(R.id.linearRightView);
        titleLeftView = (ImageView) findViewByHeaderId(R.id.titleLeftView);
        titleRightView = (ImageView) findViewByHeaderId(R.id.titleRightView);
        titleCenterView = (TextView) findViewByHeaderId(R.id.titleCenterView);
        titleRightText = (TextView) findViewByHeaderId(R.id.header_htv_righttext);
        relative_root = (RelativeLayout) findViewByHeaderId(R.id.relative_root);
        //左边图片
        if (null != left_img) {
            titleLeftView.setImageDrawable(left_img);
        }
        //中间文字
        if (null != center_text) {
            titleCenterView.setText(center_text);
        }
        //右边文字
        if (null != right_text) {
            titleRightText.setVisibility(View.VISIBLE);
            titleRightText.setText(right_text);
        } else {
            titleRightText.setVisibility(View.GONE);
        }
        //右边图片
        if (null != right_img) {
            titleRightView.setVisibility(View.VISIBLE);
            titleRightView.setImageDrawable(right_img);
        } else {
            titleRightView.setVisibility(View.GONE); 
        }
        
        if(null == right_text && null == right_img){
        	titleRightText.setVisibility(View.INVISIBLE);
        }
        
        if (0xFFFFFFFF != color) {
            relative_root.setBackgroundColor(color);
        }
    }

    public View findViewByHeaderId(int id) {
        return mHeader.findViewById(id);
    }

    public void setMidText(String text) {

        titleCenterView.setText(text);
    }

    public void setLeftImage(int id) {
        titleLeftView.setImageDrawable(getResources().getDrawable(id));
    }

    public void setRightImage(int id) {
    	titleRightView.setVisibility(View.VISIBLE);
    	titleRightText.setVisibility(View.GONE);
        titleRightView.setImageDrawable(getResources().getDrawable(id));
    }

    public void setOnLeftImageViewClickListener(OnLeftClickListener listener) {
        this.mOnLeftClickListener = listener;
        titleLeftView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnLeftClickListener != null) {
                    mOnLeftClickListener.onClick();
                }
            }
        });

    }

    public void setOnMiddleTextViewClickListener(OnMiddleClickListener listener) {
        this.mOnMiddleClickListener = listener;
        titleCenterView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnMiddleClickListener != null) {
                    mOnMiddleClickListener.onClick();
                }
            }
        });
    }

    public void setOnRightImageViewClickListener(OnRightClickListener listener) {
        this.mOnRightClickListener = listener;
        linearHeaderRight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnRightClickListener != null) {
                    mOnRightClickListener.onClick();
                }
            }
        });
    }

    public interface OnLeftClickListener {
        void onClick();
    }

    public interface OnMiddleClickListener {
        void onClick();
    }

    public interface OnRightClickListener {
        void onClick();
    }

    /**
     * 设置左边和中间信息
     *
     * @param leftClick
     * @param titleCenter
     */
    public void setLeftCenter(OnLeftClickListener leftClick, String titleCenter) {
        setLeftClick(leftClick);
        titleCenterView.setText(titleCenter);
    }

    /**
     * 设置左,中(文字)，右(设置)
     *
     * @param leftClick
     * @param titleCenter
     * @param rightClick
     */
    public void setLeftCenterSet(OnLeftClickListener leftClick,
                                 String titleCenter, OnRightClickListener rightClick) {
        // iv_arrow.setVisibility(View.GONE);
        setLeftClick(leftClick);
        titleCenterView.setText(titleCenter);
        setRightClick(rightClick);
    }

    /**
     * 设置左,中(下拉对话框)，右(刷新)
     *
     * @param leftClick
     * @param middleClick
     * @param rightClick
     */
    public void setLeftCenterRight(OnLeftClickListener leftClick,
                                   OnMiddleClickListener middleClick, OnRightClickListener rightClick) {
        setLeftClick(leftClick);
        setMiddleClick(middleClick);
        setRightClick(rightClick);
    }

    /**
     * 设置左,中，右(刷新)
     *
     * @param leftClick
     * @param titleCenter
     * @param rightClick
     */
    public void setLeftCenterRight(OnLeftClickListener leftClick,
                                   String titleCenter, OnRightClickListener rightClick) {
        titleCenterView.setText(titleCenter);
        setLeftClick(leftClick);
        setRightClick(rightClick);
    }

    /**
     * 设置左，中，右（文字信息）
     *
     * @param leftClick
     * @param titleCenter
     * @param rightText
     */
    public void setLeftCenterRight(OnLeftClickListener leftClick,
                                   String titleCenter, String rightText) {
        titleCenterView.setText(titleCenter);
        setLeftClick(leftClick);
        titleRightText.setText(rightText);
    }

    private void setLeftClick(OnLeftClickListener leftClick) {
        mOnLeftClickListener = leftClick;
        titleLeftView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnLeftClickListener != null) {
                    mOnLeftClickListener.onClick();
                }
            }
        });
    }

    private void setMiddleClick(OnMiddleClickListener middleClick) {
        mOnMiddleClickListener = middleClick;
        titleCenterView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnMiddleClickListener != null) {
                    mOnMiddleClickListener.onClick();
                }
            }
        });
    }

    private void setRightClick(OnRightClickListener rightClick) {
        mOnRightClickListener = rightClick;
        linearHeaderRight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnRightClickListener != null) {
                    mOnRightClickListener.onClick();
                }
            }
        });
    }
    
    private LayoutInflater mInflater;
    private View mHeader;
    private LinearLayout linearHeaderRight;
    private ImageView titleLeftView, titleRightView;
    private TextView titleCenterView;
    private TextView titleRightText;

    private OnLeftClickListener mOnLeftClickListener;
    private OnMiddleClickListener mOnMiddleClickListener;
    private OnRightClickListener mOnRightClickListener;

    private String right_text;
    private String center_text;
    private Drawable left_img;
    private Drawable right_img;
    private RelativeLayout relative_root;
    private int color;


}
