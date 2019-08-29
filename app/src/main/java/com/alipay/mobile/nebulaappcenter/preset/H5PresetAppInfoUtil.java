package com.alipay.mobile.nebulaappcenter.preset;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulaappcenter.util.H5AppGlobal;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;

public class H5PresetAppInfoUtil {
    /* access modifiers changed from: private */
    public static ConcurrentHashMap<String, AppInfo> a = new ConcurrentHashMap<>();

    public static AppInfo a(String appId) {
        if (a.isEmpty()) {
            H5AppCenterPresetProvider h5PresetFroMemory = (H5AppCenterPresetProvider) H5Utils.getProvider(H5AppCenterPresetProvider.class.getName());
            if (h5PresetFroMemory != null) {
                final long t1 = System.currentTimeMillis();
                InputStream presetObjectStream = h5PresetFroMemory.getPresetAppInfoObject();
                if (presetObjectStream != null) {
                    ObjectInput input = new ObjectInputStream(presetObjectStream);
                    try {
                        a = (ConcurrentHashMap) input.readObject();
                    } catch (EOFException exception) {
                        H5Log.e((String) "H5PresetAppInfoUtil", (Throwable) exception);
                    }
                    try {
                        input.close();
                        presetObjectStream.close();
                        H5Log.d("H5PresetAppInfoUtil", "getPresetAppInfoObject cost " + (System.currentTimeMillis() - t1));
                        if (!a.isEmpty()) {
                            return a.get(appId);
                        }
                    } catch (Exception e) {
                        H5Log.e("H5PresetAppInfoUtil", "Exception:", e);
                    }
                }
                InputStream presetJsonStream = h5PresetFroMemory.getPresetAppInfo();
                if (presetJsonStream != null) {
                    String presetJson = H5AppGlobal.a(presetJsonStream);
                    presetJsonStream.close();
                    H5Log.d("H5PresetAppInfoUtil", "h5PresetFroMemory loadPresetApp len: " + presetJson.length());
                    JSONArray dataList = H5Utils.getJSONArray(H5Utils.parseObject(presetJson), "data", null);
                    if (dataList == null) {
                        H5Log.e((String) "H5PresetAppInfoUtil", (String) "dataList == null return");
                        return null;
                    }
                    for (int index = 0; index < dataList.size(); index++) {
                        AppInfo appInfo = null;
                        try {
                            if (dataList.get(index) instanceof JSONObject) {
                                appInfo = H5AppUtil.toAppInfo((JSONObject) dataList.get(index));
                            } else {
                                H5Log.d("H5PresetAppInfoUtil", "dataList.get(index) is not JSONObject");
                            }
                        } catch (Exception e2) {
                            H5Log.e((String) "H5PresetAppInfoUtil", (Throwable) e2);
                        }
                        if (appInfo == null) {
                            H5Log.d("H5PresetAppInfoUtil", "appInfo == null continue ");
                        } else {
                            H5Log.d("H5PresetAppInfoUtil", "preset appId:" + appInfo.app_id);
                            a.put(appInfo.app_id, appInfo);
                        }
                    }
                    H5Log.d("H5PresetAppInfoUtil", "h5PresetFroMemory cost " + (System.currentTimeMillis() - t1));
                    if (!a.isEmpty()) {
                        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                            public final void run() {
                                try {
                                    OutputStream fileStream = new FileOutputStream(new File(H5Utils.getContext().getExternalCacheDir(), "nebulapreset.ser"));
                                    OutputStream bufferedStream = new BufferedOutputStream(fileStream);
                                    ObjectOutput output = new ObjectOutputStream(bufferedStream);
                                    output.writeObject(H5PresetAppInfoUtil.a);
                                    output.close();
                                    bufferedStream.close();
                                    fileStream.close();
                                } catch (Throwable throwable) {
                                    H5Log.e((String) "H5PresetAppInfoUtil", throwable);
                                }
                                H5Log.d("H5PresetAppInfoUtil", "write cost " + (System.currentTimeMillis() - t1));
                            }
                        });
                    }
                }
            }
        }
        return a.get(appId);
    }

    public static void a() {
        a.clear();
    }
}
