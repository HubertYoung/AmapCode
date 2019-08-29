package com.alipay.mobile.framework.app.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.alipay.mobile.aspect.FrameworkPointcutExecution;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.ActivityApplication;
import com.alipay.mobile.framework.loading.LoadingView;
import com.alipay.mobile.framework.permission.RequestPermissionsResultCallback;
import com.alipay.mobile.framework.service.ext.ExternalService;
import com.alipay.mobile.quinox.utils.ContentResolvers;
import com.alipay.mobile.quinox.utils.TraceLogger;

public abstract class BaseFragmentActivity extends QuinoxFragmentActivity implements ActivityResponsable, ActivityTrackable {
    static final String TAG = BaseFragmentActivity.class.getSimpleName();
    protected ActivityHelper mActivityHelper;
    protected ActivityApplication mApp;
    protected MicroApplicationContext mMicroApplicationContext;

    public /* bridge */ /* synthetic */ ClassLoader getClassLoader() {
        return super.getClassLoader();
    }

    public /* bridge */ /* synthetic */ Resources getResources() {
        return super.getResources();
    }

    public /* bridge */ /* synthetic */ void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    public /* bridge */ /* synthetic */ void startActivityForResult(Intent intent, int i) {
        super.startActivityForResult(intent, i);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean bRet = false;
        Object[] args = {ev};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_DISPATCHTOUCHEVENT, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_DISPATCHTOUCHEVENT, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            bRet = a(ev);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_DISPATCHTOUCHEVENT, this, args);
        return bRet;
    }

    private boolean a(MotionEvent ev) {
        if (this.mActivityHelper != null) {
            this.mActivityHelper.dispatchOnTouchEvent(ev);
        }
        boolean bRet = false;
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Throwable e) {
            TraceLogger.w(TAG, e);
            return bRet;
        }
    }

    public boolean dispatchKeyEvent(KeyEvent ev) {
        boolean bRet = false;
        Object[] args = {ev};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_DISPATCHKEYEVENT, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_DISPATCHKEYEVENT, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            bRet = _dispatchKeyEvent(ev);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_DISPATCHKEYEVENT, this, args);
        return bRet;
    }

    public boolean _dispatchKeyEvent(KeyEvent ev) {
        boolean bRet = false;
        try {
            return super.dispatchKeyEvent(ev);
        } catch (Throwable e) {
            TraceLogger.w(TAG, e);
            return bRet;
        }
    }

    public ActivityApplication getActivityApplication() {
        return this.mApp;
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"NewApi"})
    public void onCreate(Bundle savedInstanceState) {
        Object[] args = {savedInstanceState};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ONCREATE, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ONCREATE, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a(savedInstanceState);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ONCREATE, this, args);
    }

    public View findViewById(int id) {
        return super.findViewById(id);
    }

    private void a(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TraceLogger.d((String) "dynamicLoadToCheck", this + "/" + getApplicationContext() + "/" + Thread.currentThread().getContextClassLoader() + "/" + getClassLoader());
        this.mActivityHelper = new ActivityHelper(this);
        this.mApp = this.mActivityHelper.getApp();
        this.mMicroApplicationContext = this.mActivityHelper.getMicroApplicationContext();
        if (TextUtils.equals(Build.DEVICE, "M040") && VERSION.SDK_INT >= 11) {
            getWindow().getDecorView().setLayerType(1, null);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Object[] args = new Object[0];
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ONRESUME, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ONRESUME, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ONRESUME, this, args);
    }

    private void a() {
        super.onResume();
        if (this.mActivityHelper != null) {
            this.mActivityHelper.onResume();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        Object[] args = new Object[0];
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ONPAUSE, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ONPAUSE, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            b();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ONPAUSE, this, args);
    }

    private void b() {
        super.onPause();
        if (this.mActivityHelper != null) {
            this.mActivityHelper.onPause();
        }
    }

    public void onBackPressed() {
        Object[] args = new Object[0];
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ONBACKPRESSED, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ONBACKPRESSED, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            c();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ONBACKPRESSED, this, args);
    }

    private void c() {
        try {
            super.onBackPressed();
        } catch (Exception e) {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        Object[] args = {intent};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ONNEWINTENT, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ONNEWINTENT, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a(intent);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ONNEWINTENT, this, args);
    }

    private void a(Intent intent) {
        super.onNewIntent(intent);
        if (this.mActivityHelper != null) {
            this.mActivityHelper.onNewIntent(intent);
        }
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        if (this.mActivityHelper != null) {
            this.mActivityHelper.onUserInteraction();
        }
    }

    /* access modifiers changed from: protected */
    public void onUserLeaveHint() {
        Object[] args = new Object[0];
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ONUSERLEAVEHINT, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ONUSERLEAVEHINT, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            d();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ONUSERLEAVEHINT, this, args);
    }

    private void d() {
        super.onUserLeaveHint();
        if (this.mActivityHelper != null) {
            this.mActivityHelper.onUserLeaveHint();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        Bundle bundle = null;
        if (this.mApp != null && !TextUtils.isEmpty(this.mApp.getAppId())) {
            bundle = new Bundle();
            bundle.putString("appId", this.mApp.getAppId());
        }
        Object[] args = {Boolean.valueOf(hasFocus), bundle};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ONWINDOWFOCUSCHANGED, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ONWINDOWFOCUSCHANGED, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a(hasFocus);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ONWINDOWFOCUSCHANGED, this, args);
    }

    private void a(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (this.mActivityHelper != null) {
            this.mActivityHelper.onWindowFocusChanged(hasFocus);
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        Object[] args = {outState};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ONSAVEINSTANCESTATE, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ONSAVEINSTANCESTATE, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            b(outState);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ONSAVEINSTANCESTATE, this, args);
    }

    private void b(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.mActivityHelper != null) {
            this.mActivityHelper.onSaveInstanceState(outState);
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        Object[] args = new Object[0];
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ONSTART, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ONSTART, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            e();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ONSTART, this, args);
    }

    private void e() {
        super.onStart();
        if (this.mActivityHelper != null) {
            this.mActivityHelper.onStart();
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        Object[] args = new Object[0];
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ONSTOP, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ONSTOP, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            f();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ONSTOP, this, args);
    }

    private void f() {
        super.onStop();
        if (this.mActivityHelper != null) {
            this.mActivityHelper.onStop();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        Object[] args = new Object[0];
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ONDESTROY, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ONDESTROY, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            g();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ONDESTROY, this, args);
    }

    private void g() {
        super.onDestroy();
        if (this.mActivityHelper != null) {
            this.mActivityHelper.onDestroy();
        }
    }

    public void onContentChanged() {
        Object[] args = new Object[0];
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ONCONTENTCHANGED, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ONCONTENTCHANGED, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            h();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ONCONTENTCHANGED, this, args);
    }

    private void h() {
        if (this.mActivityHelper != null) {
            this.mActivityHelper.onContentChanged();
        }
        super.onContentChanged();
    }

    public void finish() {
        Object[] args = new Object[0];
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_FINISH, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_FINISH, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            i();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_FINISH, this, args);
    }

    private void i() {
        if (this.mActivityHelper != null) {
            this.mActivityHelper.finish();
        }
        if (this.mActivityHelper == null || !this.mActivityHelper.isBehindTranslucentActivity() || VERSION.SDK_INT < 25) {
            try {
                super.finish();
            } catch (Throwable e) {
                TraceLogger.w(TAG, e);
            }
        } else {
            TraceLogger.w(TAG, (String) "delay finish when behind translucent activity");
            final Handler handler = new Handler(getMainLooper());
            handler.postAtFrontOfQueue(new Runnable() {
                public void run() {
                    BaseFragmentActivity.this.getWindow().setWindowAnimations(0);
                    handler.post(new Runnable() {
                        public void run() {
                            TraceLogger.i(BaseFragmentActivity.TAG, (String) "do finish after delay");
                            AnonymousClass1.super.finish();
                        }
                    });
                }
            });
        }
    }

    @Deprecated
    public void alert(String title, String msg, String positive, OnClickListener positiveListener, String negative, OnClickListener negativeListener) {
        Object[] args = {title, msg, positive, positiveListener, negative, negativeListener};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ALERT1, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ALERT1, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            this.mActivityHelper.alert(title, msg, positive, positiveListener, negative, negativeListener);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ALERT1, this, args);
    }

    @Deprecated
    public void alert(String title, String msg, String positive, OnClickListener positiveListener, String negative, OnClickListener negativeListener, Boolean isCanceledOnTouchOutside) {
        Object[] args = {title, msg, positive, positiveListener, negative, negativeListener, isCanceledOnTouchOutside};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ALERT2, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ALERT2, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            this.mActivityHelper.alert(title, msg, positive, positiveListener, negative, negativeListener, isCanceledOnTouchOutside);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ALERT2, this, args);
    }

    @Deprecated
    public void alert(String title, String msg, String positive, OnClickListener positiveListener, String negative, OnClickListener negativeListener, Boolean isCanceledOnTouchOutside, Boolean cancelable) {
        Object[] args = {title, msg, positive, positiveListener, negative, negativeListener, isCanceledOnTouchOutside, cancelable};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ALERT3, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ALERT3, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            this.mActivityHelper.alert(title, msg, positive, positiveListener, negative, negativeListener, isCanceledOnTouchOutside, cancelable);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ALERT3, this, args);
    }

    @Deprecated
    public void toast(String msg, int period) {
        Object[] args = {msg, Integer.valueOf(period)};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_TOAST, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_TOAST, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a(msg, period);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_TOAST, this, args);
    }

    @Deprecated
    private void a(String msg, int period) {
        this.mActivityHelper.toast(msg, period);
    }

    public void showProgressDialog(String msg) {
        this.mActivityHelper.showProgressDialog(msg);
    }

    public void showProgressDialog(String msg, boolean cancelable, OnCancelListener cancelListener) {
        this.mActivityHelper.showProgressDialog(msg, cancelable, cancelListener);
    }

    public void dismissProgressDialog() {
        this.mActivityHelper.dismissProgressDialog();
    }

    /* access modifiers changed from: protected */
    public <T> T findServiceByInterface(String interfaceName) {
        return this.mActivityHelper.findServiceByInterface(interfaceName);
    }

    /* access modifiers changed from: protected */
    public <T extends ExternalService> T getExtServiceByInterface(String className) {
        return this.mActivityHelper.getExtServiceByInterface(className);
    }

    public String getActivityTrackId() {
        return getClass().getName();
    }

    public String getAppTrackId() {
        if (this.mApp != null) {
            return this.mApp.getAppId();
        }
        return null;
    }

    public String getSourceTrackId() {
        if (this.mApp != null) {
            return this.mApp.getSourceId();
        }
        return null;
    }

    @TargetApi(23)
    public void requestPermissions(String[] permissions, int requestCode, RequestPermissionsResultCallback callback) {
        if (VERSION.SDK_INT >= 23) {
            if (this.mActivityHelper != null) {
                this.mActivityHelper.requestPermissions(permissions, requestCode, callback);
            }
            requestPermissions(permissions, requestCode);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (this.mActivityHelper != null) {
            this.mActivityHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void setContentView(int layoutResID) {
        Object[] args = {Integer.valueOf(layoutResID)};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_SETCONTENTVIEW1, this, args);
        super.setContentView(layoutResID);
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_SETCONTENTVIEW1, this, args);
    }

    public void setContentView(View view) {
        Object[] args = {view};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_SETCONTENTVIEW2, this, args);
        super.setContentView(view);
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_SETCONTENTVIEW2, this, args);
    }

    public void setContentView(View view, LayoutParams params) {
        Object[] args = {view, params};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_SETCONTENTVIEW3, this, args);
        super.setContentView(view, params);
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_SETCONTENTVIEW3, this, args);
    }

    public ContentResolver getContentResolver() {
        ContentResolver cr = super.getContentResolver();
        ContentResolvers.fixTargetSdkInParallel(cr);
        return cr;
    }

    public void addLoadingView(LoadingView loadingView) {
        if (this.mActivityHelper != null) {
            this.mActivityHelper.addLoadingView(loadingView);
        }
    }

    public boolean startLoading() {
        if (this.mActivityHelper != null) {
            return this.mActivityHelper.startLoading();
        }
        return false;
    }

    public boolean stopLoading() {
        if (this.mActivityHelper != null) {
            return this.mActivityHelper.stopLoading();
        }
        return false;
    }

    public void onReady(Bundle bundle) {
        if (this.mApp != null && !TextUtils.isEmpty(this.mApp.getAppId())) {
            if (bundle == null) {
                bundle = new Bundle();
                bundle.putString("appId", this.mApp.getAppId());
            } else if (!bundle.containsKey("appId")) {
                bundle.putString("appId", this.mApp.getAppId());
            }
        }
        Object[] args = {bundle};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENTACTIVITY_ONREADY, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENTACTIVITY_ONREADY, this, args);
        if (aroundResult != null) {
            ((Boolean) aroundResult.first).booleanValue();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENTACTIVITY_ONREADY, this, args);
    }

    public void setRequestedOrientation(int requestedOrientation) {
        try {
            super.setRequestedOrientation(requestedOrientation);
        } catch (Throwable e) {
            TraceLogger.w(TAG, e);
        }
    }
}
