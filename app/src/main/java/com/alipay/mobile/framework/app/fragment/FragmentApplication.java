package com.alipay.mobile.framework.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.aspect.FrameworkPointcutExecution;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.framework.app.AppLoadException;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.app.monitor.MemoryMonitor;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Stack;

public abstract class FragmentApplication extends MicroApplication {
    static final String TAG = "FragmentApplication";
    private View a;
    private Stack<WeakReference<BaseFragment>> b = new Stack<>();
    protected FragmentActivity mHostActivity;
    protected Bundle mParams;

    public int getFragmentContainerId() {
        return 0;
    }

    public FragmentApplication(FragmentActivity hostActivity) {
        this.mHostActivity = hostActivity;
        this.a = null;
    }

    public final void create(Bundle params) {
        this.mParams = params;
        onCreate(params);
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
                Constructor constructor = getClass().getClassLoader().loadClass(className).getConstructor(new Class[0]);
                constructor.setAccessible(true);
                add((BaseFragment) constructor.newInstance(new Object[0]));
            } catch (Throwable e) {
                throw new AppLoadException(e);
            }
        }
        TraceLogger.d((String) "FragmentApplication", getClass().getSimpleName() + ": " + getAppId() + "  start.");
        onStart();
    }

    private void a(int id) {
        if (this.mHostActivity != null) {
            if (this.a == null) {
                this.a = this.mHostActivity.findViewById(id);
                if (this.a != null) {
                    this.a.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                        }
                    });
                }
            }
            if (this.a != null) {
                this.a.setVisibility(0);
            }
        }
    }

    private void a() {
        if (this.mHostActivity != null && this.a != null) {
            this.a.setVisibility(4);
        }
    }

    public void add(BaseFragment fragment) {
        TraceLogger.d((String) "FragmentApplication", "add(fragment=" + fragment + ")");
        Bundle bundle = new Bundle();
        bundle.putString("app_id", getAppId());
        if (this.mParams != null) {
            bundle.putBundle("mExtras", this.mParams);
        }
        fragment.setArguments(bundle);
        FragmentTransaction ftr = this.mHostActivity.getSupportFragmentManager().beginTransaction();
        int id = getFragmentContainerId();
        if (id == 0) {
            id = 16908290;
        } else {
            a(id);
        }
        try {
            ftr.add(id, fragment, fragment.getClass().getName());
            ftr.addToBackStack(String.valueOf(fragment));
            ftr.commitAllowingStateLoss();
        } catch (Throwable e) {
            TraceLogger.w((String) "FragmentApplication", e);
            a();
        }
        this.b.push(new WeakReference(fragment));
    }

    public void remove(BaseFragment fragment) {
        TraceLogger.d((String) "FragmentApplication", "remove(fragment=" + fragment + ")");
        Iterator iterator = this.b.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                break;
            }
            Fragment _fragment = (Fragment) ((WeakReference) iterator.next()).get();
            if (_fragment == null) {
                iterator.remove();
            } else if (_fragment == fragment) {
                iterator.remove();
                try {
                    FragmentTransaction ftr = this.mHostActivity.getSupportFragmentManager().beginTransaction();
                    ftr.remove(fragment);
                    ftr.commitAllowingStateLoss();
                    break;
                } catch (Throwable throwable) {
                    TraceLogger.w((String) "FragmentApplication", throwable);
                }
            }
        }
        if (this.b.isEmpty() && !this.mIsPrevent) {
            destroy(null);
        }
    }

    public void replace(BaseFragment fragment) {
        TraceLogger.d((String) "FragmentApplication", "replace(fragment=" + fragment + ")");
        this.mIsPrevent = true;
        BaseFragment _fragment = null;
        while (!this.b.isEmpty()) {
            WeakReference ref = this.b.pop();
            if (ref != null) {
                _fragment = (BaseFragment) ref.get();
                if (_fragment != null) {
                    break;
                }
            }
        }
        FragmentManager fm = this.mHostActivity.getSupportFragmentManager();
        if (_fragment != null) {
            try {
                fm.popBackStack();
            } catch (Throwable e) {
                TraceLogger.w((String) "FragmentApplication", e);
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("app_id", getAppId());
        if (this.mParams != null) {
            bundle.putBundle("mExtras", this.mParams);
        }
        fragment.setArguments(bundle);
        FragmentTransaction ftr = fm.beginTransaction();
        int id = getFragmentContainerId();
        if (id == 0) {
            id = 16908290;
        } else {
            a(id);
        }
        try {
            ftr.replace(id, fragment, fragment.getClass().getName());
            ftr.addToBackStack(String.valueOf(fragment));
            ftr.commitAllowingStateLoss();
        } catch (Throwable e2) {
            TraceLogger.w((String) "FragmentApplication", e2);
            a();
        }
        this.b.push(new WeakReference(fragment));
        this.mIsPrevent = false;
    }

    public final void restart(Bundle params) {
        this.mParams = params;
        TraceLogger.d((String) "FragmentApplication", getClass().getSimpleName() + ": " + getAppId() + " restart.");
        onRestart(params);
    }

    public final void stop() {
        TraceLogger.d((String) "FragmentApplication", getClass().getSimpleName() + ": " + getAppId() + " stop.");
        onStop();
        MemoryMonitor.getInstance(null).record(getAppId(), "app.stop");
    }

    public final void destroy(Bundle params) {
        super.destroy(params);
        if (getFragmentContainerId() != 0) {
            a();
        }
        TraceLogger.d((String) "FragmentApplication", getClass().getSimpleName() + ": " + getAppId() + " destroy.");
        try {
            FragmentManager fm = this.mHostActivity.getSupportFragmentManager();
            FragmentTransaction ftr = fm.beginTransaction();
            Iterator iterator = this.b.iterator();
            while (iterator.hasNext()) {
                Fragment fragment = (Fragment) ((WeakReference) iterator.next()).get();
                if (fragment != null) {
                    iterator.remove();
                    ftr.remove(fragment);
                    fm.popBackStackImmediate();
                }
            }
            ftr.commitAllowingStateLoss();
        } catch (Throwable e) {
            TraceLogger.w((String) "FragmentApplication", e);
        }
        getMicroApplicationContext().onDestroyContent(this);
        MemoryMonitor.getInstance(null).record(getAppId(), "app.stop");
        MemoryMonitor.getInstance(null).commit(getAppId());
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
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.FRAGMENTAPPLICATION_ONREADY, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.FRAGMENTAPPLICATION_ONREADY, this, args);
        if (aroundResult != null) {
            ((Boolean) aroundResult.first).booleanValue();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.FRAGMENTAPPLICATION_ONREADY, this, args);
    }
}
