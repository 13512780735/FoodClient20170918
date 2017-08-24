package com.wbteam.onesearch.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wbteam.app.base.BaseFragmentV4;
import com.wbteam.app.base.interf.OnTabReselectListener;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.adapter.ViewPageFragmentAdapter;
import com.wbteam.onesearch.app.ui.RecommendFragment;
import com.wbteam.onesearch.app.weight.PagerSlidingTabStrip;

/**
 *  @author 码农哥
 *	@date 2016-3-28下午3:43:33
 *	@email 441293364@qq.com
 *	@TODO 主界面(发现)
 *
 *  ** *** ━━━━━━神兽出没━━━━━━
 *  ** ***       ┏┓　　  ┏┓
 *  ** *** 	   ┏┛┻━━━┛┻┓
 *  ** *** 　  ┃　　　　　　　┃
 *  ** *** 　　┃　　　━　　　┃
 *  ** *** 　　┃　┳┛　┗┳　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┃　　　┻　　　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┗━┓　　　┏━┛
 *  ** *** 　　　　┃　　　┃ 神兽保佑,代码永无bug
 *  ** *** 　　　　┃　　　┃
 *  ** *** 　　　　┃　　　┗━━━┓
 *  ** *** 　　　　┃　　　　　　　┣┓
 *  ** *** 　　　　┃　　　　　　　┏┛
 *  ** *** 　　　　┗┓┓┏━┳┓┏┛
 *  ** *** 　　　　  ┃┫┫  ┃┫┫
 *  ** *** 　　　　  ┗┻┛　┗┻┛
 */

public class Index02Fragment extends BaseFragmentV4 implements OnTabReselectListener {
	
	public static final String BUNDLE_KEY_TYPE = "BUNDLE_KEY_TYPE";

	public final static int TYPE_HAVE_COMMENT = 0;
	public final static int TYPE_WAIT_COMMENT = 1;
	
	protected PagerSlidingTabStrip mTabStrip;
	protected ViewPager mViewPager;
	protected ViewPageFragmentAdapter mTabsAdapter;
	
	@Override
	public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_index_02, container, false);
	}

	@Override
	public void initView(View currentView) {
		mTabStrip = (PagerSlidingTabStrip) currentView.findViewById(R.id.pager_tabstrip);

		mViewPager = (ViewPager) currentView.findViewById(R.id.pager);

		mTabsAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(), mTabStrip, mViewPager);
		setScreenPageLimit();
		onSetupTabAdapter(mTabsAdapter);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
	}
	
	protected void setScreenPageLimit() {
		mViewPager.setOffscreenPageLimit(2);
	}

	protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
		 String[] title =getResources().getStringArray(R.array.array_recommend);
		 adapter.addTab(title[0], "now_recommend",RecommendFragment.class, getBundle(TYPE_HAVE_COMMENT));
		 adapter.addTab(title[1], "past_recommend",RecommendFragment.class, getBundle(TYPE_WAIT_COMMENT));
	}
	
	private Bundle getBundle(int newType) {
		Bundle bundle = new Bundle();
		bundle.putInt(BUNDLE_KEY_TYPE, newType);
		return bundle;
	}

	@Override
	public void onTabReselect() {
		try {
			int currentIndex = mViewPager.getCurrentItem();
			Fragment currentFragment = getChildFragmentManager().getFragments().get(currentIndex);
			if (currentFragment != null && currentFragment instanceof OnTabReselectListener) {
				OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
				listener.onTabReselect();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public View getSlidableView() {
		// TODO Auto-generated method stub
		return null;
	}

}
