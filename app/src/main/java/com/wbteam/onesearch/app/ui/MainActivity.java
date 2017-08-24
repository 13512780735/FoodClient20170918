package com.wbteam.onesearch.app.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.wbteam.app.base.BaseActivity;
import com.wbteam.ioc.annotation.ContentView;
import com.wbteam.ioc.annotation.ViewInject;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.fragment.Index01Fragment;
import com.wbteam.onesearch.app.fragment.Index02Fragment;
import com.wbteam.onesearch.app.fragment.Index03Fragment;
import com.wbteam.onesearch.app.fragment.Index04Fragment;
import com.wbteam.onesearch.app.fragment.Index05Fragment;
import com.wbteam.onesearch.app.utils.AppHelper;
import com.wbteam.onesearch.app.utils.AppHelper.onCheckVersionListener;
import com.wbteam.onesearch.app.utils.AppUtils;
import com.wbteam.onesearch.app.utils.DialogUtils;
import com.wbteam.onesearch.app.utils.DownLoadAppTask;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.weight.TabWidget;
import com.wbteam.onesearch.app.weight.TabWidget.OnTabSelectedListener;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnTabSelectedListener{
	
	//private final static String TAG = MainActivity.class.getSimpleName();
	
	public static final int index_1 = 0, index_2 = 1, index_3 = 2, index_4 = 3, index_5 = 4;

	private AppExitHelper mAppExitHelper;
	
	@ViewInject(R.id.tab_widget)
	private TabWidget mTabWidget;
	
	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;
	private Index01Fragment index01Fragment;
	private Index02Fragment index02Fragment;
	private Index03Fragment index03Fragment;
	private Index04Fragment index04Fragment;
	private Index05Fragment index05Fragment;
	private int index = 0;
    private long firstTime = 0;
	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		mTabWidget.setOnTabSelectedListener(this);
		fragmentManager = getSupportFragmentManager();
		mAppExitHelper = new AppExitHelper(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		onTabSelected(index);
		mTabWidget.setTabsDisplay(this, index);
		if (!NetUtils.isOnline()) {
			AppUtils.showToast(MainActivity.this, "请您检查网络是否连接");
		}else{
			AppHelper.checkAppVersion(MainActivity.this, new onCheckVersionListener() {

				@Override
				public void onTargetVersionListener() {
					DialogUtils.dismiss();
					//ToastUtils.showToast(MainActivity.this, "当前版本为最新版本");
				}

				@Override
				public void onNeedUpdateListener(final String url, String log) {
					DialogUtils.dismiss();
					DialogUtils.showVersionUpdate(MainActivity.this, log, new OnClickListener() {

						@Override
						public void onClick(View v) {
							new DownLoadAppTask(MainActivity.this).execute(url);
						}
					});
				}
			});
		}
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initIntent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(int index) {
		transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case index_1://第一个fragment
                if (null == index01Fragment) {
                	index01Fragment = new Index01Fragment();
                    transaction.add(R.id.center_layout, index01Fragment, "indexfragment");
                } else {
                    transaction.show(index01Fragment);
                }
                break;
            case index_2://第二个fragment
                if (null == index02Fragment) {
                	index02Fragment = new Index02Fragment();
                    transaction.add(R.id.center_layout, index02Fragment);
                } else {
                    transaction.show(index02Fragment);
                }
                break;
            case index_3://第三个fragment
                if (null == index03Fragment) {
                	index03Fragment = new Index03Fragment();
                    transaction.add(R.id.center_layout, index03Fragment);
                } else {
                    transaction.show(index03Fragment);
                }
                break;
            case index_4://第四个fragment
                if (null == index04Fragment) {
                	index04Fragment = new Index04Fragment();
                    transaction.add(R.id.center_layout, index04Fragment);
                } else {
                    transaction.show(index04Fragment);
                }
                break;
                
            case index_5://第四个fragment
                if (null == index05Fragment) {
                	index05Fragment = new Index05Fragment();
                    transaction.add(R.id.center_layout, index05Fragment);
                } else {
                    transaction.show(index05Fragment);
                }
                break;
        }
        this.index = index;
        transaction.commitAllowingStateLoss();
	}
	
	private void hideFragments(FragmentTransaction transaction) {

        if (null != index01Fragment) {
            transaction.hide(index01Fragment);
        }
        if (null != index02Fragment) {
            transaction.hide(index02Fragment);
        }
        if (null != index03Fragment) {
            transaction.hide(index03Fragment);
        }
        if (null != index04Fragment) {
            transaction.hide(index04Fragment);
        }
        if (null != index05Fragment) {
            transaction.hide(index05Fragment);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    
    /**
     * 二次点击退出应用程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mAppExitHelper.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 自己记录fragment的位置,防止activity被系统回收时，fragment错乱的问题
        // super.onSaveInstanceState(outState);
        outState.putInt("index", index);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // super.onRestoreInstanceState(savedInstanceState);
        index = savedInstanceState.getInt("index");
    }

}