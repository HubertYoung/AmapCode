package com.alipay.mobile.framework.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.alipay.mobile.aspect.FrameworkPointcutCall;
import com.alipay.mobile.aspect.FrameworkPointcutExecution;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.framework.app.monitor.MemoryMonitor;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ActivityApplication extends MicroApplication {
    static final String TAG = ActivityApplication.class.getSimpleName();
    private Stack<WeakReference<Activity>> a = new Stack<>();
    private Bundle b;
    private AtomicBoolean c = new AtomicBoolean(false);
    private AtomicBoolean d = new AtomicBoolean(false);

    public final void create(Bundle params) {
        this.c.set(false);
        this.d.set(true);
        this.b = params;
        TraceLogger.d(TAG, getClass().getSimpleName() + ": " + getAppId() + "  create.");
        onCreate(params);
    }

    public boolean isCreated() {
        return this.d.get();
    }

    public final void start() {
        MemoryMonitor.getInstance(null).record(getAppId(), "app.start");
        if (this.mDes != null) {
            String engineType = this.mDes.getEngineType();
            if (!TextUtils.isEmpty(engineType)) {
                MemoryMonitor.getInstance(null).putExternalParams(getAppId(), null, "engineType", engineType);
            }
        }
        String className = getEntryClassName();
        if (className != null) {
            try {
                getMicroApplicationContext().startActivity((MicroApplication) this, className);
            } catch (ActivityNotFoundException e) {
                throw new AppLoadException((Throwable) e);
            }
        }
        TraceLogger.d(TAG, getClass().getSimpleName() + ": " + getAppId() + "  start.");
        onStart();
    }

    public final void restart(Bundle params) {
        TraceLogger.d(TAG, getClass().getSimpleName() + ": " + getAppId() + "  restart.");
        onRestart(params);
    }

    public final void stop() {
        TraceLogger.d(TAG, getClass().getSimpleName() + ": " + getAppId() + "  stop.");
        onStop();
        MemoryMonitor.getInstance(null).record(getAppId(), "app.stop");
    }

    public final synchronized void destroy(Bundle params) {
        TraceLogger.d(TAG, getClass().getSimpleName() + ": " + getAppId() + "  destroy.");
        while (!this.a.isEmpty()) {
            WeakReference reference = this.a.pop();
            if (reference == null) {
                break;
            }
            Activity activity = (Activity) reference.get();
            if (activity != null && !activity.isFinishing()) {
                String pointCut = null;
                if (activity instanceof BaseActivity) {
                    pointCut = PointCutConstants.BASEACTIVITY_FINISH;
                } else if (activity instanceof BaseFragmentActivity) {
                    pointCut = PointCutConstants.BASEFRAGMENTACTIVITY_FINISH;
                }
                Object[] args = new Object[0];
                FrameworkPointcutCall.onCallBefore(pointCut, activity, args);
                Pair aroundResult = FrameworkPointcutCall.onCallAround(pointCut, activity, args);
                if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
                    activity.finish();
                }
                FrameworkPointcutCall.onCallAfter(pointCut, activity, args);
            }
        }
        if (!this.c.get()) {
            getMicroApplicationContext().onDestroyContent(this);
        }
        super.destroy(params);
        this.c.set(true);
        this.d.set(false);
        MemoryMonitor.getInstance(null).record(getAppId(), "app.stop");
        MemoryMonitor.getInstance(null).commit(getAppId());
    }

    public final boolean handle(Bundle params) {
        Log.i(TAG, getClass().getSimpleName() + ".handle(params=[" + params.toString() + "]) return: false");
        return false;
    }

    public final synchronized void pushActivity(Activity activity) {
        if (!this.a.isEmpty() && this.a.peek().get() == null) {
            this.a.pop();
        }
        this.a.push(new WeakReference(activity));
        TraceLogger.v(TAG, "=== >pushActivity(): " + activity.getComponentName().getClassName() + " to " + getClass().getName() + ", count=" + this.a.size());
    }

    public synchronized int getActiveActivityCount() {
        int count;
        count = 0;
        WeakReference[] elements = new WeakReference[this.a.size()];
        this.a.copyInto(elements);
        for (WeakReference ref : elements) {
            if (!(ref == null || ref.get() == null)) {
                count++;
            }
        }
        return count;
    }

    public synchronized void removeActivity(Activity activity) {
        if (activity != null) {
            WeakReference dirtyItem = null;
            Iterator it = this.a.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WeakReference item = (WeakReference) it.next();
                if (item.get() == activity) {
                    dirtyItem = item;
                    break;
                }
            }
            if (!this.a.remove(dirtyItem)) {
                TraceLogger.d(TAG, (String) "removeActivity: activity already removed");
            } else {
                TraceLogger.d(TAG, "=== >remove Activity:" + activity.getClass().getName() + " from " + getClass().getName() + ", count=" + this.a.size());
                if (this.a.isEmpty() && !this.mIsPrevent) {
                    destroy(null);
                }
            }
        }
    }

    public synchronized Activity findActivityByHashcode(int code) {
        Activity activity;
        Iterator it = this.a.iterator();
        while (true) {
            if (!it.hasNext()) {
                activity = null;
                break;
            }
            activity = (Activity) ((WeakReference) it.next()).get();
            if (activity != null && activity.hashCode() == code) {
                break;
            }
        }
        return activity;
    }

    public void windowFocus() {
        getMicroApplicationContext().onWindowFocus(this);
    }

    public synchronized Activity getTopActivity() {
        Activity activity;
        if (this.a.isEmpty()) {
            activity = null;
        } else {
            activity = (Activity) this.a.peek().get();
        }
        return activity;
    }

    public synchronized Activity getActivityAt(int index) {
        Activity activity;
        activity = null;
        try {
            activity = (Activity) ((WeakReference) this.a.get(index)).get();
        } catch (ArrayIndexOutOfBoundsException e) {
            TraceLogger.w(TAG, (Throwable) e);
        }
        return activity;
    }

    public int getStartActivityCount() {
        return this.a.size();
    }

    public Bundle getParams() {
        return this.b;
    }

    public void saveState(Editor editor) {
        editor.putInt(getAppId() + ".stack", this.a.size());
    }

    public synchronized void restoreState(SharedPreferences preferences) {
        int num = preferences.getInt(getAppId() + ".stack", 0);
        for (int i = 0; i < num; i++) {
            this.a.push(new WeakReference(null));
        }
    }

    public void onReady(Bundle bundle) {
        if (!TextUtils.isEmpty(getAppId())) {
            if (bundle == null) {
                bundle = new Bundle();
                bundle.putString("appId", getAppId());
            } else if (!bundle.containsKey("appId")) {
                bundle.putString("appId", getAppId());
            }
        }
        Object[] args = {bundle};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.ACTIVITYAPPLICATION_ONREADY, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.ACTIVITYAPPLICATION_ONREADY, this, args);
        if (aroundResult != null) {
            ((Boolean) aroundResult.first).booleanValue();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.ACTIVITYAPPLICATION_ONREADY, this, args);
    }
}
