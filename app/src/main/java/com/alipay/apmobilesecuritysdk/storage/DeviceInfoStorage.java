package com.alipay.apmobilesecuritysdk.storage;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.loggers.LoggerUtil;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.localstorage.SecurityStorageUtils;
import com.taobao.accs.common.Constants;
import org.json.JSONObject;

public class DeviceInfoStorage {
    public static void a(Context context, DeviceInfoStorageModel deviceInfoStorageModel) {
        if (deviceInfoStorageModel != null && context != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(Constants.KEY_IMEI, deviceInfoStorageModel.a());
                jSONObject.put(Constants.KEY_IMSI, deviceInfoStorageModel.b());
                jSONObject.put("mac", deviceInfoStorageModel.c());
                jSONObject.put("bluetoothmac", deviceInfoStorageModel.d());
                jSONObject.put("gsi", deviceInfoStorageModel.e());
                String jSONObject2 = jSONObject.toString();
                SecurityStorageUtils.writeToFile("device_feature_file_name", "device_feature_file_key", jSONObject2);
                SecurityStorageUtils.writeToSharedPreference(context, "device_feature_prefs_name", "device_feature_prefs_key", jSONObject2);
            } catch (Exception e) {
                LoggerUtil.a((Throwable) e);
            }
        }
    }

    public static DeviceInfoStorageModel a(Context context) {
        if (context == null) {
            return null;
        }
        String readFromSharedPreference = SecurityStorageUtils.readFromSharedPreference(context, "device_feature_prefs_name", "device_feature_prefs_key");
        if (CommonUtils.isBlank(readFromSharedPreference)) {
            readFromSharedPreference = SecurityStorageUtils.readFromFile("device_feature_file_name", "device_feature_file_key");
        }
        if (CommonUtils.isBlank(readFromSharedPreference)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(readFromSharedPreference);
            DeviceInfoStorageModel deviceInfoStorageModel = new DeviceInfoStorageModel();
            deviceInfoStorageModel.a(jSONObject.getString(Constants.KEY_IMEI));
            deviceInfoStorageModel.b(jSONObject.getString(Constants.KEY_IMSI));
            deviceInfoStorageModel.c(jSONObject.getString("mac"));
            deviceInfoStorageModel.d(jSONObject.getString("bluetoothmac"));
            deviceInfoStorageModel.e(jSONObject.getString("gsi"));
            return deviceInfoStorageModel;
        } catch (Exception e) {
            LoggerUtil.a((Throwable) e);
            return null;
        }
    }
}
