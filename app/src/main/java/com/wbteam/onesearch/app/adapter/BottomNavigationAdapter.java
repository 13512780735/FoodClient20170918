package com.wbteam.onesearch.app.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * 
 * @author zhangbin
 *
 */
public class BottomNavigationAdapter extends PagerAdapter
{
	private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private List<Fragment> mFragmentList ;

    public BottomNavigationAdapter(FragmentManager fm,List<Fragment> list) 
    {
        mFragmentManager = fm;
        this.mFragmentList = list;
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    public  Fragment getItem(int position)
    {
    	
    	if (mFragmentList!=null&&mFragmentList.size()>0) 
    	{
			return mFragmentList.get(position);
		}
    	return null;
    };

    @Override
    public void startUpdate(View container) 
    {
    }
    public Fragment getItemAt(int position) {
        return mFragmentList.get(position);
    }
    @Override
    public Object instantiateItem(View container, int position) 
    {
        if (mCurTransaction == null)
        {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        // Do we already have this fragment?
        String name = makeFragmentName(container.getId(), position);
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null)
        {
            mCurTransaction.show(fragment);
        } 
        else 
        {
            fragment = getItem(position);
            mCurTransaction.add(container.getId(), fragment,makeFragmentName(container.getId(), position));
        }
        if (fragment != mCurrentPrimaryItem) 
        {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }

        return fragment;
    }

    @Override
    public void destroyItem(View container, int position, Object object) 
    {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        Fragment fragment = (Fragment) object;
        fragment.setUserVisibleHint(false);
        mCurTransaction.hide(fragment);
    }

    @Override
    public void setPrimaryItem(View container, int position, Object object)
    {
        Fragment fragment = (Fragment)object;
        if (fragment != mCurrentPrimaryItem) 
        {
            if (mCurrentPrimaryItem != null) 
            {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) 
            {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(View container)
    {
        if (mCurTransaction != null)
        {
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) 
    {
        return ((Fragment)object).getView() == view;
    }

    @Override
    public Parcelable saveState() 
    {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) 
    {
    }

    private static String makeFragmentName(int viewId, int index)
    {
        return "android:switcher:" + viewId + ":" + index;
    }

	@Override
	public int getCount()
	{
		if (mFragmentList!=null) 
		{
			return mFragmentList.size();
		}
		return 0;
	}

}
