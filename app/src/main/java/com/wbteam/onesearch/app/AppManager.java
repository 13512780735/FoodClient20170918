package com.wbteam.onesearch.app;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 *  Activity管理机制
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-6-1  下午7:25:19
 * @contact:QQ-441293364 TEL-15105695563
 **/
final public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {}
    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
//        return ManagerHolder.instance;
    }

    /**
     * 获取当前Activity栈中元素个数
     */
    public int getCount() {
        return activityStack.size();
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity(栈顶Ativity)
     */
    public Activity getTopActivity() {
        if (activityStack == null) {
            throw new NullPointerException("Activity stack is Null");
        }
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 根据类型获取指定Activity  没有找到则返回null
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                return aty;
            }
        }
        return null;
    }

    /**
     * 根据类名返回指定activity
     *
     * @param name
     * @return
     */
    public Activity getActivity(String name) {
        for (Activity ac : activityStack) {
            if (ac.getClass().getName().contains(name)) {
                return ac;
            }
        }
        return null;
    }

    /**
     * 得到Activity集合
     * @return
     */
    public Stack<Activity> getActivityStack(){
        return activityStack;
    }
    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void removeActivity() {
        finishActivity(activityStack.lastElement());
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 应用程序完全退出
     *
     * @param context 当前上下文
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context
                    .ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
           }
    }
    /**
     * 退出应用
     *
     * @param context
     */
    public void exitSelf(Context context) {
        try {
            appExit(context);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            // 忽略这里的错误，不需要关注系统退出时的错误
        }
    }
}