package com.alipay.mobile.nebulacore.wallet;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.msg.MsgCodeConstants;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.ref.WeakReference;

public class H5WalletPageNotifyPlugin extends H5SimplePlugin {
    public static final String TAG = "H5WalletPageNotifyPlugin";
    /* access modifiers changed from: private */
    public String a;
    private boolean b = false;
    /* access modifiers changed from: private */
    public H5Page c;
    private WeakReference<Activity> d;
    private BroadcastReceiver e = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            H5Log.debug(H5WalletPageNotifyPlugin.TAG, "received handleResumeListen");
            if (intent != null && intent.getExtras() != null && intent.getAction() != null && H5WalletPageNotifyPlugin.this.c != null) {
                H5Log.d(H5WalletPageNotifyPlugin.TAG, "onReceive:" + intent.getAction() + Token.SEPARATOR + intent.getExtras().getString("app_id"));
                if (MsgCodeConstants.FRAMEWORK_ACTIVITY_RESUME.equalsIgnoreCase(intent.getAction())) {
                    if (TextUtils.isEmpty(H5WalletPageNotifyPlugin.this.a)) {
                        H5WalletPageNotifyPlugin.this.a = intent.getExtras().getString("app_id");
                    }
                    if (TextUtils.equals(intent.getExtras().getString("app_id"), H5WalletPageNotifyPlugin.this.a) && H5WalletPageNotifyPlugin.this.c()) {
                        H5Log.d(H5WalletPageNotifyPlugin.TAG, "createPage resume");
                        H5WalletPageNotifyPlugin.this.c.sendEvent(CommonEvents.H5_PAGE_RESUME, null);
                    }
                } else if (MsgCodeConstants.FRAMEWORK_ACTIVITY_PAUSE.equalsIgnoreCase(intent.getAction()) && H5WalletPageNotifyPlugin.this.c()) {
                    H5Log.d(H5WalletPageNotifyPlugin.TAG, "createPage pause");
                    H5WalletPageNotifyPlugin.this.c.sendEvent(CommonEvents.H5_PAGE_PAUSE, null);
                }
            }
        }
    };

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.H5_PAGE_STARTED);
    }

    public void onRelease() {
        this.c = null;
        if (this.b) {
            try {
                H5Log.d(TAG, "unregister broadcastreceiver");
                d();
            } catch (Throwable r) {
                H5Log.e((String) TAG, r);
            }
        }
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        if (event.getTarget() instanceof H5Page) {
            this.c = (H5Page) event.getTarget();
        }
        this.d = new WeakReference<>(event.getActivity());
        if (this.d.get() == null || ((Activity) this.d.get()).isFinishing() || !(this.d.get() instanceof H5Activity)) {
            H5Log.debug(TAG, "current activity is not H5Activity registerListen");
            a();
        } else {
            H5Log.debug(TAG, "current activity is H5Activity");
        }
        return false;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        return false;
    }

    private void a() {
        if (this.b) {
            H5Log.e((String) TAG, (String) "hasRegistered not register");
            return;
        }
        this.b = true;
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(H5Utils.getContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(MsgCodeConstants.FRAMEWORK_ACTIVITY_RESUME);
        filter.addAction(MsgCodeConstants.FRAMEWORK_ACTIVITY_PAUSE);
        H5Log.d(TAG, "registerFrameWorkListen");
        manager.registerReceiver(this.e, filter);
    }

    private static boolean b() {
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("H5_createPage_listen"))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean c() {
        try {
            if (!b()) {
                return true;
            }
            Activity currentTop = (Activity) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
            if (currentTop == null || this.d == null || this.d.get() == null) {
                H5Log.d(TAG, "not match: " + currentTop + ", weakRef: " + this.d);
                return false;
            }
            String top = H5Utils.getClassName(currentTop);
            String page = H5Utils.getClassName(this.d.get());
            H5Log.d(TAG, "top " + top + " page:" + page);
            if (TextUtils.equals(top, page)) {
                return true;
            }
            return false;
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
    }

    private void d() {
        if (this.e != null) {
            LocalBroadcastManager.getInstance(H5Utils.getContext()).unregisterReceiver(this.e);
            this.e = null;
        }
    }
}
