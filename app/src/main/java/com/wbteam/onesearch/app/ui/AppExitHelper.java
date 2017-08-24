package com.wbteam.onesearch.app.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;

import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppManager;
import com.wbteam.onesearch.app.utils.AppUtils;


/**
 * 连续点击返回按钮时（2秒之内），退出应用的辅助类
 *
 * @author zhangbin
 */
public class AppExitHelper {
    private static final int DELAY_MILLIS = 2000;

    private Context mContext;

    private Handler mHandler;


    private boolean isKeyDownHandling = false;

    private Runnable timeJob = new Runnable() {
        @Override
        public void run() {
            isKeyDownHandling = false;
        }
    };

    public AppExitHelper(Context ctx) {
        this.mContext = ctx;
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if (isKeyDownHandling) {
            mHandler.removeCallbacks(timeJob);
            if (AppUtils.tipsToast != null) {
                AppUtils.tipsToast.cancel();
            }
            // AppManager.getInstance().appExit(mContext);
            //AppManager.getInstance().exitSelf(mContext);
            AppManager.getInstance().finishAllActivity();
        } else {
            isKeyDownHandling = true;
            AppUtils.showToast(mContext, mContext.getString(R.string.exit_hint));

            mHandler.postDelayed(timeJob, DELAY_MILLIS);
        }

        return true;
    }
}
