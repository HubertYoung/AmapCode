package com.alipay.mobile.framework.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Pair;
import com.alipay.mobile.aspect.FrameworkPointcutExecution;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.exception.StartActivityRecord;
import com.alipay.mobile.quinox.utils.TraceLogger;

class QuinoxActivity extends Activity {
    public static final String TAG = "QuinoxActivity";
    private volatile boolean a = false;

    QuinoxActivity() {
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        TraceLogger.w((String) TAG, getClass().getSimpleName() + " replaceResources().");
        replaceResources(base);
    }

    /* access modifiers changed from: protected */
    public void replaceResources(Context base) {
        if (ActivityConstants.replace) {
            LauncherApplicationAgent.getmBundleContext().replaceResources(base, getClass().getName(), ActivityConstants.bundleNames);
            return;
        }
        if (!ActivityConstants.judged) {
            if ("com.eg.android.AlipayGphone".equals(base.getPackageName())) {
                ActivityConstants.replace = true;
            }
            ActivityConstants.judged = true;
        }
        LauncherApplicationAgent.getmBundleContext().replaceResources(base, getClass().getName(), new String[0]);
    }

    public void startActivity(Intent intent) {
        Object[] args = {intent};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEACTIVITY_STARTACTIVITY, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEACTIVITY_STARTACTIVITY, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a(intent, -1);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEACTIVITY_STARTACTIVITY, this, args);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        Object[] args = {intent, Integer.valueOf(requestCode)};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEACTIVITY_STARTACTIVITYFORRESULT, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEACTIVITY_STARTACTIVITYFORRESULT, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a(intent, requestCode);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEACTIVITY_STARTACTIVITYFORRESULT, this, args);
    }

    private void a(Intent intent, int requestCode) {
        TraceLogger.w((String) TAG, (Throwable) new StartActivityRecord("startActivityForResult(intent=" + intent + ", code=" + requestCode + ")"));
        super.startActivityForResult(intent, requestCode);
    }

    public ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }

    public Resources getResources() {
        Resources res = super.getResources();
        if (!this.a) {
            TraceLogger.i((String) TAG, "getResources:" + res);
            this.a = true;
        }
        return res;
    }
}
