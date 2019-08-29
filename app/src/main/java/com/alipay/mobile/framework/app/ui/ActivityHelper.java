package com.alipay.mobile.framework.app.ui;

import android.app.Activity;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.ActivityApplication;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.app.monitor.ONRMonitor;
import com.alipay.mobile.framework.loading.LoadingView;
import com.alipay.mobile.framework.loading.LoadingView.Factory;
import com.alipay.mobile.framework.msg.MsgCodeConstants;
import com.alipay.mobile.framework.permission.RequestPermissionsResultCallback;
import com.alipay.mobile.framework.pipeline.Pipeline;
import com.alipay.mobile.framework.quinoxless.QuinoxUtilsDecorator.ActivityLifecycleCallback;
import com.alipay.mobile.framework.quinoxless.QuinoxUtilsDecorator.StartupSafeguard;
import com.alipay.mobile.framework.quinoxless.QuinoxUtilsDecorator.UcNativeCrashApi;
import com.alipay.mobile.framework.service.ext.ExternalService;
import com.alipay.mobile.quinox.utils.LiteProcessInfo;
import com.alipay.mobile.quinox.utils.LogUtil;
import com.alipay.mobile.quinox.utils.SystemUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ActivityHelper {
    public static final String KEY_APP_ID = "app_id";
    public static final String KEY_EXTRAS = "mExtras";
    static final String TAG = "ActivityHelper";
    private static boolean a = true;
    private static boolean b = false;
    private static boolean c = false;
    private static volatile boolean d = false;
    private static List<Runnable> l = new CopyOnWriteArrayList();
    private boolean e = false;
    /* access modifiers changed from: private */
    public Activity f;
    private ONRMonitor g;
    private final Handler h = new Handler();
    private DialogHelper i;
    private final HashMap<Integer, RequestPermissionsResultCallback> j = new HashMap<>();
    /* access modifiers changed from: private */
    public LoadingView k;
    protected ActivityApplication mApp;
    protected MicroApplicationContext mMicroApplicationContext;

    private class ActivityApplicationStub extends ActivityApplication {
        private ActivityApplicationStub() {
        }

        public String getEntryClassName() {
            return null;
        }

        /* access modifiers changed from: protected */
        public void onCreate(Bundle params) {
        }

        /* access modifiers changed from: protected */
        public void onStart() {
        }

        /* access modifiers changed from: protected */
        public void onRestart(Bundle params) {
        }

        /* access modifiers changed from: protected */
        public void onStop() {
        }

        /* access modifiers changed from: protected */
        public void onDestroy(Bundle params) {
        }
    }

    public ActivityHelper(Activity activity) {
        this.f = activity;
        this.g = new ONRMonitor(activity);
        ActivityCollections.getInstance().recordActivity(this.f);
        this.i = new DialogHelper(this.f);
        String appId = "";
        try {
            appId = this.f.getIntent().getStringExtra("app_id");
        } catch (Throwable e2) {
            TraceLogger.e((String) TAG, e2);
        }
        TraceLogger.v((String) TAG, "ActivityHelper(" + activity.getClass().getName() + ") appId: " + appId);
        a(this.f.getIntent());
        LocalBroadcastManager.getInstance(this.f).sendBroadcast(new Intent(MsgCodeConstants.FRAMEWORK_ACTIVITY_CREATE));
        attachToAppSync(activity, appId);
    }

    public void attachToAppSync(Activity activity, String appId) {
        TraceLogger.d((String) TAG, "attachToAppAsync_" + activity.getClass().getSimpleName() + "_" + appId);
        LauncherApplicationAgent application = LauncherApplicationAgent.getInstance();
        if (application == null) {
            throw new RuntimeException("Unable to start " + activity.getClass().getName());
        }
        this.mMicroApplicationContext = application.getMicroApplicationContext();
        MicroApplication app = this.mMicroApplicationContext.findAppById(appId);
        if (app instanceof ActivityApplication) {
            this.mApp = (ActivityApplication) app;
        }
        if (this.mApp == null) {
            if (!TextUtils.isEmpty(appId)) {
                try {
                    this.mApp = (ActivityApplication) this.mMicroApplicationContext.createAppById(appId);
                } catch (Throwable e2) {
                    TraceLogger.e((String) TAG, e2);
                }
            }
            if (this.mApp == null) {
                this.mApp = new ActivityApplicationStub();
                this.mApp.setAppId("ActivityApplicationStub");
                this.mApp.attachContext(this.mMicroApplicationContext);
                TraceLogger.v((String) TAG, "ActivityHelper(" + activity.getClass().getName() + ") finish & return");
                finish();
            }
        } else if (!this.mApp.isCreated()) {
            this.mApp.create(null);
        }
        this.mApp.setIsPrevent(false);
        this.mApp.pushActivity(this.f);
    }

    private void a(Intent intent) {
        try {
            Bundle mExtras = intent.getBundleExtra("mExtras");
            if (mExtras != null) {
                mExtras.setClassLoader(this.f.getClassLoader());
                intent.replaceExtras(mExtras);
            }
            if (this.mApp != null) {
                intent.putExtra("app_id", this.mApp.getAppId());
            }
        } catch (Throwable e2) {
            TraceLogger.e((String) TAG, e2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void requestPermissions(String[] permissions, int requestCode, RequestPermissionsResultCallback callback) {
        this.j.put(Integer.valueOf(requestCode), callback);
    }

    /* access modifiers changed from: 0000 */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        RequestPermissionsResultCallback callback = this.j.remove(Integer.valueOf(requestCode));
        if (callback != null) {
            callback.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void onStart() {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this.f);
        Intent intent = new Intent(MsgCodeConstants.FRAMEWORK_ACTIVITY_START);
        if (this.f != null) {
            intent.putExtra(MsgCodeConstants.FRAMEWORK_ACTIVITY_DATA, this.f.getClass().getName());
        }
        broadcastManager.sendBroadcast(intent);
    }

    public static void eraseStartupSafeguardFlags() {
        StartupSafeguard.eraseStartupSafeguardFlags();
    }

    public void onResume() {
        boolean z;
        this.e = false;
        if (VERSION.SDK_INT < 14) {
            StartupSafeguard.eraseStartupSafeguardFlags();
        }
        this.mMicroApplicationContext.updateActivity(this.f);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this.f);
        Intent intent = new Intent(MsgCodeConstants.FRAMEWORK_ACTIVITY_RESUME);
        Intent i2 = this.f.getIntent();
        if (i2 != null) {
            try {
                Bundle bundle = i2.getExtras();
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
            } catch (Throwable e2) {
                Log.w(TAG, e2);
            }
        }
        try {
            if (this.mApp != null) {
                intent.putExtra("app_id", this.mApp.getAppId());
            }
            intent.putExtra(MsgCodeConstants.FRAMEWORK_ACTIVITY_DATA, this.f.getClass().getName());
            intent.putExtra(MsgCodeConstants.FRAMEWORK_APP_DATA, this.mApp.getAppId());
            if (LiteProcessInfo.g(this.f).isCurrentProcessALiteProcess() || a()) {
                z = true;
            } else {
                z = false;
            }
            intent.putExtra(MsgCodeConstants.FRAMEWORK_IS_TINY_APP, z);
            if (i2 != null) {
                intent.putExtra(MsgCodeConstants.FRAMEWORK_IS_RN_APP, i2.getBooleanExtra("IS_RN_APP", false));
            }
            broadcastManager.sendBroadcast(intent);
        } catch (Throwable e3) {
            LogUtil.e(TAG, "sendBroadcast: MsgCodeConstants.FRAMEWORK_ACTIVITY_RESUME error", e3);
        }
        LogUtil.d(TAG, "sendBroadcast: MsgCodeConstants.FRAMEWORK_ACTIVITY_RESUME");
        if (a) {
            a = false;
            broadcastManager.sendBroadcast(new Intent("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND"));
            LogUtil.d(TAG, "sendBroadcast: MsgCodeConstants.FRAMEWORK_BROUGHT_TO_FOREGROUND");
        }
        startClientStartedPipeline();
        startFrameworkSecondPipeLine();
        if (this.g != null) {
            this.g.register();
        }
    }

    private boolean a() {
        return (this.mApp == null || this.mApp.getParams() == null || !this.mApp.getParams().getBoolean("isTinyApp")) ? false : true;
    }

    public static void startFrameworkSecondPipeLine() {
        if (!c) {
            c = true;
            TraceLogger.e((String) "Pipeline", (String) "PIPELINE_FRAMEWORK_SECOND_STARTED : start");
            MicroApplicationContext microContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
            if (microContext.hasInited()) {
                Pipeline pipeLine = microContext.getPipelineByName(MsgCodeConstants.PIPELINE_FRAMEWORK_SECOND);
                if (pipeLine == null) {
                    TraceLogger.w((String) TAG, (String) "The framework has inited, but pipeline==null.");
                } else {
                    pipeLine.start();
                }
            } else {
                TraceLogger.i((String) TAG, (String) "The framework hasn't inited, pipeline maybe null.");
            }
        }
    }

    public static void startFrameworkPipeLine() {
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().getPipelineByName("com.alipay.mobile.framework.INITED").start();
    }

    public static void startHomePageLoadFinishPipeLine() {
        if (!d) {
            d = true;
            TraceLogger.i((String) "Pipeline", (String) "com.alipay.mobile.framework.HOMEPAGE_LOAD_FINISH : start");
            MicroApplicationContext microContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
            if (microContext.hasInited()) {
                Pipeline pipeLine = microContext.getPipelineByName(MsgCodeConstants.PIPELINE_HOMEPAGE_LOAD_FINISH);
                if (pipeLine == null) {
                    TraceLogger.w((String) TAG, (String) "The framework has inited, but pipeline==null.");
                } else {
                    pipeLine.start();
                }
            } else {
                TraceLogger.i((String) TAG, (String) "The framework hasn't inited, pipeline maybe null.");
            }
            try {
                for (Runnable run : l) {
                    run.run();
                }
            } catch (Throwable e2) {
                TraceLogger.e(TAG, "invoke listener failed.", e2);
            }
        }
    }

    public static void registerHomeLoadFinishListener(Runnable listener) {
        try {
            l.add(listener);
        } catch (Throwable e2) {
            TraceLogger.w(TAG, "add home load finish listener failed.", e2);
        }
    }

    public static void startClientStartedPipeline() {
        if (!b) {
            b = true;
            TraceLogger.e((String) "Pipeline", (String) "PIPELINE_FRAMEWORK_CLIENT_STARTED : start");
            MicroApplicationContext microContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
            if (microContext.hasInited()) {
                Pipeline pipeLine = microContext.getPipelineByName(MsgCodeConstants.PIPELINE_FRAMEWORK_CLIENT_STARTED);
                if (pipeLine == null) {
                    TraceLogger.w((String) TAG, (String) "The framework has inited, but pipeline==null.");
                } else {
                    pipeLine.start();
                }
            } else {
                TraceLogger.i((String) TAG, (String) "The framework hasn't inited, pipeline maybe null.");
            }
        }
    }

    public void onPause() {
        this.e = true;
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this.f);
        Intent intent = new Intent(MsgCodeConstants.FRAMEWORK_ACTIVITY_PAUSE);
        if (this.mApp != null) {
            intent.putExtra(MsgCodeConstants.FRAMEWORK_ACTIVITY_DATA, this.mApp.getAppId());
        }
        broadcastManager.sendBroadcast(intent);
        if (this.g != null) {
            this.g.unregister();
        }
    }

    public void onStop() {
        this.e = false;
    }

    public void onContentChanged() {
    }

    public void onNewIntent(Intent intent) {
        this.mApp.setIsPrevent(false);
        a(intent);
    }

    public void onUserInteraction() {
        ONRMonitor.onUserInteraction(this.f);
    }

    public void onUserLeaveHint() {
        long delay = 100;
        if (VERSION.SDK_INT < 21) {
            this.h.postDelayed(new Runnable() {
                public void run() {
                    if (SystemUtil.isAppOnBackground(ActivityHelper.this.f) || LiteProcessInfo.g(ActivityHelper.this.f).isCurrentProcessALiteProcess()) {
                        ActivityHelper.sendUserLeaveHintBroadcast(ActivityHelper.this.f);
                    }
                }
            }, 100);
            return;
        }
        if (!LiteProcessInfo.g(this.f).isCurrentProcessALiteProcess()) {
            delay = 2000;
        }
        this.h.postDelayed(new Runnable() {
            public void run() {
                if (SystemUtil.isAppOnBackground(ActivityHelper.this.f) || LiteProcessInfo.g(ActivityHelper.this.f).isCurrentProcessALiteProcess()) {
                    ActivityHelper.sendUserLeaveHintBroadcast(ActivityHelper.this.f);
                }
            }
        }, delay);
    }

    public static void sendUserLeaveHintBroadcast(Activity activity) {
        if (!a) {
            a = true;
            LocalBroadcastManager.getInstance(activity).sendBroadcast(new Intent("com.alipay.mobile.framework.USERLEAVEHINT"));
            UcNativeCrashApi.setForeground(false);
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this.f);
        Intent intent = new Intent(MsgCodeConstants.FRAMEWORK_WINDOW_FOCUS_CHANGED);
        intent.putExtra(MsgCodeConstants.FRAMEWORK_WINDOW_FOCUS_CHANGED, hasFocus);
        broadcastManager.sendBroadcast(intent);
        if (hasFocus) {
            this.mApp.windowFocus();
        }
    }

    public void dispatchOnTouchEvent(MotionEvent event) {
    }

    public void onSaveInstanceState(Bundle outState) {
        this.mMicroApplicationContext.saveState();
    }

    public void onDestroy() {
        b();
        if (this.mApp != null) {
            this.mApp.removeActivity(this.f);
        }
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this.f);
        Intent intent = new Intent(MsgCodeConstants.FRAMEWORK_ACTIVITY_DESTROY);
        intent.putExtra(MsgCodeConstants.FRAMEWORK_ACTIVITY_DATA, this.f.getClass().getName());
        broadcastManager.sendBroadcast(intent);
        if (this.g != null) {
            this.g.unregister();
        }
    }

    private static void b() {
        try {
            Class cls = Class.forName("android.text.TextLine");
            Field f2 = cls.getDeclaredField("sCached");
            f2.setAccessible(true);
            f2.set(null, Array.newInstance(cls, 3));
        } catch (Throwable e2) {
            Log.v(TAG, "ActivityHelper.onDestroy().recycle(): [android.text.TextLine]", e2);
        }
    }

    public void finish() {
        if (this.mApp != null) {
            this.mApp.removeActivity(this.f);
        }
        this.i.dismissProgressDialog();
    }

    public void alert(String title, String msg, String positive, OnClickListener positiveListener, String negative, OnClickListener negativeListener) {
        this.i.alert(title, msg, positive, positiveListener, negative, negativeListener);
    }

    public void alert(String title, String msg, String positive, OnClickListener positiveListener, String negative, OnClickListener negativeListener, Boolean isCanceledOnTouchOutside) {
        this.i.alert(title, msg, positive, positiveListener, negative, negativeListener, isCanceledOnTouchOutside);
    }

    public void alert(String title, String msg, String positive, OnClickListener positiveListener, String negative, OnClickListener negativeListener, Boolean isCanceledOnTouchOutside, Boolean cancelable) {
        this.i.alert(title, msg, positive, positiveListener, negative, negativeListener, isCanceledOnTouchOutside, cancelable);
    }

    public void toast(String msg, int period) {
        this.i.toast(msg, period);
    }

    public void showProgressDialog(String msg) {
        this.i.showProgressDialog(msg);
    }

    public void showProgressDialog(String msg, boolean cancelable, OnCancelListener cancelListener) {
        this.i.showProgressDialog(msg, cancelable, cancelListener, true);
    }

    public void dismissProgressDialog() {
        this.i.dismissProgressDialog();
    }

    public <T> T findServiceByInterface(String interfaceName) {
        return this.mMicroApplicationContext.findServiceByInterface(interfaceName);
    }

    public <T extends ExternalService> T getExtServiceByInterface(String className) {
        return this.mMicroApplicationContext.getExtServiceByInterface(className);
    }

    public ActivityApplication getApp() {
        return this.mApp;
    }

    public MicroApplicationContext getMicroApplicationContext() {
        return this.mMicroApplicationContext;
    }

    public static boolean isBrought2Foreground() {
        return isBackgroundRunning();
    }

    public static boolean isBackgroundRunning() {
        return a && !ActivityLifecycleCallback.isApplicationInForeground();
    }

    public void addLoadingView(LoadingView loadingView) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("must addLoadingView on UI thread");
        }
        ViewGroup contentContainer = (ViewGroup) this.f.findViewById(16908290);
        if (contentContainer == null) {
            throw new IllegalStateException("can not find content container");
        }
        if (loadingView == null) {
            Factory loadingViewFactory = this.mMicroApplicationContext.getLoadingPageManager().getDefaultLoadingViewFactory();
            if (loadingViewFactory != null) {
                loadingView = loadingViewFactory.createLoadingView(this.mApp.getSourceId(), this.mApp.getAppId(), this.mApp.getParams());
            }
        }
        if (loadingView == this.k) {
            TraceLogger.w((String) TAG, (String) "same loading view");
            return;
        }
        if (this.k != null) {
            TraceLogger.i((String) TAG, "remove original loading view:" + this.k);
            contentContainer.removeView(this.k);
        }
        this.k = loadingView;
        this.k.setVisibility(8);
        this.k.setOnCancelListener(new LoadingView.OnCancelListener() {
            public void onCancel() {
                ActivityHelper.this.f.finish();
            }
        });
        TraceLogger.i((String) TAG, "addLoadingView:" + this.k);
        contentContainer.addView(this.k, new LayoutParams(-1, -1));
        this.k.setHostActivity(this.f);
    }

    public boolean startLoading() {
        TraceLogger.i((String) TAG, "startLoading:" + this.k);
        if (this.k == null) {
            return false;
        }
        this.h.post(new Runnable() {
            public void run() {
                TraceLogger.i((String) ActivityHelper.TAG, (String) "do startLoading");
                ActivityHelper.this.k.setVisibility(0);
                ActivityHelper.this.k.start();
            }
        });
        return true;
    }

    public boolean isBehindTranslucentActivity() {
        return this.e;
    }

    public boolean stopLoading() {
        TraceLogger.i((String) TAG, "stopLoading:" + this.k);
        if (this.k == null) {
            return false;
        }
        this.h.post(new Runnable() {
            public void run() {
                TraceLogger.i((String) ActivityHelper.TAG, (String) "do stopLoading");
                ActivityHelper.this.k.setVisibility(8);
                ActivityHelper.this.k.stop();
            }
        });
        return true;
    }
}
