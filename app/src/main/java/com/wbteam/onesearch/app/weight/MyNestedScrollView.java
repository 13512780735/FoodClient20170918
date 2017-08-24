package com.wbteam.onesearch.app.weight;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author 码农哥
 * @date 2016-12-29下午2:39:29
 * @email 441293364@qq.com
 * @TODO
 * 
 *       ** *** ━━━━━━神兽出没━━━━━━ ** *** ┏┓　　 ┏┓ ** *** ┏┛┻━━━┛┻┓ ** *** 　
 *       ┃　　　　　　　┃ ** *** 　　┃　　　━　　　┃ ** *** 　　┃　┳┛　┗┳　┃ ** *** 　　┃　　　　　　　┃ **
 *       *** 　　┃　　　┻　　　┃ ** *** 　　┃　　　　　　　┃ ** *** 　　┗━┓　　　┏━┛ ** *** 　　　　┃　　　┃
 *       神兽保佑,代码永无bug ** *** 　　　　┃　　　┃ ** *** 　　　　┃　　　┗━━━┓ ** ***
 *       　　　　┃　　　　　　　┣┓ ** *** 　　　　┃　　　　　　　┏┛ ** *** 　　　　┗┓┓┏━┳┓┏┛ ** *** 　　　　
 *       ┃┫┫ ┃┫┫ ** *** 　　　　 ┗┻┛　┗┻┛
 */

public class MyNestedScrollView extends NestedScrollView {

	public MyNestedScrollView(Context context) {
		super(context);
	}

	public MyNestedScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private int yLast = 0;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int action = ev.getAction() & MotionEvent.ACTION_MASK;

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			yLast = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int xCur = (int) ev.getX();
			int yCur = (int) ev.getY();
			int yDiff = yCur - yLast;
			if (canScroll(this, false, yDiff, xCur, yCur)) {
				return false;
			}
			break;
		default:
			yLast = 0;
			break;
		}

		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * 递归检测一个view通过dy是否可滑动
	 * 
	 * @param v
	 * @param checkV
	 *            是否检测
	 * @param dy
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean canScroll(View v, boolean checkV, int dy, int x, int y) {
		if (v instanceof ViewGroup) {
			final ViewGroup group = (ViewGroup) v;
			final int scrollX = v.getScrollX();
			final int scrollY = v.getScrollY();
			final int count = group.getChildCount();
			for (int i = count - 1; i >= 0; i--) {
				final View child = group.getChildAt(i);
				if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() && y + scrollY >= child.getTop() && y + scrollY < child.getBottom()
						&& canScroll(child, true, dy, x + scrollX - child.getLeft(), y + scrollY - child.getTop())) {
					return true;
				}
			}
		}

		return checkV && ViewCompat.canScrollVertically(v, -dy);
	}
}
