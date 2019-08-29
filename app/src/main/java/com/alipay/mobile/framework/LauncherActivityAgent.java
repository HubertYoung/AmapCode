package com.alipay.mobile.framework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.alipay.mobile.quinox.utils.LogUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;

public class LauncherActivityAgent {
    protected Object mMicroApplicationContext;

    public void attachMicroApplicationContext(Object microApplicationContext) {
        this.mMicroApplicationContext = microApplicationContext;
    }

    public void preInit(Activity activity) {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.preInit()");
    }

    public void postInit(Activity activity) {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.postInit() => startEntryApp()");
        try {
            this.mMicroApplicationContext.getClass().getDeclaredMethod("startEntryApp", new Class[]{Bundle.class}).invoke(this.mMicroApplicationContext, new Object[]{null});
        } catch (Exception e) {
            LogUtil.w((String) "LauncherActivityAgent", (Throwable) e);
        }
    }

    public void attachBaseContext(Context newBase) {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.attachBaseContext()");
    }

    public void onCreate(Bundle savedInstanceState) {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onCreate()");
    }

    public Resources getResources() {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.getResources()");
        return null;
    }

    public AssetManager getAssets() {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.getAssets()");
        return null;
    }

    public void onNewIntent(Intent intent) {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onNewIntent()");
    }

    public void onResume() {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onResume()");
    }

    public void onStart() {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onStart()");
    }

    public void onRestart() {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onRestart()");
    }

    public void onPause() {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onPause()");
    }

    public void onStop() {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onStop()");
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onWindowFocusChanged()");
    }

    public void onSaveInstanceState(Bundle outState) {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onSaveInstanceState()");
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onRestoreInstanceState()");
    }

    public boolean onBackPressed() {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onBackPressed()");
        return false;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.dispatchTouchEvent()");
        return false;
    }

    public ClassLoader getClassLoader() {
        return null;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onKeyDown()");
        return false;
    }

    public void finish() {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.finish()");
    }

    public void onDestroy() {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onDestroy()");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onActivityResult()");
    }

    public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
    }

    public void onUserInteraction() {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onUserInteraction()");
    }

    public void onUserLeaveHint() {
        TraceLogger.d((String) "LauncherActivityAgent", (String) "LauncherActivityAgent.onUserLeaveHint()");
    }
}
