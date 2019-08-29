package com.alipay.mobile.tinyappcommon.h5plugin;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5MemoryWarningPlugin extends H5SimplePlugin {
    static final String MEMORY_WARNING = "memoryWarning";
    static final String START_MONITOR_MEMORY_WARNING = "startMonitorMemoryWarning";
    static final String STOP_MONITOR_MEMORY_WARNING = "stopMonitorMemoryWarning";
    final ComponentCallbacks2 mCallback = new ComponentCallbacks2() {
        public void onLowMemory() {
        }

        public void onConfigurationChanged(Configuration newConfig) {
        }

        public void onTrimMemory(int level) {
            if (H5MemoryWarningPlugin.this.mSession != null && H5MemoryWarningPlugin.this.mShowMonitorMemoryWarning) {
                if (level == 10 || level == 15) {
                    JSONObject o = new JSONObject();
                    JSONObject data = new JSONObject();
                    data.put((String) H5PermissionManager.level, (Object) Integer.valueOf(level));
                    o.put((String) "data", (Object) data);
                    H5Page page = H5MemoryWarningPlugin.this.mSession.getTopPage();
                    if (page != null) {
                        H5Bridge bridge = page.getBridge();
                        if (bridge != null) {
                            bridge.sendToWeb(H5MemoryWarningPlugin.MEMORY_WARNING, o, null);
                        }
                    }
                }
            }
        }
    };
    final Context mContext = H5Utils.getContext();
    @Nullable
    H5Session mSession;
    /* access modifiers changed from: private */
    public boolean mShowMonitorMemoryWarning = false;

    public void onRelease() {
        unregisterCallback();
        this.mSession = null;
    }

    public void onInitialize(H5CoreNode coreNode) {
        super.onInitialize(coreNode);
        if (coreNode instanceof H5Session) {
            this.mSession = (H5Session) coreNode;
            registerCallback();
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(START_MONITOR_MEMORY_WARNING);
        filter.addAction(STOP_MONITOR_MEMORY_WARNING);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (TextUtils.isEmpty(action)) {
            return false;
        }
        char c = 65535;
        switch (action.hashCode()) {
            case -1378241725:
                if (action.equals(STOP_MONITOR_MEMORY_WARNING)) {
                    c = 1;
                    break;
                }
                break;
            case 1339326371:
                if (action.equals(START_MONITOR_MEMORY_WARNING)) {
                    c = 0;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mShowMonitorMemoryWarning = true;
                wrapregisterCallback(event);
                return true;
            case 1:
                this.mShowMonitorMemoryWarning = false;
                unregisterCallback();
                return true;
            default:
                return false;
        }
    }

    private void wrapregisterCallback(H5Event event) {
        H5Page page = event.getH5page();
        if (page != null) {
            H5Session session = page.getSession();
            if (session != null) {
                this.mSession = session;
                registerCallback();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void registerCallback() {
        unregisterCallback();
        if (this.mContext != null) {
            this.mContext.registerComponentCallbacks(this.mCallback);
        }
    }

    /* access modifiers changed from: 0000 */
    public void unregisterCallback() {
        if (this.mContext != null) {
            this.mContext.unregisterComponentCallbacks(this.mCallback);
        }
    }
}
