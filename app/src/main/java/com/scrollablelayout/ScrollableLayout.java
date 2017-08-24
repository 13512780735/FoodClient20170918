//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.scrollablelayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.Scroller;
import com.scrollablelayout.ScrollableHelper;

public class ScrollableLayout extends LinearLayout {
    private Context context;
    private Scroller mScroller;
    private float mDownX;
    private float mDownY;
    private float mLastX;
    private float mLastY;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private boolean mIsHorizontalScrolling;
    private float x_down;
    private float y_down;
    private float x_move;
    private float y_move;
    private float moveDistanceX;
    private float moveDistanceY;
    private View mHeadView;
    private ViewPager childViewPager;
    private DIRECTION mDirection;
    private int mHeadHeight;
    private int mScrollY;
    private int sysVersion;
    private boolean flag1;
    private boolean flag2;
    private int mLastScrollerY;
    private boolean mDisallowIntercept;
    private int minY;
    private int maxY;
    private int mCurY;
    private boolean isClickHead;
    private int mScrollMinY;
    private OnScrollListener onScrollListener;
    private ScrollableHelper mHelper;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public ScrollableHelper getHelper() {
        return this.mHelper;
    }

    public ScrollableLayout(Context context) {
        this(context, (AttributeSet)null);
    }

    public ScrollableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.minY = 0;
        this.maxY = 0;
        this.mScrollMinY = 10;
        this.context = context;
        this.mHelper = new ScrollableHelper();
        this.mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.sysVersion = VERSION.SDK_INT;
        this.setOrientation(1);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mHeadView = this.getChildAt(0);
        if(this.mHeadView != null) {
            this.measureChildWithMargins(this.mHeadView, widthMeasureSpec, 0, 0, 0);
            this.maxY = this.mHeadView.getMeasuredHeight();
            this.mHeadHeight = this.mHeadView.getMeasuredHeight();
        }

        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec) + this.maxY, 1073741824));
    }

    protected void onFinishInflate() {
        if(this.mHeadView != null && !this.mHeadView.isClickable()) {
            this.mHeadView.setClickable(true);
        }

        int childCount = this.getChildCount();

        for(int i = 0; i < childCount; ++i) {
            View childAt = this.getChildAt(i);
            if(childAt != null && childAt instanceof ViewPager) {
                this.childViewPager = (ViewPager)childAt;
            }
        }

        super.onFinishInflate();
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        float currentX = ev.getX();
        float currentY = ev.getY();
        int shiftX = (int)Math.abs(currentX - this.mDownX);
        int shiftY = (int)Math.abs(currentY - this.mDownY);
        switch(ev.getAction()) {
        case 0:
            this.mDisallowIntercept = false;
            this.mIsHorizontalScrolling = false;
            this.x_down = ev.getRawX();
            this.y_down = ev.getRawY();
            this.flag1 = true;
            this.flag2 = true;
            this.mDownX = currentX;
            this.mDownY = currentY;
            this.mLastX = currentX;
            this.mLastY = currentY;
            this.mScrollY = this.getScrollY();
            this.checkIsClickHead((int)currentY, this.mHeadHeight, this.getScrollY());
            this.initOrResetVelocityTracker();
            this.mVelocityTracker.addMovement(ev);
            this.mScroller.forceFinished(true);
            break;
        case 1:
            if(!this.flag2 || shiftY <= shiftX || shiftY <= this.mTouchSlop) {
                break;
            }

            this.mVelocityTracker.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
            float action1 = -this.mVelocityTracker.getYVelocity();
            if(Math.abs(action1) > (float)this.mMinimumVelocity) {
                this.mDirection = action1 > 0.0F? DIRECTION.UP: DIRECTION.DOWN;
                if(this.mDirection != DIRECTION.UP || !this.isSticked()) {
                    this.mScroller.fling(0, this.getScrollY(), 0, (int)action1, 0, 0, -2147483647, 2147483647);
                    this.mScroller.computeScrollOffset();
                    this.mLastScrollerY = this.getScrollY();
                    this.invalidate();
                }
            }

            if(this.isClickHead || !this.isSticked()) {
                int dd2 = ev.getAction();
                ev.setAction(3);
                boolean dd1 = super.dispatchTouchEvent(ev);
                ev.setAction(dd2);
                return dd1;
            }
            break;
        case 2:
            if(!this.mDisallowIntercept) {
                this.initVelocityTrackerIfNotExists();
                this.mVelocityTracker.addMovement(ev);
                float deltaY = this.mLastY - currentY;
                if(this.flag1) {
                    if(shiftX > this.mTouchSlop && shiftX > shiftY) {
                        this.flag1 = false;
                        this.flag2 = false;
                    } else if(shiftY > this.mTouchSlop && shiftY > shiftX) {
                        this.flag1 = false;
                        this.flag2 = true;
                    }
                }

                if(this.flag2 && shiftY > this.mTouchSlop && shiftY > shiftX && (!this.isSticked() || this.mHelper.isTop())) {
                    if(this.childViewPager != null) {
                        this.childViewPager.requestDisallowInterceptTouchEvent(true);
                    }

                    this.scrollBy(0, (int)((double)deltaY + 0.5D));
                }

                this.mLastX = currentX;
                this.mLastY = currentY;
                this.x_move = ev.getRawX();
                this.y_move = ev.getRawY();
                this.moveDistanceX = (float)((int)(this.x_move - this.x_down));
                this.moveDistanceY = (float)((int)(this.y_move - this.y_down));
                if(Math.abs(this.moveDistanceY) > (float)this.mScrollMinY && (double)Math.abs(this.moveDistanceY) * 0.1D > (double)Math.abs(this.moveDistanceX)) {
                    this.mIsHorizontalScrolling = false;
                } else {
                    this.mIsHorizontalScrolling = true;
                }
            }
            break;
        case 3:
            if(this.flag2 && this.isClickHead && (shiftX > this.mTouchSlop || shiftY > this.mTouchSlop)) {
                int action = ev.getAction();
                ev.setAction(3);
                boolean dd = super.dispatchTouchEvent(ev);
                ev.setAction(action);
                return dd;
            }
        }

        super.dispatchTouchEvent(ev);
        return true;
    }

    @TargetApi(14)
    private int getScrollerVelocity(int distance, int duration) {
        return this.mScroller == null?0:(this.sysVersion >= 14?(int)this.mScroller.getCurrVelocity():distance / duration);
    }

    public void computeScroll() {
        if(this.mScroller.computeScrollOffset()) {
            int currY = this.mScroller.getCurrY();
            int deltaY;
            int toY;
            if(this.mDirection == DIRECTION.UP) {
                if(this.isSticked()) {
                    deltaY = this.mScroller.getFinalY() - currY;
                    toY = this.calcDuration(this.mScroller.getDuration(), this.mScroller.timePassed());
                    this.mHelper.smoothScrollBy(this.getScrollerVelocity(deltaY, toY), deltaY, toY);
                    this.mScroller.forceFinished(true);
                    return;
                }

                this.scrollTo(0, currY);
            } else {
                if(this.mHelper.isTop()) {
                    deltaY = currY - this.mLastScrollerY;
                    toY = this.getScrollY() + deltaY;
                    this.scrollTo(0, toY);
                    if(this.mCurY <= this.minY) {
                        this.mScroller.forceFinished(true);
                        return;
                    }
                }

                this.invalidate();
            }

            this.mLastScrollerY = currY;
        }

    }

    public void scrollBy(int x, int y) {
        int scrollY = this.getScrollY();
        int toY = scrollY + y;
        if(toY >= this.maxY) {
            toY = this.maxY;
        } else if(toY <= this.minY) {
            toY = this.minY;
        }

        y = toY - scrollY;
        super.scrollBy(x, y);
    }

    public void scrollTo(int x, int y) {
        if(y >= this.maxY) {
            y = this.maxY;
        } else if(y <= this.minY) {
            y = this.minY;
        }

        this.mCurY = y;
        if(this.onScrollListener != null) {
            this.onScrollListener.onScroll(y, this.maxY);
        }

        super.scrollTo(x, y);
    }

    private void initOrResetVelocityTracker() {
        if(this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }

    }

    private void initVelocityTrackerIfNotExists() {
        if(this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }

    }

    private void recycleVelocityTracker() {
        if(this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }

    }

    private void checkIsClickHead(int downY, int headHeight, int scrollY) {
        this.isClickHead = downY + scrollY <= headHeight;
    }

    private int calcDuration(int duration, int timepass) {
        return duration - timepass;
    }

    public void requestScrollableLayoutDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        this.mDisallowIntercept = disallowIntercept;
    }

    public boolean isSticked() {
        return this.mCurY == this.maxY;
    }

    public int getMaxY() {
        return this.maxY;
    }

    public void setScrollMinY(int y) {
        this.mScrollMinY = y;
    }

    public boolean isCanPullToRefresh() {
        return this.getScrollY() <= 0 && this.mHelper.isTop() && !this.mIsHorizontalScrolling;
    }

    public interface OnScrollListener {
        void onScroll(int var1, int var2);
    }

    static enum DIRECTION {
        UP,
        DOWN;

        private DIRECTION() {
        }
    }
}
