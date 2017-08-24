package com.wbteam.onesearch.app.weight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**********************************************************
 * @文件名称：CustomScrollView.java
 * @文件作者：rzq
 * @创建时间：2015年7月7日 下午2:20:16
 * @文件描述：滑动隐藏ScrollView
 * @修改历史：2015年7月7日创建初始版本
 **********************************************************/
public class CustomScrollView extends ScrollView {
	/**
	 * UI
	 */
	private View contentView;
	/**
	 * data
	 */
	private ValueAnimator apperaAnim;
	private ValueAnimator hiddenAnim;
	private int downScrollY;
	private int moveScrollY;
	private boolean isHidding;

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			contentView = getChildAt(0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downScrollY = getScrollY();
			break;
		case MotionEvent.ACTION_MOVE:
			moveScrollY = getScrollY();
			if (moveScrollY != downScrollY) {
				startHiddenAnimation();
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			moveScrollY = 0;
			downScrollY = 0;
			break;
		}
		return super.onTouchEvent(ev);
	}

	public void setAnimationView(final View animationView) {
		/**
		 * 创建动画
		 */
		hiddenAnim = ValueAnimator.ofFloat(0, animationView.getHeight());
		hiddenAnim.setDuration(500);
		hiddenAnim.setTarget(animationView);
		hiddenAnim.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				animationView.setTranslationY((Float) animation.getAnimatedValue());
			}
		});
		hiddenAnim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				startApperaAnimation();
			}

			@Override
			public void onAnimationStart(Animator animation) {
				isHidding = true;
			}
		});

		apperaAnim = ValueAnimator.ofFloat(animationView.getHeight(), 0);
		apperaAnim.setDuration(500);
		apperaAnim.setTarget(animationView);
		apperaAnim.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				animationView.setTranslationY((Float) animation.getAnimatedValue());
			}
		});

		apperaAnim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				isHidding = false;
			}

			@Override
			public void onAnimationStart(Animator animation) {
			}
		});
	}

	private void startHiddenAnimation() {
		if (!hiddenAnim.isRunning() && !isHidding) {
			hiddenAnim.start();
		}
	}

	private void startApperaAnimation() {
		if (!apperaAnim.isRunning()) {
			apperaAnim.start();
		}
	}

	/**
	 * 是否直接滑动到底部
	 */
	protected boolean isScrollDown() {
		return getHeight() + getScrollY() == contentView.getHeight();
	}

	/**
	 * 是否直接滑到顶部
	 */
	protected boolean isScrollUp() {
		return getScrollY() == 0;
	}
}
