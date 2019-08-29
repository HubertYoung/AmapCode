package com.autonavi.minimap.ajx3.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import java.util.ArrayList;
import java.util.Iterator;

@AjxModule("network")
public final class ModuleNetwork extends AbstractModule {
    private static final String ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String MODULE_NAME = "network";
    private static final int NET_STATUS_2G = 1;
    private static final int NET_STATUS_3G = 2;
    private static final int NET_STATUS_4G = 3;
    private static final int NET_STATUS_DISCONNECTION = 0;
    private static final int NET_STATUS_WIFI = 4;
    private boolean mHasRegisteNetReceiver = false;
    private NetReceiver mNetReceiver;

    static class NetReceiver extends BroadcastReceiver {
        private ArrayList<JsFunctionCallback> jsFunctionCallback = new ArrayList<>();
        private ModuleNetwork mJsNetworkService;

        public NetReceiver(ModuleNetwork moduleNetwork) {
            this.mJsNetworkService = moduleNetwork;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null && ModuleNetwork.ACTION_CONNECTIVITY_CHANGE.equals(intent.getAction())) {
                ModuleNetwork moduleNetwork = this.mJsNetworkService;
                if (this.jsFunctionCallback.size() > 0 && moduleNetwork != null) {
                    Iterator<JsFunctionCallback> it = this.jsFunctionCallback.iterator();
                    while (it.hasNext()) {
                        it.next().callback(Integer.valueOf(moduleNetwork.getInnerNetStatue()));
                    }
                }
            }
        }

        public void addCallback(JsFunctionCallback jsFunctionCallback2) {
            this.jsFunctionCallback.add(jsFunctionCallback2);
        }

        public void clearCallback() {
            this.jsFunctionCallback.clear();
        }
    }

    public ModuleNetwork(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("getNetStatus")
    public final void getNetStatus(JsFunctionCallback jsFunctionCallback) {
        jsFunctionCallback.callback(Integer.valueOf(getInnerNetStatue()));
    }

    @AjxMethod(invokeMode = "sync", value = "syncGetNetStatus")
    public final int syncGetNetStatus() {
        return getInnerNetStatue();
    }

    @AjxMethod("registerNetChange")
    public final void registerNetChange(JsFunctionCallback jsFunctionCallback) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CONNECTIVITY_CHANGE);
        if (this.mNetReceiver == null) {
            this.mNetReceiver = new NetReceiver(this);
        }
        this.mNetReceiver.addCallback(jsFunctionCallback);
        getNativeContext().registerReceiver(this.mNetReceiver, intentFilter);
        this.mHasRegisteNetReceiver = true;
    }

    @AjxMethod("unregisterNetChange")
    public final void unregisterNetChange() {
        if (this.mHasRegisteNetReceiver) {
            getNativeContext().unregisterReceiver(this.mNetReceiver);
            this.mNetReceiver.clearCallback();
        }
        this.mHasRegisteNetReceiver = false;
    }

    /* access modifiers changed from: private */
    public int getInnerNetStatue() {
        int i = 0;
        if (getNativeContext() == null) {
            return 0;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getNativeContext().getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            return 0;
        }
        int networkType = ((TelephonyManager) getNativeContext().getSystemService("phone")).getNetworkType();
        int type = activeNetworkInfo.getType();
        if (type == 0) {
            switch (networkType) {
                case 0:
                    return 1;
                case 1:
                    return 2;
                case 2:
                    return 1;
                case 3:
                    return 2;
                case 4:
                    return 1;
                case 5:
                    return 2;
                case 6:
                    return 2;
                case 7:
                    return 1;
                case 8:
                    return 2;
                case 9:
                    return 2;
                case 10:
                    return 2;
                case 11:
                    return 1;
                case 12:
                    return 2;
                case 13:
                    return 3;
                case 14:
                    return 2;
                case 15:
                    return 2;
                default:
                    return 1;
            }
        } else {
            if (type == 1) {
                i = 4;
            }
            return i;
        }
    }

    public final void onModuleDestroy() {
        super.onModuleDestroy();
        unregisterNetChange();
    }
}
