package com.alipay.mobile.nebulacore.plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Data;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.provider.H5CacheProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;

public class H5ShareDataPlugin extends H5SimplePlugin {
    public static final String TAG = "H5ShareDataPlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.SET_SHARE_DATA);
        filter.addAction(CommonEvents.GET_SHARE_DATA);
        filter.addAction(CommonEvents.REMOVE_SHARE_DATA);
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (CommonEvents.GET_SHARE_DATA.equals(action)) {
            H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                public void run() {
                    H5ShareDataPlugin.b(event, bridgeContext);
                }
            });
        } else if (CommonEvents.SET_SHARE_DATA.equals(action)) {
            H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                public void run() {
                    H5ShareDataPlugin.c(event);
                    bridgeContext.sendSuccess();
                }
            });
        } else if (CommonEvents.REMOVE_SHARE_DATA.equals(action)) {
            H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                public void run() {
                    H5ShareDataPlugin.d(event);
                    bridgeContext.sendSuccess();
                }
            });
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static void c(H5Event event) {
        JSONObject param = event.getParam();
        JSONObject data = H5Utils.getJSONObject(param, "data", null);
        if (data != null && !data.isEmpty()) {
            H5CacheProvider shareData = (H5CacheProvider) Nebula.getProviderManager().getProvider(H5CacheProvider.class.getName());
            if (shareData == null) {
                H5Log.e((String) TAG, (String) " not set H5CacheProvider can not use shareDate");
            } else if (H5Utils.getBoolean(param, (String) "writeToFile", false)) {
                for (String key : data.keySet()) {
                    String value = H5Utils.getString(data, key);
                    if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                        shareData.set(key, value, true);
                    }
                }
            } else {
                for (String key2 : data.keySet()) {
                    String value2 = H5Utils.getString(data, key2);
                    if (!TextUtils.isEmpty(key2) && !TextUtils.isEmpty(value2)) {
                        shareData.set(key2, value2);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        JSONObject data = new JSONObject();
        JSONArray keys = H5Utils.getJSONArray(param, "keys", null);
        H5Data shareData = Nebula.getService().getData();
        if (shareData == null) {
            H5Log.e((String) TAG, (String) " not set H5CacheProvider can not use shareDate");
            return;
        }
        if (keys != null && !keys.isEmpty()) {
            int size = keys.size();
            for (int index = 0; index < size; index++) {
                Object obj = keys.get(index);
                if (obj instanceof String) {
                    String key = (String) obj;
                    data.put(key, (Object) shareData.get(key));
                }
            }
        }
        JSONObject result = new JSONObject();
        result.put((String) "data", (Object) data);
        bridgeContext.sendBridgeResult(result);
    }

    /* access modifiers changed from: private */
    public static void d(H5Event event) {
        JSONArray keys = H5Utils.getJSONArray(event.getParam(), "keys", null);
        H5Data shareData = Nebula.getService().getData();
        if (shareData == null) {
            H5Log.e((String) TAG, (String) " not set H5CacheProvider can not use shareDate");
        } else if (keys != null && !keys.isEmpty()) {
            int size = keys.size();
            for (int index = 0; index < size; index++) {
                Object obj = keys.get(index);
                if (obj instanceof String) {
                    shareData.remove((String) obj);
                }
            }
        }
    }
}
