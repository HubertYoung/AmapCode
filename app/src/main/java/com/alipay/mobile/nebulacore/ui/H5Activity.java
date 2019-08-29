package com.alipay.mobile.nebulacore.ui;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.loading.LoadingView;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5LoadingView;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.monitor.track.TrackPageConfig;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.activity.H5ActivityResultManager;
import com.alipay.mobile.nebula.activity.H5BaseActivity;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.process.H5EventHandler;
import com.alipay.mobile.nebula.provider.H5BizProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5DialogManagerProvider;
import com.alipay.mobile.nebula.provider.H5DialogManagerProvider.OnClickPositiveListener;
import com.alipay.mobile.nebula.provider.H5EmbededViewProvider;
import com.alipay.mobile.nebula.provider.H5LoadingViewProvider;
import com.alipay.mobile.nebula.provider.H5TinyAppProvider;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.util.H5DeviceHelper;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5KeepAliveUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5StatusBarUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.ManagerToastLikeDialog;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.core.H5PagePreloader;
import com.alipay.mobile.nebulacore.core.H5SessionImpl;
import com.alipay.mobile.nebulacore.data.H5ParamHolder;
import com.alipay.mobile.nebulacore.data.H5ParamHolder.PageParamListener;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.env.H5WebViewChoose;
import com.alipay.mobile.nebulacore.prerender.H5PreRenderPool;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabBar;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabInfoParser;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabInfoParser.H5SessionTabInfoListener;
import com.alipay.mobile.nebulacore.util.H5AnimatorUtil;
import com.alipay.mobile.nebulacore.util.H5IntentUtil;
import com.alipay.mobile.nebulacore.util.NebulaUtil;
import com.alipay.mobile.nebulacore.wallet.H5Application;
import com.alipay.mobile.nebulacore.wallet.H5LoggerPlugin;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.ajx3.util.Constants;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;

public class H5Activity extends H5BaseActivity implements TrackPageConfig {
    public static final int FILE_CHOOSER_REQUEST_CODE = 1;
    public String TAG = "H5Activity";
    protected Bundle a = null;
    boolean b = false;
    /* access modifiers changed from: private */
    public H5FragmentManager c;
    private BroadcastReceiver d = null;
    private PageParamListener e = null;
    private boolean f = true;
    private View g = null;
    private H5LoadingViewProvider h;
    /* access modifiers changed from: private */
    public H5SessionImpl i;
    private H5LoadingDialog j;
    private boolean k = false;
    private boolean l = false;
    private boolean m = false;
    private boolean n = true;
    private String o = "appPause";
    private boolean p = false;
    private RelativeLayout q;
    private boolean r = false;
    private boolean s = false;
    private ActivityLifecycleCallback t = null;
    /* access modifiers changed from: private */
    public String u = null;
    private Bundle v;

    private static class ActivityLifecycleCallback implements ActivityLifecycleCallbacks {
        private String a;
        private WeakReference<H5Activity> b;

        ActivityLifecycleCallback(String tag, H5Activity activity) {
            this.a = tag;
            this.b = new WeakReference<>(activity);
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
            H5Log.d(this.a, "onActivityResumed  : " + activity);
            if (this.b != null) {
                H5Activity h5Activity = (H5Activity) this.b.get();
                if (h5Activity != null && h5Activity.b) {
                    if (H5Activity.b(this.a, h5Activity, new WeakReference(activity))) {
                        h5Activity.a((String) "onActivityResumed");
                    }
                }
            }
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        public void onActivityDestroyed(Activity activity) {
        }
    }

    public static class H5Activity1 extends H5Activity {
        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            H5Activity.super.onCreate(savedInstanceState);
            H5Log.d(this.TAG, "onCreate H5Activity1");
        }
    }

    public static class H5Activity2 extends H5Activity {
        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            H5Activity.super.onCreate(savedInstanceState);
            H5Log.d(this.TAG, "onCreate H5Activity2");
        }
    }

    public static class H5Activity3 extends H5Activity {
        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            H5Activity.super.onCreate(savedInstanceState);
            H5Log.d(this.TAG, "onCreate H5Activity3");
        }
    }

    public static class H5Activity4 extends H5Activity {
        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            H5Activity.super.onCreate(savedInstanceState);
            H5Log.d(this.TAG, "onCreate H5Activity4");
        }
    }

    public static class H5Activity5 extends H5Activity {
        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            H5Activity.super.onCreate(savedInstanceState);
            H5Log.d(this.TAG, "onCreate H5Activity5");
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        H5Log.d(this.TAG, "requestCode " + requestCode + " resultCode:" + resultCode + Token.SEPARATOR + data);
        H5ActivityResultManager.getInstance().sendResult(requestCode, resultCode, data);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        H5Log.d(this.TAG, "onConfigChange: " + H5Utils.toJSONString(newConfig));
        if (H5DeviceHelper.isFoldingScreen()) {
            H5DimensionUtil.resetScreenWidthHeight(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        H5Utils.handleTinyAppKeyEvent((String) "main", (String) "H5Activity.onCreate()");
        super.onCreate(savedInstanceState);
        if (H5Utils.isInTinyProcess()) {
            H5PagePreloader.onH5ActivityCreated(this);
        }
        H5Log.d(this.TAG, "onCreate " + this + " with savedInstanceState " + savedInstanceState);
        this.TAG += "_" + H5Activity.class.hashCode();
        try {
            Class.forName("android.os.AsyncTask");
        } catch (Throwable ignored) {
            H5Log.e(this.TAG, "Exception", ignored);
        }
        H5Log.d(this.TAG, "configChange oncreate: " + H5Utils.toJSONString(getResources().getConfiguration()));
        if (savedInstanceState != null) {
            String save = H5Utils.getString(savedInstanceState, (String) "savedInstanceStateKey");
            if (!TextUtils.isEmpty(save) && !"yes".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_savedInstanceState"))) {
                H5Log.d(this.TAG, " savedInstanceState " + save);
                H5LogUtil.logNebulaTech(H5LogData.seedId("h5_savedInstanceState").param4().add("isInTinyProcess", Boolean.valueOf(H5Utils.isInTinyProcess())));
                if (H5Utils.isInTinyProcess()) {
                    this.r = true;
                    H5Log.d(this.TAG, "not handler savedInstanceState in isInTinyProcess");
                } else {
                    finish();
                    return;
                }
            }
        }
        Bundle bundle = l();
        if (bundle == null) {
            finish();
            return;
        }
        boolean fullScreen = false;
        String snapshot = H5Utils.getString(bundle, (String) H5Param.SNAPSHOT);
        try {
            fullScreen = H5Utils.getBoolean(bundle, (String) H5Param.LONG_FULLSCREEN, false);
        } catch (Exception e2) {
            H5Log.e(this.TAG, (Throwable) e2);
        }
        if (!TextUtils.isEmpty(snapshot) && BQCCameraParam.VALUE_NO.equalsIgnoreCase(snapshot)) {
            H5Log.d(this.TAG, "snapshot " + snapshot);
            getWindow().addFlags(8192);
        }
        try {
            requestWindowFeature(1);
        } catch (Throwable throwable) {
            H5Log.e(this.TAG, throwable);
        }
        if (fullScreen) {
            getWindow().setFlags(1024, 1024);
        }
        H5Environment.setContext(this);
        H5Log.d(this.TAG, "h5_activity " + getResources());
        setContentView(R.layout.h5_activity);
        if (!H5Utils.isTinyMiniService(bundle) && bundle.containsKey("needAnimInTiny") && bundle.getBoolean("needAnimInTiny")) {
            H5Log.d(this.TAG, "create needAnimInTiny true");
            this.s = true;
            overridePendingTransition(getResources().getIdentifier("tiny_push_up_in", ResUtils.ANIM, H5Utils.getContext().getPackageName()), getResources().getIdentifier("tiny_fading_out", ResUtils.ANIM, H5Utils.getContext().getPackageName()));
        }
        boolean isTransparent = H5Utils.getBoolean(bundle, (String) "showLoadingView", false);
        if (!isTransparent && !fullScreen) {
            try {
                b(bundle);
            } catch (Throwable throwable2) {
                H5Log.e(this.TAG, throwable2);
            }
        }
        if (!H5WebViewChoose.notNeedInitUc(this.a) && (isTransparent || fullScreen)) {
            a(NebulaUtil.isShowTransAnimate(bundle));
        }
        if (Nebula.getService().getTopSession() instanceof H5SessionImpl) {
            this.i = (H5SessionImpl) Nebula.getService().getTopSession();
        }
        String appId = H5Utils.getString(bundle, (String) "appId");
        Nebula.initInfo(appId, this);
        try {
            Nebula.setWindowSoftInputMode(this, appId, bundle, isTransparent);
        } catch (Exception e3) {
            H5Log.e(this.TAG, (Throwable) e3);
        }
        this.u = H5KeepAliveUtil.getChInfo(getIntent(), getActivityApplication());
        a(bundle);
        if (!"NO".equalsIgnoreCase(H5Environment.getConfig("h5_enableAppPauseWhenTranslucent"))) {
            this.t = new ActivityLifecycleCallback(this.TAG, this);
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext().registerActivityLifecycleCallbacks(this.t);
        }
    }

    private static void a(Bundle param) {
        if (param != null && param.containsKey(H5Param.AUTH_CODE_KEY)) {
            H5BizProvider bizProvider = (H5BizProvider) H5Utils.getProvider(H5BizProvider.class.getName());
            if (bizProvider != null) {
                bizProvider.cancelBizTimeoutCheck(H5Utils.getString(param, (String) H5Param.AUTH_CODE_KEY));
            }
        }
    }

    public H5Session getH5Session() {
        return this.i;
    }

    private synchronized void b(Bundle bundle) {
        if (this.f) {
            this.f = false;
            String title = H5Utils.getString(bundle, (String) H5Param.LONG_WALLET_APP_NAME);
            String icon = H5Utils.getString(bundle, (String) H5Param.LONG_WALLET_APP_ICON);
            H5Log.d(this.TAG, "show loading view.");
            this.h = (H5LoadingViewProvider) H5Utils.getProvider(H5LoadingViewProvider.class.getName());
            if (this.h != null) {
                H5Log.d(this.TAG, "got H5LoadingViewProvider: " + this.h);
                this.g = this.h.getContentView(this, bundle);
            }
            if (this.g == null) {
                H5Log.d(this.TAG, "loadingView == null, use nebula LoadingView");
                this.h = new H5LoadingView();
                this.g = this.h.getContentView(this, bundle);
            }
            View view = this.g;
            if (!TextUtils.isEmpty(title)) {
                this.h.setTitle(title);
            }
            if (!TextUtils.isEmpty(icon)) {
                this.h.setIcon(icon);
            }
            if (view != null && H5StatusBarUtils.isSupport() && H5StatusBarUtils.isConfigSupport()) {
                view.setPadding(0, H5StatusBarUtils.getStatusBarHeight(this), 0, 0);
                H5StatusBarUtils.setTransparentColor(this, 855638016);
            }
            if (c()) {
                b();
                showFrameworkLoadingView(view);
            } else {
                b();
                ((RelativeLayout) findViewById(R.id.h5_fragment)).addView(view, new LayoutParams(-1, -1));
            }
        }
    }

    private void b() {
        View whiteBg = new View(this);
        whiteBg.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        whiteBg.setBackgroundColor(-1);
        ((ViewGroup) findViewById(R.id.h5_fragment)).addView(whiteBg, 0);
    }

    public void showFrameworkLoadingView(View view) {
        H5Log.d(this.TAG, "show framework loading view." + view);
        if (view instanceof LoadingView) {
            addLoadingView((LoadingView) view);
        } else {
            LayoutParams params = new LayoutParams(-1, -1);
            LoadingView loadingView = new LoadingView(this) {
                public void onStart() {
                }

                public void onStop() {
                }

                public void onHandleMessage(String s, Map<String, Object> map) {
                }
            };
            loadingView.addView(view, params);
            addLoadingView(loadingView);
        }
        startLoading();
    }

    private static boolean c() {
        if (LauncherApplicationAgent.getInstance().getMicroApplicationContext().getLoadingPageManager() == null) {
            return false;
        }
        JSONObject jsonObject = H5Utils.parseObject(H5Environment.getConfigWithProcessCache("h5_enableNebulaAppLoadingView"));
        if (jsonObject == null || jsonObject.isEmpty() || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Utils.getString(jsonObject, (String) "framework"))) {
            return true;
        }
        return false;
    }

    private void a(boolean showTransAnimate) {
        LayoutParams params = new LayoutParams(-1, -1);
        RelativeLayout mainLayout = new RelativeLayout(this);
        H5Log.d(this.TAG, " showTransAnimate : " + showTransAnimate);
        if (!showTransAnimate || BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_open_transparent_bg"))) {
            mainLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            mainLayout.setBackgroundColor(0);
        }
        if (!H5WebViewChoose.notNeedInitUc(this.a)) {
            this.j = new H5LoadingDialog(this);
            this.j.show();
            if (showTransAnimate && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_open_transparent_bg"))) {
                this.j.setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        H5Activity.this.finish();
                    }
                });
            }
        }
        ((RelativeLayout) findViewById(R.id.h5_fragment)).addView(mainLayout, params);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        try {
            if (canUsePreRender()) {
                H5PreRenderPool.getInstance().release();
            }
            super.onDestroy();
            if (this.d != null) {
                LocalBroadcastManager.getInstance(this).unregisterReceiver(this.d);
                this.d = null;
            }
            this.g = null;
            this.e = null;
            if (!H5Utils.isInWallet() && ManagerToastLikeDialog.hasManagerInstance()) {
                ManagerToastLikeDialog.getInstance().cancelAllToastLikeDialogs();
            }
            if (this.t != null) {
                LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext().unregisterActivityLifecycleCallbacks(this.t);
            }
            H5Log.d(this.TAG, "onDestroy " + this);
        } catch (Throwable t2) {
            H5Log.e(this.TAG, "destroy exception.", t2);
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_DESTROY_EXCEPTION").param1().add(t2.toString(), null).param2().add(H5AppUtil.secAppId, null));
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        H5Log.d(this.TAG, "onStart " + this);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        H5Log.d(this.TAG, "onStop " + this);
        a((String) "onStop");
    }

    private void d() {
        if (this.a == null && this.e == null) {
            this.e = new PageParamListener() {
                public void onPageParam(final Bundle bundle) {
                    if (!H5Activity.this.isFinishing()) {
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                H5Activity.this.c(bundle);
                            }
                        });
                    }
                }
            };
            Bundle bundle = l();
            if (bundle == null) {
                finish();
                return;
            }
            String landscape = H5Utils.getString(bundle, (String) H5Param.LONG_LANDSCAPE);
            if (landscape.equals(H5Param.LONG_LANDSCAPE)) {
                if (getRequestedOrientation() != 0) {
                    setRequestedOrientation(0);
                }
            } else if (landscape.equals("auto") && getRequestedOrientation() != -1) {
                setRequestedOrientation(-1);
            }
            String asyncIndex = H5Utils.getString(bundle, (String) H5Param.ASYNCINDEX);
            if (!TextUtils.isEmpty(asyncIndex)) {
                H5ParamHolder.retrievePageParam(asyncIndex, this.e);
            } else {
                this.e.onPageParam(bundle);
            }
        }
    }

    /* access modifiers changed from: private */
    public void c(Bundle bundle) {
        H5Utils.handleTinyAppKeyEvent(new String[]{"main", "package_prepare"}, (String) "H5Activity.onPageParamReady()");
        H5Log.d(this.TAG, "onPageParamReady");
        this.a = bundle;
        if (H5Utils.getBoolean(this.a, (String) "isTinyApp", false)) {
            H5TinyAppProvider h5TinyAppProvider = (H5TinyAppProvider) H5Utils.getProvider(H5TinyAppProvider.class.getName());
            if (h5TinyAppProvider != null) {
                h5TinyAppProvider.handlerStartParamsReady(this, this.a);
            }
        }
        if (H5WebViewChoose.notNeedInitUc(this.a)) {
            a();
            return;
        }
        h();
        int time4Degrade = e();
        if (time4Degrade == -1) {
            return;
        }
        if (H5Utils.getBoolean(this.a, (String) H5Param.FIRST_INIT_USE_ANDROID_WEBVIEW, false)) {
            H5Log.d(this.TAG, "FIRST_INIT_USE_ANDROID_WEBVIEW");
            a();
            return;
        }
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                if (!H5Activity.this.isFinishing()) {
                    H5Log.d(H5Activity.this.TAG, "fragmentManager " + H5Activity.this.c + Token.SEPARATOR + H5Activity.this.a);
                    if (H5Activity.this.c == null) {
                        H5LogUtil.logNebulaTech(H5LogData.seedId("h5_wait_uc_init_timeout"));
                        H5Activity.this.a();
                    }
                }
            }
        }, (long) time4Degrade);
        H5Utils.getExecutor(H5ThreadType.NORMAL).execute(new Runnable() {
            public void run() {
                if (H5Activity.this.c == null && !H5Activity.this.isFinishing() && H5ServiceUtils.getUcService() == null && !Constants.ANIMATOR_NONE.equals(H5ServiceUtils.getServiceDownGradeMode())) {
                    H5Log.d(H5Activity.this.TAG, "initPageContent because of UcService not found");
                    H5Utils.runOnMain(new Runnable() {
                        public void run() {
                            if (!H5Activity.this.isFinishing() && H5Activity.this.c == null) {
                                H5Activity.this.a();
                            }
                        }
                    });
                }
            }
        });
    }

    private int e() {
        if (H5WebViewChoose.useSysWebWillCrash()) {
            return -1;
        }
        int time = 45000;
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            JSONObject configObj = H5Utils.parseObject(h5ConfigProvider.getConfig("h5WaitUCConfig"));
            if (configObj != null && !configObj.isEmpty()) {
                time = H5Utils.getInt(configObj, (String) "waitTime", 12000);
                H5Log.d(this.TAG, "getTimeout from configservice " + time);
            }
        }
        H5Log.d(this.TAG, "getTimeout final " + time);
        return time;
    }

    private boolean f() {
        return H5Utils.isInTinyProcess() || this == H5KeepAliveUtil.getRunningActivity();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        Bundle bundle = null;
        super.onNewIntent(intent);
        try {
            final H5Page h5Page = g();
            if (h5Page != null) {
                this.p = true;
                final String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
                boolean needClientRelaunch = false;
                if (f()) {
                    needClientRelaunch = H5KeepAliveUtil.needRelaunchInNebula(appId, intent, this.u, getActivityApplication());
                }
                if (intent != null) {
                    bundle = intent.getExtras();
                }
                if (needClientRelaunch && bundle != null) {
                    bundle.putBoolean("shouldNotReLaunch", true);
                }
                final boolean finalNeedClientRelaunch = needClientRelaunch;
                final Intent intent2 = intent;
                Nebula.sendAppResume(h5Page, bundle, new H5CallBack() {
                    public void onCallBack(JSONObject param) {
                        H5Log.d(H5Activity.this.TAG, "appResume needClientRelaunch: " + finalNeedClientRelaunch + " callback: " + param);
                        String chInfo = H5KeepAliveUtil.getChInfo(intent2, H5Activity.this.getActivityApplication());
                        if (finalNeedClientRelaunch) {
                            H5Activity.this.a(appId, h5Page);
                        }
                        H5Activity.this.u = chInfo;
                    }
                });
                H5Log.d(this.TAG, "appResume form onNewIntent");
                if (H5Utils.isInTinyProcess()) {
                    h5Page.sendEvent(H5LoggerPlugin.KEEP_ALIVE_PAGE_PERFORMANCE, null);
                }
            }
        } catch (Throwable throwable) {
            H5Log.e(this.TAG, throwable);
        }
    }

    /* access modifiers changed from: private */
    public void a(String appId, H5Page h5Page) {
        String homePage = H5StartParamManager.getInstance().getHomePage(appId);
        H5Log.d(this.TAG, "do relaunch in nebula");
        if ((this.i.getH5SessionTabBar() != null && this.i.getH5SessionTabBar().getContent() == null && TextUtils.equals("YES", H5Utils.getString(this.a, (String) "enableTabBar"))) || !(this.i.getH5SessionTabManager() == null || this.i.getH5SessionTabManager().getCurrentIndex() == -1)) {
            H5Log.d(this.TAG, "relaunch with tabbar");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "tag", (Object) homePage);
            jsonObject.put((String) "recreate", (Object) Boolean.valueOf(true));
            h5Page.sendEvent(H5SessionTabBar.SWITCH_TAB, jsonObject);
            return;
        }
        H5Log.d(this.TAG, "relaunch with pushWindow");
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put((String) "url", (Object) new StringBuilder(MetaRecord.LOG_SEPARATOR).append(homePage).toString());
        jsonObject2.put((String) H5StartParamManager.launchParamsTag, (Object) homePage);
        JSONObject paramObject = new JSONObject();
        paramObject.put((String) "closeAllWindow", (Object) Boolean.valueOf(true));
        paramObject.put((String) "fromRelaunch", (Object) Boolean.valueOf(true));
        jsonObject2.put((String) "param", (Object) paramObject);
        h5Page.sendEvent("pushWindow", jsonObject2);
    }

    private H5Page g() {
        if (this.i != null) {
            return this.i.getTopPage();
        }
        H5Log.d(this.TAG, "h5Session==null");
        return null;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Bundle param = null;
        try {
            H5Utils.handleTinyAppKeyEvent((String) "main", (String) "H5Activity.onResume()");
            super.onResume();
            H5Log.d(this.TAG, "onResume " + this);
            this.m = true;
            d();
            if (!this.n) {
                if (this.p) {
                    H5Log.d(this.TAG, "hasSendResumeFromOnNewIntent");
                    this.p = false;
                } else {
                    H5Page h5Page = g();
                    if (h5Page == null || h5Page.getBridge() == null) {
                        H5Log.d(this.TAG, "h5page==null");
                    } else {
                        H5Log.d(this.TAG, "appResume form onResume");
                        if (this.mApp instanceof H5Application) {
                            param = ((H5Application) this.mApp).takeLastRestartParam();
                        }
                        Nebula.sendAppResume(h5Page, param, null);
                    }
                }
            }
            this.n = false;
            if (g() != null) {
                g().sendEvent(CommonEvents.H5_SESSION_RESUME, null);
            }
        } catch (Throwable throwable) {
            H5Log.e(this.TAG, throwable);
        }
    }

    private void h() {
        if (this.d == null) {
            this.d = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    if (intent != null && intent.getAction() != null && H5Param.H5_ACTION_UC_INIT_FINISH.equals(intent.getAction()) && !H5Activity.this.isFinishing()) {
                        if (H5Activity.this.a != null) {
                            H5Activity.this.a.putBoolean(H5Param.LONG_UC_INIT_LOADING_SHOWN, true);
                        }
                        H5Log.d(H5Activity.this.TAG, "uc init result " + H5Utils.getBoolean(intent.getExtras(), (String) "result", false));
                        H5Activity.this.a();
                    }
                }
            };
            IntentFilter filter = new IntentFilter();
            filter.addAction(H5Param.H5_ACTION_UC_INIT_FINISH);
            LocalBroadcastManager.getInstance(this).registerReceiver(this.d, filter);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        H5Utils.handleTinyAppKeyEvent(new String[]{"uc_init", "main"}, (String) "H5Activity.initPageContent()");
        H5Log.d(this.TAG, "initPageContent");
        if (!isFinishing() && this.c == null) {
            this.c = new H5FragmentManager(this);
            hideLoadingView();
            if (TextUtils.equals("YES", H5Utils.getString(this.a, (String) "enableTabBar"))) {
                String tabBarJsonUrl = H5Utils.getString(this.a, (String) "tabBarJson");
                if (!TextUtils.isEmpty(tabBarJsonUrl)) {
                    H5SessionTabInfoParser.getOnlineData(tabBarJsonUrl, new H5SessionTabInfoListener() {
                        public void onGetSyncData(JSONObject data) {
                        }

                        public void onGetAsyncData(JSONObject data) {
                            H5Activity.this.a(data);
                        }

                        public void onShowDefaultTab() {
                            H5Activity.this.i();
                        }
                    }, H5Utils.getString(this.a, (String) "appId"));
                } else {
                    H5SessionTabInfoParser.getOfflineData(this.i, new H5SessionTabInfoListener() {
                        public void onGetSyncData(JSONObject data) {
                            H5Activity.this.a(data);
                        }

                        public void onGetAsyncData(JSONObject data) {
                            H5Activity.this.a(data);
                        }

                        public void onShowDefaultTab() {
                            H5Activity.this.i();
                        }
                    }, H5Utils.getString(this.a, (String) "appId"));
                }
            } else {
                this.c.addFragment(this.a, false, false);
            }
            boolean isH5App = H5Utils.getBoolean(this.a, (String) H5Param.isH5app, false);
            String appId = H5Utils.getString(this.a, (String) "appId");
            if (!TextUtils.isEmpty(appId) && isH5App && !H5AppUtil.isH5ContainerAppId(appId)) {
                Nebula.checkOffline(this, appId);
            }
        }
    }

    /* access modifiers changed from: private */
    public void i() {
        if (!isFinishing()) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    if (H5Activity.this.i != null) {
                        H5SessionTabBar h5SessionTabBar = H5Activity.this.i.getH5SessionTabBar();
                        if (h5SessionTabBar != null) {
                            h5SessionTabBar.createDefaultSessionTab(H5Activity.this, H5Utils.getInt(H5Activity.this.a, (String) "tabItemCount"));
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void a(final JSONObject data) {
        if (!isFinishing() && !this.k) {
            this.k = true;
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    if (data == null || data.isEmpty()) {
                        H5Activity.this.c.addFragment(H5Activity.this.a, false, false);
                        H5Activity.this.k();
                        return;
                    }
                    boolean success = false;
                    if (H5Activity.this.i != null) {
                        H5SessionTabBar h5SessionTabBar = H5Activity.this.i.getH5SessionTabBar();
                        if (h5SessionTabBar != null) {
                            success = h5SessionTabBar.createSessionTab(data, H5Activity.this, H5Activity.this.a);
                        }
                    }
                    if (!success) {
                        H5Activity.this.c.addFragment(H5Activity.this.a, false, false);
                        H5Activity.this.j();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void j() {
        H5Log.e(this.TAG, (String) "showSessionTabErrorToast");
        if (Nebula.DEBUG) {
            Toast.makeText(this, H5Environment.getResources().getString(R.string.h5_sessiontab_toast), 1).show();
        }
        H5Logger.reportTabBarLog(this.a, "H5_AL_TABBAR_ERROR", "H5-EM");
    }

    /* access modifiers changed from: private */
    public void k() {
        H5Log.e(this.TAG, (String) "showSessionTabErrorDialog");
        if (TextUtils.equals("YES", H5Environment.getConfig("showSessionTabErrorDialog"))) {
            final H5DialogManagerProvider h5DialogManagerProvider = (H5DialogManagerProvider) H5Utils.getProvider(H5DialogManagerProvider.class.getName());
            if (h5DialogManagerProvider != null) {
                Dialog dialog = h5DialogManagerProvider.createDialog(this, H5Environment.getResources().getString(R.string.h5_sessiontab_notice_failtitle), H5Environment.getResources().getString(R.string.h5_sessiontab_notice_failmsg), H5Environment.getResources().getString(R.string.h5_default_confirm), null, null);
                h5DialogManagerProvider.setPositiveListener(new OnClickPositiveListener() {
                    public void onClick() {
                        h5DialogManagerProvider.disMissDialog();
                        h5DialogManagerProvider.release();
                        if (H5Utils.isInTinyProcess()) {
                            H5EventHandler h5EventHandlerService = (H5EventHandler) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                            if (h5EventHandlerService != null) {
                                h5EventHandlerService.moveTaskToBackAndStop(H5Activity.this, false);
                                return;
                            }
                            return;
                        }
                        H5Activity.this.finish();
                    }
                });
                if (dialog != null) {
                    dialog.setOnKeyListener(new OnKeyListener() {
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == 4) {
                                return true;
                            }
                            return false;
                        }
                    });
                }
                h5DialogManagerProvider.showDialog();
            }
        }
        H5Logger.reportTabBarLog(this.a, "H5_AL_TABBAR_WARN", H5Logger.LOG_HEADER_VM);
    }

    public void hideLoadingView() {
        H5Log.d(this.TAG, "hide loading view");
        if (!(this.h == null || this.g == null)) {
            this.h.stopLoading(this);
            ViewGroup rootView = (ViewGroup) findViewById(R.id.h5_fragment);
            if (rootView != null) {
                rootView.removeView(this.g);
            }
            this.g = null;
        }
        stopLoading();
        if (this.j != null) {
            this.j.dismiss();
            this.j = null;
        }
    }

    /* access modifiers changed from: private */
    public void a(String from) {
        this.b = false;
        H5Page h5Page = g();
        if (h5Page == null || h5Page.getBridge() == null) {
            H5Log.d(this.TAG, "h5page==null");
            return;
        }
        H5Log.d(this.TAG, "appPause from " + from);
        h5Page.getBridge().sendToWeb(this.o, null, null);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        try {
            super.onPause();
            this.m = false;
            this.b = true;
            H5Log.d(this.TAG, "onPause " + this);
            if (g() != null) {
                g().sendEvent(CommonEvents.H5_SESSION_PAUSE, null);
            }
        } catch (Throwable e2) {
            H5Log.e(this.TAG, e2);
        }
    }

    public boolean isTabContainerVisible() {
        if (findViewById(R.id.h5_sessiontab_stub) != null) {
            return false;
        }
        View container = findViewById(R.id.h5_sessiontabcontainer);
        if (container == null || container.getVisibility() != 0) {
            return false;
        }
        return true;
    }

    public View getSessionTabContainer() {
        if (this.q == null) {
            ViewStub tabStub = (ViewStub) findViewById(R.id.h5_sessiontab_stub);
            if (tabStub != null) {
                this.q = (RelativeLayout) tabStub.inflate();
            } else {
                this.q = (RelativeLayout) findViewById(R.id.h5_sessiontabcontainer);
            }
        }
        return this.q;
    }

    public boolean canUsePreRender() {
        if (H5Utils.getBoolean(this.a, (String) "isTinyApp", false) || this.l) {
            return true;
        }
        return false;
    }

    public void openPreRenderByPlugin(boolean openPreRenderByPlugin) {
        this.l = openPreRenderByPlugin;
    }

    public boolean isBetweenResumePause() {
        return this.m;
    }

    public boolean onKeyDown(int keyCode, KeyEvent intent) {
        Fragment fragment = getCurrentFragment();
        if (fragment == null || !(fragment instanceof H5Fragment)) {
            return super.onKeyDown(keyCode, intent);
        }
        return ((H5Fragment) fragment).onKeyDown(keyCode, intent);
    }

    public Fragment getCurrentFragment() {
        return this.c.getCurrentFragment();
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            H5Log.d(this.TAG, "onSaveInstanceState ");
            try {
                outState.putString("savedInstanceStateKey", this.a);
            } catch (Throwable throwable) {
                H5Log.e(this.TAG, throwable);
            }
        }
    }

    public void finish() {
        String appId = H5Utils.getString(this.a, (String) "appId");
        if (this.s || ("yes".equalsIgnoreCase(H5Utils.getString(this.a, (String) "onlyOptionMenu")) && "yes".equals(H5Utils.getString(this.a, (String) "exitAppPair")) && H5Utils.canTransferH5ToTiny(appId))) {
            H5Log.d(this.TAG, "finish needAnimInTiny true");
            super.finish();
            if (this.a != null) {
                this.a.remove("exitAppPair");
            }
            overridePendingTransition(getResources().getIdentifier("tiny_fading_in", ResUtils.ANIM, H5Utils.getContext().getPackageName()), getResources().getIdentifier("tiny_push_down_out", ResUtils.ANIM, H5Utils.getContext().getPackageName()));
        } else if (H5Utils.getBoolean(this.a, (String) H5Param.LONG_CLOSE_ALL_ACTIVITY__ANIMATION, false)) {
            super.finish();
        } else if (H5Utils.getBoolean(this.a, (String) H5Param.SHOW_ACTIVITY_FINISH_ANIMATION, true)) {
            super.finish();
            H5Log.d(this.TAG, "finish:" + this);
            if (NebulaUtil.isShowTransAnimate(this.a)) {
                H5AnimatorUtil.setActivityFadingFinish();
            } else if (H5AnimatorUtil.presentWithAnimation(this.a)) {
                H5Log.d(this.TAG, "setActivityPresentFinish finish");
                H5AnimatorUtil.setActivityPresentFinish();
            } else {
                H5AnimatorUtil.setActivityFinish(this, this.a);
            }
        } else if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_showActivityFinishAnimation"))) {
            getWindow().setWindowAnimations(0);
            new Handler(getMainLooper()).postDelayed(new Runnable() {
                public void run() {
                    try {
                        H5Activity.super.finish();
                        H5Log.d(H5Activity.this.TAG, "finish:" + this);
                    } catch (Throwable e) {
                        H5Log.e(H5Activity.this.TAG, e);
                    }
                }
            }, 1);
        } else {
            super.finish();
            H5Log.d(this.TAG, "finish:" + this);
        }
    }

    public H5FragmentManager getH5FragmentManager() {
        return this.c;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (canUsePreRender()) {
            return H5PreRenderPool.getInstance().isIntercept() || super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public String getPageSpmId() {
        return null;
    }

    public Map<String, String> getExtParam() {
        return null;
    }

    public boolean isTrackPage() {
        return false;
    }

    private Bundle l() {
        if (this.r && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_isOnSavedInstanceInTinyProcess"))) {
            if (this.v != null) {
                H5Log.d(this.TAG, "getIntentParam  " + this.v);
                return this.v;
            }
            MicroApplication app = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp();
            H5Log.d(this.TAG, "getIntentParam " + app);
            if (app != null) {
                String appId = app.getAppId();
                if (!TextUtils.isEmpty(appId)) {
                    this.v = H5IntentUtil.removeParam(appId);
                    H5Log.d(this.TAG, "getIntentParam appId:" + appId + Token.SEPARATOR + this.v);
                    if (this.v != null) {
                        return this.v;
                    }
                }
            }
            if (app != null) {
                H5LogUtil.logNebulaTech(H5LogData.seedId("h5_isOnSavedInstanceInTinyProcess"));
            }
        }
        return getIntent().getExtras();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0007, code lost:
        if (r8.get() == null) goto L_0x0009;
     */
    @android.annotation.SuppressLint({"ResourceType"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean b(java.lang.String r6, com.alipay.mobile.nebulacore.ui.H5Activity r7, java.lang.ref.WeakReference<android.app.Activity> r8) {
        /*
            r1 = 0
            if (r8 == 0) goto L_0x0009
            java.lang.Object r3 = r8.get()     // Catch:{ Throwable -> 0x0054 }
            if (r3 != 0) goto L_0x0015
        L_0x0009:
            com.alipay.mobile.framework.LauncherApplicationAgent r3 = com.alipay.mobile.framework.LauncherApplicationAgent.getInstance()     // Catch:{ Throwable -> 0x0054 }
            com.alipay.mobile.framework.MicroApplicationContext r3 = r3.getMicroApplicationContext()     // Catch:{ Throwable -> 0x0054 }
            java.lang.ref.WeakReference r8 = r3.getTopActivity()     // Catch:{ Throwable -> 0x0054 }
        L_0x0015:
            if (r8 == 0) goto L_0x0041
            if (r7 == 0) goto L_0x0041
            java.lang.Object r2 = r8.get()     // Catch:{ Throwable -> 0x0054 }
            android.app.Activity r2 = (android.app.Activity) r2     // Catch:{ Throwable -> 0x0054 }
            if (r2 == 0) goto L_0x0041
            if (r2 == r7) goto L_0x0041
            android.view.Window r3 = r2.getWindow()     // Catch:{ Throwable -> 0x0054 }
            if (r3 == 0) goto L_0x0041
            android.view.Window r3 = r2.getWindow()     // Catch:{ Throwable -> 0x0054 }
            android.content.res.TypedArray r3 = r3.getWindowStyle()     // Catch:{ Throwable -> 0x0054 }
            if (r3 == 0) goto L_0x0041
            android.view.Window r3 = r2.getWindow()     // Catch:{ Throwable -> 0x0054 }
            android.content.res.TypedArray r3 = r3.getWindowStyle()     // Catch:{ Throwable -> 0x0054 }
            r4 = 5
            r5 = 0
            boolean r1 = r3.getBoolean(r4, r5)     // Catch:{ Throwable -> 0x0054 }
        L_0x0041:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "isTopActivityTranslucent isTranslucent  : "
            r3.<init>(r4)
            java.lang.StringBuilder r3 = r3.append(r1)
            java.lang.String r3 = r3.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r6, r3)
            return r1
        L_0x0054:
            r0 = move-exception
            java.lang.String r3 = "isTopActivityTranslucent Exception : "
            com.alipay.mobile.nebula.util.H5Log.e(r6, r3, r0)
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.ui.H5Activity.b(java.lang.String, com.alipay.mobile.nebulacore.ui.H5Activity, java.lang.ref.WeakReference):boolean");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        H5Session h5Session = getH5Session();
        if (h5Session != null) {
            Iterator it = h5Session.getPages().iterator();
            while (it.hasNext()) {
                H5EmbededViewProvider embededViewProvider = ((H5Page) it.next()).getEmbededViewProvider();
                if (embededViewProvider != null) {
                    embededViewProvider.onRequestPermissionResult(requestCode, permissions, grantResults);
                }
            }
        }
    }
}
