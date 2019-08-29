package com.alipay.mobile.core.loading.impl;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.framework.FrameworkAdapterManager;
import com.alipay.mobile.framework.FrameworkMonitor;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.loading.LoadingPageHandler;
import com.alipay.mobile.framework.loading.LoadingPageManager;
import com.alipay.mobile.framework.loading.LoadingView;
import com.alipay.mobile.framework.loading.LoadingView.OnCancelListener;
import com.alipay.mobile.quinox.utils.SharedPreferenceUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class LoadingPage extends Activity {
    public static final String PARAM_TOKEN = "token";
    private int a;
    private String b;
    /* access modifiers changed from: private */
    public String c;
    private Bundle d;
    private LoadingPageHandler e;
    private LoadingView f;
    /* access modifiers changed from: private */
    public LoadingPageManagerImpl g;
    private Timer h;
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.g = (LoadingPageManagerImpl) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getLoadingPageManager();
        this.a = getIntent().getIntExtra("token", -1);
        LoadingRecord record = this.g.getLoadingPageRecord(this.a);
        if (record == null) {
            TraceLogger.e(LoadingPageManager.TAG, (String) "record is null");
            finish();
        } else if (record.isStopped) {
            TraceLogger.e(LoadingPageManager.TAG, (String) "record is isStopped");
            finish();
        } else {
            record.loadingPage = new WeakReference<>(this);
            this.d = record.params;
            this.b = record.sourceId;
            this.c = record.targetAppId;
            this.e = record.loadingPageHandler;
            this.f = record.loadingView;
            this.h = new Timer(LoadingPageManager.TAG);
            if (this.e == null) {
                TraceLogger.e(LoadingPageManager.TAG, (String) "loadingPageHandler is null");
                finish();
            } else if (this.f == null) {
                TraceLogger.e(LoadingPageManager.TAG, (String) "loadingView is null");
                finish();
            } else {
                setContentView(this.f);
                this.f.setHostActivity(this);
                startLoadingPage();
                a();
                TraceLogger.i(LoadingPageManager.TAG, "LoadingPage.onCreate, token=" + this.a + ", sourceId=" + this.b + ", targetAppId=" + this.c + ", loadingView=" + this.f);
                this.f.setOnCancelListener(new OnCancelListener() {
                    public void onCancel() {
                        LoadingPage.this.cancelLoadingPage();
                    }
                });
            }
        }
    }

    private void a() {
        final String timeout = FrameworkAdapterManager.g().getConfigFromAdapter(SharedPreferenceUtil.CONFIG_KEY_LOADING_PEND_TIMEOUT);
        int timeoutInSec = 0;
        try {
            if (TextUtils.isEmpty(timeout)) {
                timeoutInSec = 0;
            } else {
                timeoutInSec = Integer.parseInt(timeout);
            }
        } catch (Throwable t) {
            TraceLogger.w(LoadingPageManager.TAG, t);
        }
        TraceLogger.i(LoadingPageManager.TAG, "startLongstandingMonitor:" + timeoutInSec);
        if (timeoutInSec >= 5) {
            this.h.schedule(new TimerTask() {
                public void run() {
                    if (LoadingPage.this.g.findLoadingRecordByAppId(LoadingPage.this.c) != null) {
                        TraceLogger.e(LoadingPageManager.TAG, "LoadingPagePending: " + LoadingPage.this.c);
                        FrameworkMonitor.getInstance(null).handleLoadingPagePending(LoadingPage.this.c, timeout);
                    }
                }
            }, TimeUnit.SECONDS.toMillis((long) timeoutInSec));
        }
    }

    public void finish() {
        if (this.h != null) {
            this.h.cancel();
            this.h = null;
        }
        if (isFinishing()) {
            TraceLogger.w(LoadingPageManager.TAG, (String) "skip finish when isFinishing");
            return;
        }
        super.finish();
        if (this.e != null) {
            this.e.onFinishLoadingPage(this, this.b, this.c, this.d);
        }
        TraceLogger.i(LoadingPageManager.TAG, "LoadingPage.finish, token=" + this.a + ",targetAppId=" + this.c);
        this.g.removeLoadingPageRecord(this.a);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (this.i) {
            stopLoadingPage();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        TraceLogger.i(LoadingPageManager.TAG, "LoadingPage.onDestroy, token=" + this.a + ",targetAppId=" + this.c);
        this.g.removeLoadingPageRecord(this.a);
    }

    public void onBackPressed() {
        try {
            super.onBackPressed();
        } catch (Throwable t) {
            TraceLogger.e(LoadingPageManager.TAG, t);
        }
        cancelLoadingPage();
    }

    public void setHasLostFocus(boolean hasLostFocus) {
        this.i = hasLostFocus;
    }

    public LoadingView getLoadingView() {
        if (isFinishing()) {
            return null;
        }
        return this.f;
    }

    public void startLoadingPage() {
        TraceLogger.d(LoadingPageManager.TAG, "LoadingPage.startLoadingPage, token=" + this.a + ",targetAppId=" + this.c);
        this.j = false;
        this.k = false;
        if (this.f != null) {
            this.f.start();
        }
        if (this.e != null) {
            this.e.onCreateLoadingPage(this, this.b, this.c, this.d);
        }
    }

    public void stopLoadingPage() {
        TraceLogger.i(LoadingPageManager.TAG, "LoadingPage.stopLoadingPage, token=" + this.a + ",targetAppId=" + this.c + ",hasNotifyStop=" + this.j);
        if (!this.j) {
            this.j = true;
            if (this.f != null) {
                this.f.stop();
            }
            if (this.e != null) {
                this.e.onStopLoadingPage(this, this.b, this.c, this.d);
            }
        }
        finish();
    }

    public void cancelLoadingPage() {
        TraceLogger.i(LoadingPageManager.TAG, "LoadingPage.cancelLoadingPage, token=" + this.a + ",targetAppId=" + this.c);
        if (!this.k) {
            this.k = true;
            if (this.f != null) {
                this.f.stop();
            }
            if (this.e != null) {
                this.e.onCancelLoadingPage(this, this.b, this.c, this.d);
            }
        }
        finish();
    }
}
