package com.alipay.mobile.nebulacore.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class H5NotifyPlugin extends H5SimplePlugin {
    public static final String NOTIFY_PREFIX = "NEBULANOTIFY_";
    public static final String TAG = "H5NotifyPlugin";
    /* access modifiers changed from: private */
    public LocalBroadcastManager a = null;
    /* access modifiers changed from: private */
    public Map<String, Boolean> b = null;
    private Map<String, BroadcastReceiver> c = null;

    public void onRelease() {
        super.onRelease();
        H5Log.d(TAG, "onRelease unregisterReceiver");
        a();
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("postNotification");
        filter.addAction("addNotifyListener");
        filter.addAction("removeNotifyListener");
        filter.addAction(CommonEvents.H5_PAGE_STARTED);
        this.a = LocalBroadcastManager.getInstance(H5Environment.getContext());
        this.b = new HashMap();
        this.c = new HashMap();
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext bridgeContext) {
        H5Log.d(TAG, "interceptEvent is " + event.getAction() + ", data is " + event.getParam().toJSONString());
        if (CommonEvents.H5_PAGE_STARTED.equals(event.getAction())) {
            a();
        }
        return super.interceptEvent(event, bridgeContext);
    }

    private void a() {
        this.b.clear();
        H5Log.d(TAG, "H5_PAGE_STARTED unregisterReceiver");
        for (BroadcastReceiver br : this.c.values()) {
            this.a.unregisterReceiver(br);
        }
        this.c.clear();
    }

    public boolean handleEvent(H5Event event, final H5BridgeContext bridgecontext) {
        String action = event.getAction();
        if ("addNotifyListener".equals(action)) {
            JSONObject param = event.getParam();
            final String broadcastName = H5Utils.getString(param, (String) "name");
            if (TextUtils.isEmpty(broadcastName)) {
                JSONObject data = new JSONObject();
                data.put((String) "message", (Object) "param name must not null");
                data.put((String) "error", (Object) Integer.valueOf(Error.INVALID_PARAM.ordinal()));
                bridgecontext.sendBridgeResult(data);
                H5Log.d(TAG, "broadcastname none");
            } else if (this.b.containsKey(broadcastName)) {
                JSONObject data2 = new JSONObject();
                data2.put((String) "message", (Object) "repeat event");
                data2.put((String) "error", (Object) Integer.valueOf(12));
                bridgecontext.sendBridgeResult(data2);
                H5Log.d(TAG, "repeat event");
            } else {
                this.b.put(broadcastName, Boolean.valueOf(H5Utils.getBoolean(param, (String) "keep", true)));
                BroadcastReceiver receiver = new BroadcastReceiver() {
                    public void onReceive(Context context, Intent intent) {
                        if (intent != null && H5NotifyPlugin.this.b != null && H5NotifyPlugin.this.b.get(broadcastName) != null && bridgecontext != null && H5NotifyPlugin.this.a != null && !TextUtils.isEmpty(broadcastName)) {
                            Bundle extra = new Bundle();
                            try {
                                extra = intent.getExtras();
                            } catch (Exception e) {
                                H5Log.e(H5NotifyPlugin.TAG, "getExtras Exception", e);
                            }
                            if (((Boolean) H5NotifyPlugin.this.b.get(broadcastName)).booleanValue()) {
                                bridgecontext.sendBridgeResultWithCallbackKept(H5Utils.toJSONObject(extra));
                                return;
                            }
                            bridgecontext.sendBridgeResult(H5Utils.toJSONObject(extra));
                            H5Log.d(H5NotifyPlugin.TAG, "unregister receiver keepalive false");
                            H5NotifyPlugin.this.a.unregisterReceiver(this);
                            H5NotifyPlugin.this.b.remove(broadcastName);
                        }
                    }
                };
                this.a.registerReceiver(receiver, new IntentFilter(broadcastName));
                H5Log.d(TAG, "register receiver");
                this.c.put(broadcastName, receiver);
            }
        } else if ("removeNotifyListener".equals(action)) {
            String broadcastName2 = H5Utils.getString(event.getParam(), (String) "name");
            if (TextUtils.isEmpty(broadcastName2)) {
                JSONObject data3 = new JSONObject();
                data3.put((String) "message", (Object) "param name must not null");
                data3.put((String) "error", (Object) Integer.valueOf(Error.INVALID_PARAM.ordinal()));
                bridgecontext.sendBridgeResult(data3);
                H5Log.d(TAG, "broadcastname none");
            } else if (this.c.containsKey(broadcastName2)) {
                this.a.unregisterReceiver(this.c.get(broadcastName2));
                this.b.remove(broadcastName2);
                bridgecontext.sendBridgeResult("success", "true");
            } else {
                bridgecontext.sendBridgeResult("success", "true");
            }
        } else if ("postNotification".equals(action)) {
            a(event, bridgecontext);
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        String broadcastName = H5Utils.getString(params, (String) "name", (String) null);
        JSONObject data = H5Utils.getJSONObject(params, "data", null);
        if (TextUtils.isEmpty(broadcastName)) {
            JSONObject result = new JSONObject();
            result.put((String) "error", (Object) Integer.valueOf(Error.INVALID_PARAM.ordinal()));
            result.put((String) "message", (Object) H5Environment.getResources().getString(R.string.h5_invalidparam));
            context.sendBridgeResult(result);
            return;
        }
        Set keySet = data.keySet();
        Intent intent = new Intent();
        intent.setAction(new StringBuilder(NOTIFY_PREFIX).append(broadcastName).toString());
        for (String key : keySet) {
            intent.putExtra(key, String.valueOf(H5Utils.getValue(data, key, new Object())));
        }
        H5Log.d(TAG, "postNotify action:NEBULANOTIFY_" + broadcastName + " intent data " + data);
        this.a.sendBroadcast(intent);
        context.sendBridgeResult("success", "true");
    }
}
