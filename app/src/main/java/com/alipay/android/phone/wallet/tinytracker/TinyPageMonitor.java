package com.alipay.android.phone.wallet.tinytracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.phone.wallet.spmtracker.Constant;
import com.alipay.android.phone.wallet.spmtracker.ITinyPageMonitor;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

enum TinyPageMonitor implements ITinyPageMonitor {
    ;
    
    /* access modifiers changed from: private */
    public final String a;
    /* access modifiers changed from: private */
    public LeaveHintReceiver b;
    /* access modifiers changed from: private */
    public Context c;
    private WeakReference<Object> d;

    class LeaveHintReceiver extends BroadcastReceiver {
        LeaveHintReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            SpmLogCator.debug(TinyPageMonitor.this.a, "LeaveHintReceiver onReceive:" + intent.getAction());
            TinyTrackIntegrator.getInstance().setmIsLeaveHint(true);
        }
    }

    private TinyPageMonitor(String str) {
        this.a = TinyPageMonitor.class.getSimpleName();
        a();
    }

    public final void setContext(Context context) {
        this.c = context;
    }

    public final void pageOnResume(Object page, String spmId) {
        SpmLogCator.debug(this.a, "pageOnResume spmId:" + spmId);
        if (!TextUtils.isEmpty(spmId)) {
            TinyTrackIntegrator.getInstance().logPageStartWithSpmId(spmId, page);
        }
    }

    public final void pageOnPause(Object page, String spmId, String bizCode, Map<String, String> map, String chInfo) {
        SpmLogCator.debug(this.a, "pageOnPause spmId:" + spmId + ";chInfo:" + chInfo);
        if (!TextUtils.isEmpty(spmId)) {
            TinyTrackIntegrator.getInstance().logPageEndWithSpmId(spmId, page, bizCode, a(map, chInfo));
        }
    }

    public final void pageOnPause(Object page, String spmId, String bizCode, Map<String, String> map) {
        pageOnPause(page, spmId, bizCode, map, null);
    }

    public final void pageOnDestroy(Object page) {
        String pageKey = SpmUtils.getViewKey(page);
        TrackerHelper.instance.onPageDestroy(pageKey);
        TinyTrackIntegrator.getInstance().pageOnDestroy(pageKey);
    }

    /* access modifiers changed from: 0000 */
    public final void setmTopPage(Object page) {
        this.d = new WeakReference<>(page);
    }

    public final void setPageParams(String s, int i) {
        TinyTrackIntegrator.getInstance().setPageParams(s, this.d == null ? null : this.d.get(), i);
    }

    private HashMap<String, String> a(Map<String, String> bizParams, String chInfo) {
        HashMap outParams = new HashMap();
        if (bizParams != null && !bizParams.isEmpty()) {
            if (SpmUtils.isDebug && bizParams.containsKey(Constant.KEY_FROMHOME)) {
                throw new IllegalArgumentException("\"fromHome\"为保留字段，扩展参数中key不能使用\"fromHome\"");
            } else if (!SpmUtils.isDebug || !bizParams.containsKey(Constant.KEY_PAGEBACK)) {
                if (SpmUtils.isDebug && bizParams.containsKey("chInfo")) {
                    LoggerFactory.getTraceLogger().error(this.a, (String) "\"chInfo\"为保留字段，扩展参数中key不能使用\"chInfo\"");
                }
                if (SpmUtils.isDebug && bizParams.containsKey("srcSpm")) {
                    throw new IllegalArgumentException("\"srcSpm\"为保留字段，扩展参数中key不能使用\"srcSpm\"");
                } else if (!SpmUtils.isDebug || !bizParams.containsKey(Constant.KEY_REFER_SPM)) {
                    if (SpmUtils.isDebug && bizParams.containsKey(Constant.KEY_LANINFO)) {
                        LoggerFactory.getTraceLogger().error(this.a, (String) "\"laninfo\"为保留字段，扩展参数中key不能使用\"chInfo\"");
                    }
                    outParams.putAll(bizParams);
                } else {
                    throw new IllegalArgumentException("\"referSpm\"为保留字段，扩展参数中key不能使用\"referSpm\"");
                }
            } else {
                throw new IllegalArgumentException("\"pageBack\"为保留字段，扩展参数中key不能使用\"pageBack\"");
            }
        }
        if (!TextUtils.isEmpty(chInfo)) {
            outParams.put("chInfo", chInfo);
        }
        return outParams;
    }

    public final void setCurrentPageInfo(Parcelable parcelable) {
    }

    public static boolean isH5Page(Object page) {
        return page instanceof String;
    }

    private synchronized void a() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                try {
                    if (TinyPageMonitor.this.b == null && TinyPageMonitor.this.c != null) {
                        TinyPageMonitor.this.b = new LeaveHintReceiver();
                        LocalBroadcastManager.getInstance(TinyPageMonitor.this.c).registerReceiver(TinyPageMonitor.this.b, new IntentFilter("com.alipay.mobile.framework.USERLEAVEHINT"));
                    }
                } catch (Exception e) {
                    Log.e(TinyPageMonitor.this.a, "registerHomePressReceiver exception:" + e.toString());
                } catch (Error error) {
                    Log.e(TinyPageMonitor.this.a, "registerHomePressReceiver error:" + error.toString());
                }
            }
        }, 1000);
    }
}
