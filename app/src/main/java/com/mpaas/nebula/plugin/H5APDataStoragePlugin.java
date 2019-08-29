package com.mpaas.nebula.plugin;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilesdk.storage.sp.APSharedPreferences;
import com.alipay.android.phone.mobilesdk.storage.sp.SharedPreferencesManager;
import com.alipay.mobile.beehive.audio.Constants;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5SecurityUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.mpaas.nebula.NebulaBiz;
import com.mpaas.nebula.adapter.R;
import com.uc.webview.export.internal.interfaces.IPreloadManager;
import java.util.Map;

public class H5APDataStoragePlugin extends H5SimplePlugin {
    private H5Page a;

    public void onInitialize(H5CoreNode coreNode) {
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.SET_AP_DATA_STORAGE);
        filter.addAction(CommonEvents.GET_AP_DATA_STORAGE);
        filter.addAction(CommonEvents.REMOVE_AP_DATA_STORAGE);
        filter.addAction(CommonEvents.CLEAR_AP_DATA_STORAGE);
        filter.addAction("switchControl");
        filter.addAction("getSwitchControlStatus");
    }

    public void onRelease() {
        this.a = null;
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        return false;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!a(event)) {
            H5Log.e((String) "H5APDataStoragePlugin", (String) "failed to init page info.");
            context.sendError(event, Error.INVALID_PARAM);
            return false;
        }
        String action = event.getAction();
        if (CommonEvents.SET_AP_DATA_STORAGE.equals(action)) {
            a(event, context);
        } else if (CommonEvents.GET_AP_DATA_STORAGE.equals(action)) {
            b(event, context);
        } else if (CommonEvents.REMOVE_AP_DATA_STORAGE.equals(action)) {
            c(event, context);
        } else if (CommonEvents.CLEAR_AP_DATA_STORAGE.equals(action)) {
            d(event, context);
        } else if ("switchControl".equals(action)) {
            b(event);
        } else if (!"getSwitchControlStatus".equals(action)) {
            return false;
        } else {
            e(event, context);
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        String type = H5Utils.getString(param, (String) "type", (String) "preferences");
        String business = H5Utils.getString(param, (String) Constants.KEY_AUDIO_BUSINESS_ID, (String) "");
        String key = H5Utils.getString(param, (String) "key", (String) "");
        if (param.containsKey("value")) {
            String value = H5Utils.getString(param, (String) "value", (String) "");
            int length = value.length();
            H5Log.d("H5APDataStoragePlugin", "setAPDataStorage: value length:" + length);
            if (length > 204800) {
                JSONObject result = new JSONObject();
                result.put((String) "success", (Object) Boolean.valueOf(false));
                result.put((String) "error", (Object) Integer.valueOf(11));
                result.put((String) "errorMessage", (Object) NebulaBiz.getResources().getString(R.string.long_string_error));
                bridgeContext.sendBridgeResult(result);
                H5Log.d("H5APDataStoragePlugin", "setAPDataStorage: value length >  1024 * 200" + length);
                return;
            }
            H5Log.d("H5APDataStoragePlugin", "setAPDataStorage business " + business + " key " + key + " value " + value + " type" + type);
            if (TextUtils.isEmpty(business)) {
                business = NebulaBiz.TAG;
            }
            String appId = a();
            if (!TextUtils.isEmpty(appId)) {
                business = appId;
            }
            if (TextUtils.isEmpty(key)) {
                bridgeContext.sendError(event, Error.INVALID_PARAM);
                return;
            }
            if (TextUtils.isEmpty(type)) {
                type = IPreloadManager.SIR_COMMON_TYPE;
            }
            if (type.equals("user")) {
                key = a(key);
            }
            APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), business);
            preferences.putString(key, value);
            boolean status = preferences.commit();
            H5Log.d("H5APDataStoragePlugin", "preferences " + status);
            JSONObject result2 = new JSONObject();
            result2.put((String) "success", (Object) Boolean.valueOf(status));
            result2.put((String) "error", (Object) Integer.valueOf(0));
            bridgeContext.sendBridgeResult(result2);
            return;
        }
        bridgeContext.sendError(event, Error.INVALID_PARAM);
    }

    private void b(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        JSONObject result = new JSONObject();
        String type = H5Utils.getString(param, (String) "type", (String) "preferences");
        String business = H5Utils.getString(param, (String) Constants.KEY_AUDIO_BUSINESS_ID, (String) "");
        String key = H5Utils.getString(param, (String) "key", (String) "");
        H5Log.d("H5APDataStoragePlugin", "getAPDataStorage business " + business + " key " + key + " type" + type);
        if (TextUtils.isEmpty(business)) {
            business = NebulaBiz.TAG;
        }
        String appId = a();
        if (!TextUtils.isEmpty(appId)) {
            business = appId;
        }
        if (TextUtils.isEmpty(key)) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        if (TextUtils.isEmpty(type)) {
            type = IPreloadManager.SIR_COMMON_TYPE;
        }
        if ("user".equals(type)) {
            key = a(key);
        }
        APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), business);
        if (!preferences.contains(key)) {
            result.put((String) "error", (Object) Integer.valueOf(11));
            result.put((String) "errorMessage", (Object) NebulaBiz.getResources().getString(R.string.not_get_value));
            result.put((String) "success", (Object) Boolean.valueOf(false));
            bridgeContext.sendBridgeResult(result);
            return;
        }
        String data = preferences.getString(key, "");
        H5Log.d("H5APDataStoragePlugin", "preferences data " + data);
        result.put((String) "error", (Object) Integer.valueOf(0));
        result.put((String) "success", (Object) Boolean.valueOf(true));
        result.put((String) "data", (Object) data);
        bridgeContext.sendBridgeResult(result);
    }

    private void c(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        String type = H5Utils.getString(param, (String) "type", (String) "preferences");
        String key = H5Utils.getString(param, (String) "key", (String) "");
        String business = H5Utils.getString(param, (String) Constants.KEY_AUDIO_BUSINESS_ID, (String) "");
        H5Log.d("H5APDataStoragePlugin", "removeAPDataStorage: type:" + type + " key:" + key);
        if (TextUtils.isEmpty(key)) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        if (TextUtils.isEmpty(business)) {
            business = NebulaBiz.TAG;
        }
        String appId = a();
        if (!TextUtils.isEmpty(appId)) {
            business = appId;
        }
        if (TextUtils.isEmpty(type)) {
            type = IPreloadManager.SIR_COMMON_TYPE;
        }
        if ("user".equals(type)) {
            key = a(key);
        }
        APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), business);
        boolean success = preferences.remove(key);
        boolean commit = preferences.commit();
        if (!success || !commit) {
            JSONObject result = new JSONObject();
            result.put((String) "success", (Object) Boolean.valueOf(false));
            result.put((String) "error", (Object) Integer.valueOf(0));
            bridgeContext.sendBridgeResult(result);
            H5Log.w("H5APDataStoragePlugin", "remove preferences fail");
            return;
        }
        JSONObject result2 = new JSONObject();
        result2.put((String) "success", (Object) Boolean.valueOf(true));
        result2.put((String) "error", (Object) Integer.valueOf(0));
        bridgeContext.sendBridgeResult(result2);
        H5Log.d("H5APDataStoragePlugin", "remove preferences success");
    }

    private void d(H5Event event, H5BridgeContext bridgeContext) {
        String business = H5Utils.getString(event.getParam(), (String) Constants.KEY_AUDIO_BUSINESS_ID, (String) "");
        if (TextUtils.isEmpty(business)) {
            business = NebulaBiz.TAG;
        }
        String appId = a();
        if (!TextUtils.isEmpty(appId)) {
            business = appId;
        }
        APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), business);
        preferences.clear();
        preferences.commit();
        bridgeContext.sendSuccess();
    }

    private boolean a(H5Event event) {
        if (!(event.getTarget() instanceof H5Page)) {
            H5Log.w("H5APDataStoragePlugin", "target not page.");
            return false;
        }
        this.a = (H5Page) event.getTarget();
        return true;
    }

    private String a() {
        Bundle startParam = this.a.getParams();
        return H5Utils.getBoolean(startParam, (String) "isTinyApp", false) ? H5Utils.getString(startParam, (String) "appId") : "";
    }

    private static String a(String key) {
        H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
        String userId = "";
        if (h5LoginProvider != null) {
            userId = h5LoginProvider.getUserId();
        }
        return key + "_" + H5SecurityUtil.getMD5(userId + userId + userId);
    }

    private static void b(H5Event event) {
        APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), "h5_switchcontrol");
        JSONObject param = event.getParam();
        if (param != null) {
            for (String key : param.keySet()) {
                if (!TextUtils.equals(key, "funcName")) {
                    preferences.putBoolean(key, param.getBoolean(key).booleanValue());
                    H5Log.d("H5APDataStoragePlugin", "setSwitchControl key " + key + "status " + preferences.commit());
                }
            }
        }
    }

    private static void e(H5Event event, H5BridgeContext bridgeContext) {
        APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), "h5_switchcontrol");
        JSONObject param = event.getParam();
        JSONObject result = new JSONObject();
        if (param == null || !param.containsKey("keys")) {
            result.put((String) "message", (Object) "param must not null");
            result.put((String) "error", (Object) Integer.valueOf(Error.INVALID_PARAM.ordinal()));
            bridgeContext.sendBridgeResult(result);
            return;
        }
        JSONArray keys = H5Utils.getJSONArray(param, "keys", null);
        if (keys != null) {
            if (keys.size() == 0) {
                Map maps = preferences.getAll();
                for (String key : maps.keySet()) {
                    result.put(key, (Object) maps.get(key));
                }
            } else {
                for (int i = 0; i < keys.size(); i++) {
                    String key2 = (String) keys.get(i);
                    if (preferences.contains(key2)) {
                        result.put(key2, (Object) Boolean.valueOf(preferences.getBoolean(key2, true)));
                    }
                }
            }
            if (result.size() > 0) {
                bridgeContext.sendBridgeResult(result);
                return;
            }
            result.put((String) "message", (Object) "not value exists");
            result.put((String) "error", (Object) Integer.valueOf(Error.INVALID_PARAM.ordinal()));
            bridgeContext.sendBridgeResult(result);
            return;
        }
        result.put((String) "message", (Object) "param must not null");
        result.put((String) "error", (Object) Integer.valueOf(Error.INVALID_PARAM.ordinal()));
        bridgeContext.sendBridgeResult(result);
    }
}
