package com.autonavi.wing;

import android.app.Application;
import android.content.Context;

public class WingApplication extends Application {
    protected static esj mWingContext = new esj();
    private boolean isAppLauched = false;

    /* access modifiers changed from: protected */
    public boolean isMainAppReady() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onApplicationCreate() {
    }

    /* access modifiers changed from: protected */
    public void onAttachBaseContext(Context context) {
    }

    /* access modifiers changed from: protected */
    public final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        la.b("SystemUp");
        onAttachBaseContext(this);
        la.b("MultiDex Installed");
        mWingContext.a = this;
        esg.a().c = mWingContext;
        a.a.a = mWingContext;
        esf.a().a = mWingContext;
        la.b("WingFramework Initialized");
    }

    public final void onCreate() {
        super.onCreate();
        onApplicationCreate();
        if (isMainProcess()) {
            la.b("Launcher");
        }
        if (isMainAppReady()) {
            esg.a().b();
            if (isMainProcess()) {
                la.b("VAppClassLoad");
            }
            esg.a().c();
            if (isMainProcess()) {
                la.b("VAppInitial");
            }
            this.isAppLauched = true;
        }
    }

    /* access modifiers changed from: protected */
    public final void restart() {
        if (!this.isAppLauched) {
            esg.a().b();
            esg.a().c();
            this.isAppLauched = true;
        }
    }

    public final boolean isMainProcess() {
        return ahs.a(this);
    }
}
