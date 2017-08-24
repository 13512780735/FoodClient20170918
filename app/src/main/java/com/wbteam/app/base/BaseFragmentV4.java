package com.wbteam.app.base;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 碎片基类  support V4的碎片
 * @author findchen
 * TODO
 * 2013-6-18下午1:20:00
 */
public abstract class BaseFragmentV4 extends Fragment {
    /**
     * 当前activity
     */
    public Activity fatherActivity;
    private static final String TAG = BaseFragmentV4.class.getSimpleName();
    
    public abstract View getSlidableView();
    
    /**
     * 当前视图
     */
    public View currentView;
    protected ProgressDialog dialog;
    
    /**
     * 当前碎片是否可见
     */
    protected boolean isVisible;
    
    @SuppressWarnings("deprecation")
	@Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        this.fatherActivity = getActivity();
        Log.i(TAG, "Activity life cycle onAttach " + getClass().getSimpleName());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.i(TAG,"Activity life cycle onCreateView "+getClass().getSimpleName());

        currentView = setContentView(inflater, container,
                savedInstanceState);
        initView(currentView);
        initProgress();
        initData();
        initListener();
        return currentView;
    }

    public abstract View setContentView(LayoutInflater inflater, ViewGroup container,
                                        Bundle savedInstanceState);
    public abstract void initView(View currentView);
    public abstract void initData();
    public abstract void initListener();
    public void startHasHeadActivity(Intent intent){

        startActivity(intent);
//        String value = XmlDB.getInstance(fatherActivity).getKeyString(Constant.access_token,"0");
//        if("0".equals(value)){
//            intent.setClass(fatherActivity, LoginActivity.class);
//        }
//        startActivity(intent);
    }

    public void startNoHeadActivity(Intent intent){
        startActivity(intent);
    }

    private void initProgress(){
        dialog = new ProgressDialog(fatherActivity);
    }
    /**
     * 此方法目前仅适用于标示ViewPager中的Fragment是否真实可见
     * For 友盟统计的页面线性不交叉统计需求
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {


        if (isCreated) {
            return;
        }

        if (isVisibleToUser) {
//            umengPageStart();
        }else {
//            umengPageEnd();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
    protected boolean isCreated = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Activity life cycle onCreate " + getClass().getSimpleName());
        // ...
        isCreated = true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "Activity life cycle onActivityCreated " + getClass().getSimpleName());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "Activity life cycle onStart " + getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "Activity life cycle onPause " + getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "Activity life cycle onResume " + getClass().getSimpleName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "Activity life cycle onDestroyView " + getClass().getSimpleName());
    }

    @SuppressWarnings("unchecked")
	public <T> T  getView(int resourceId) {
        return (T) currentView.findViewById(resourceId);
    }
}
