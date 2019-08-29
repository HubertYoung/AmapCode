package com.alipay.mobile.nebulacore.web;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.crash.CrashAnalyzer;
import com.alipay.mobile.common.logging.util.crash.CrashInfoDO;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5DeviceHelper;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.util.List;

public class H5HardwarePolicy {
    private static boolean a = false;
    private static boolean b = false;

    public static boolean isAbove14Level() {
        return VERSION.SDK_INT >= 14;
    }

    public static boolean disableHWACByHardwareInfo() {
        if (a) {
            H5Log.d("H5HardwarePolicy", "disableHWACByHWInfo by cache: " + b);
            return b;
        }
        a = true;
        try {
            H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (configProvider != null) {
                JSONArray configArrayStr = configProvider.getConfigJSONArray("h5_disableHWACByHardwareInfo");
                if (configArrayStr != null) {
                    int size = configArrayStr.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            break;
                        }
                        JSONObject ruleObject = configArrayStr.getJSONObject(i);
                        boolean sdkLevelMatch = true;
                        JSONObject sdkLevelObject = H5Utils.getJSONObject(ruleObject, "sdkLevelRange", null);
                        if (sdkLevelObject != null) {
                            sdkLevelMatch = VERSION.SDK_INT >= H5Utils.getInt(sdkLevelObject, (String) "min", Integer.MIN_VALUE) && VERSION.SDK_INT <= H5Utils.getInt(sdkLevelObject, (String) "max", Integer.MAX_VALUE);
                        }
                        if (sdkLevelMatch) {
                            boolean modelMatch = true;
                            JSONArray modelsArray = H5Utils.getJSONArray(ruleObject, "models", null);
                            if (modelsArray != null) {
                                int modelSize = modelsArray.size();
                                for (int j = 0; j < modelSize; j++) {
                                    String model = modelsArray.getString(j);
                                    H5Log.d("H5HardwarePolicy", "disableHWACByHardwareInfo model " + Build.MODEL);
                                    modelMatch = Build.MODEL.equalsIgnoreCase(model);
                                    if (modelMatch) {
                                        break;
                                    }
                                }
                            }
                            if (modelMatch) {
                                boolean boardMatch = true;
                                JSONArray boardsArray = H5Utils.getJSONArray(ruleObject, "boards", null);
                                if (boardsArray != null) {
                                    int boardsSize = boardsArray.size();
                                    for (int j2 = 0; j2 < boardsSize; j2++) {
                                        String board = boardsArray.getString(j2);
                                        String cpuHardWare = H5DeviceHelper.getCpuHardware();
                                        H5Log.d("H5HardwarePolicy", "disableHWACByHardwareInfo cpuHardWare " + cpuHardWare);
                                        if (cpuHardWare != null) {
                                            String cpuHardWare2 = cpuHardWare.replaceAll("\\s+", "").toUpperCase();
                                            boardMatch = !TextUtils.isEmpty(cpuHardWare2) && cpuHardWare2.contains(board);
                                        }
                                        if (boardMatch) {
                                            break;
                                        }
                                    }
                                }
                                if (boardMatch) {
                                    boolean screenHeightMatch = true;
                                    JSONObject screenHeightRangeObject = H5Utils.getJSONObject(ruleObject, "screenHeightRange", null);
                                    if (screenHeightRangeObject != null) {
                                        int max = H5Utils.getInt(screenHeightRangeObject, (String) "max", Integer.MAX_VALUE);
                                        int min = H5Utils.getInt(screenHeightRangeObject, (String) "min", Integer.MIN_VALUE);
                                        Context context = H5Utils.getContext();
                                        if (context != null) {
                                            int screenHeight = H5DimensionUtil.getScreenHeight(context);
                                            H5Log.d("H5HardwarePolicy", "disableHWACByHardwareInfo screenHeight " + screenHeight);
                                            screenHeightMatch = screenHeight >= min && screenHeight <= max;
                                        }
                                    }
                                    if (screenHeightMatch) {
                                        boolean screenWidthMatch = true;
                                        JSONObject screenWidthRangeObject = H5Utils.getJSONObject(ruleObject, "screenWidthRange", null);
                                        if (screenWidthRangeObject != null) {
                                            int max2 = H5Utils.getInt(screenWidthRangeObject, (String) "max", Integer.MAX_VALUE);
                                            int min2 = H5Utils.getInt(screenWidthRangeObject, (String) "min", Integer.MIN_VALUE);
                                            Context context2 = H5Utils.getContext();
                                            if (context2 != null) {
                                                int screenWidth = H5DimensionUtil.getScreenWidth(context2);
                                                H5Log.d("H5HardwarePolicy", "disableHWACByHardwareInfo screenWidth " + screenWidth);
                                                screenWidthMatch = screenWidth >= min2 && screenWidth <= max2;
                                            }
                                        }
                                        if (screenWidthMatch) {
                                            H5Log.d("H5HardwarePolicy", "disableHWACByHardwareInfo gocha!");
                                            b = true;
                                            break;
                                        }
                                        H5Log.d("H5HardwarePolicy", "disableHWACByHardwareInfo screenWidth not match");
                                    } else {
                                        H5Log.d("H5HardwarePolicy", "disableHWACByHardwareInfo screenHeight not match");
                                    }
                                } else {
                                    H5Log.d("H5HardwarePolicy", "disableHWACByHardwareInfo board not match");
                                }
                            } else {
                                H5Log.d("H5HardwarePolicy", "disableHWACByHardwareInfo model not match");
                            }
                        } else {
                            H5Log.d("H5HardwarePolicy", "disableHWACByHardwareInfo sdk " + VERSION.SDK_INT + " not match");
                        }
                        i++;
                    }
                } else {
                    return false;
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5HardwarePolicy", "parseDisableHWACByHardwareInfo error", t);
        }
        return b;
    }

    public static boolean disableHardwareAccelerate(Bundle startParams, String bizType) {
        if ("yes".equalsIgnoreCase(H5Utils.getString(startParams, (String) "forceHardAccelerate"))) {
            return false;
        }
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            JSONArray jsonArray = H5Utils.parseArray(h5ConfigProvider.getConfig("h5_interceptCrashAnalyzerBizType"));
            if (jsonArray != null && !jsonArray.isEmpty() && jsonArray.contains(bizType)) {
                return false;
            }
        }
        JSONArray onlineArray = H5Utils.parseArray(H5Environment.getConfig("h5_interceptCrashAnalyzer"));
        if (onlineArray != null && !onlineArray.isEmpty()) {
            for (int i = 0; i < onlineArray.size(); i++) {
                JSONObject obj = onlineArray.getJSONObject(i);
                String ma = obj.getString("ma");
                String mo = obj.getString(LogItem.MM_C22_K4_MODE);
                int sdk = obj.getIntValue("sdk_int");
                if (TextUtils.equals(ma, Build.MANUFACTURER) && TextUtils.equals(mo, Build.MODEL)) {
                    if (sdk == 0) {
                        return false;
                    }
                    if (sdk == VERSION.SDK_INT) {
                        H5Log.d("H5HardwarePolicy", "prevent CrashAnalyzer");
                        return false;
                    }
                }
            }
        }
        return a();
    }

    private static boolean a() {
        try {
            List crashTypeList = CrashAnalyzer.getHistoryCrashTypes(H5Environment.getContext());
            if (crashTypeList == null || crashTypeList.isEmpty()) {
                return false;
            }
            for (CrashInfoDO info : crashTypeList) {
                if (info.getCrashType() == 100) {
                    String versionName = LoggerFactory.getLogContext().getProductVersion();
                    if (TextUtils.equals(versionName, info.getCrashProductVersion())) {
                        H5Log.d("H5HardwarePolicy", "getLastCrashTime " + info.getLastCrashTime() + " getCrashTimes:" + info.getCrashTimes());
                        if (System.currentTimeMillis() - info.getLastCrashTime() <= 86400000 || BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_CrashInfo_use_time"))) {
                            H5Log.d("H5HardwarePolicy", "disableHardwareAccelerate by CrashAnalyzer in " + versionName);
                            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_DISABLE_HARDWARE_ACCELERATE_BY_FRAMEWORK").param1().add(versionName, null).param2().add(Build.MANUFACTURER + Build.MODEL + VERSION.SDK_INT, null));
                            return true;
                        }
                        H5Log.d("H5HardwarePolicy", "> onDayTime not disableHardwareAccelerate");
                        return false;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            H5Log.e((String) "H5HardwarePolicy", (Throwable) e);
            return false;
        }
    }
}
