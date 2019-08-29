package com.alipay.mobile.nebulacore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.SparseIntArray;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5ImageUploadListener;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.provider.H5ImageUploadProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.dev.trace.H5PerformanceUtils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class NebulaUtil {
    public static final String TAG = "H5NebulaUtil";
    public static String dslJs = "";

    public static String getApplicationDir() {
        Context context = H5Environment.getContext();
        if (!TextUtils.isEmpty(context.getApplicationInfo().dataDir)) {
            return context.getApplicationInfo().dataDir;
        }
        boolean z = false;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.dataDir;
        } catch (Throwable t) {
            H5Log.e(TAG, "exception detail", t);
            return z;
        }
    }

    public static void parseNoAlphaColor(Bundle bundle) {
        if (!H5Utils.getBoolean(bundle, (String) H5Param.LONG_TRANSPARENT, false)) {
            a(bundle);
        }
    }

    private static void a(Bundle bundle) {
        int color = H5Utils.getInt(bundle, (String) "backgroundColor", -16777216);
        if (color != -16777216) {
            bundle.putInt("backgroundColor", -16777216 | color);
        }
    }

    public static boolean isShowTransAnimate(Bundle bundle) {
        if (!H5Utils.getBoolean(bundle, (String) H5Param.LONG_TRANSPARENT, false) || !H5Utils.getBoolean(bundle, (String) H5Param.LONG_TRANS_ANIMATE, false)) {
            return false;
        }
        return true;
    }

    public static boolean transparentBackground(Bundle bundle) {
        if (!TextUtils.isEmpty(H5Utils.getString(bundle, (String) H5Param.BACKGROUND_IMAGE_URL))) {
            return true;
        }
        return false;
    }

    public static boolean isLogBlankScreen(String appId) {
        JSONObject jsonObject = H5Utils.parseObject(H5Environment.getConfig("h5_logNewBlankScreenConfig"));
        if (jsonObject != null && !jsonObject.isEmpty()) {
            String enable = H5Utils.getString(jsonObject, (String) "enable");
            String regex = H5Utils.getString(jsonObject, (String) "appId");
            if (TextUtils.isEmpty(dslJs)) {
                dslJs = H5Utils.getString(jsonObject, (String) "script");
            }
            if (!TextUtils.isEmpty(enable) && !TextUtils.isEmpty(regex) && !TextUtils.isEmpty(appId) && "yes".equalsIgnoreCase(enable) && isAppIdMatch(regex, appId)) {
                H5Log.e((String) TAG, (String) "isLogBlankScreen true");
                return true;
            }
        }
        return false;
    }

    public static boolean isAppIdMatch(String regex, String appId) {
        return H5PatternHelper.matchRegex(regex, appId);
    }

    public static boolean isUcCheckDsl() {
        JSONObject jsonObject = H5Utils.parseObject(H5Environment.getConfig("h5_dslCheckConfig"));
        if (jsonObject != null && !jsonObject.isEmpty()) {
            String enable = H5Utils.getString(jsonObject, (String) "ucCheck");
            if (!TextUtils.isEmpty(enable) && "yes".equalsIgnoreCase(enable)) {
                H5Log.d(TAG, "isUcCheckDsl true");
                return true;
            }
        }
        return false;
    }

    public static boolean isCloseCheckDsl() {
        JSONObject jsonObject = H5Utils.parseObject(H5Environment.getConfig("h5_dslCheckConfig"));
        if (jsonObject != null && !jsonObject.isEmpty()) {
            String enable = H5Utils.getString(jsonObject, (String) "closeCheck");
            if (!TextUtils.isEmpty(enable) && BQCCameraParam.VALUE_NO.equalsIgnoreCase(enable)) {
                H5Log.d(TAG, "isCloseCheckDsl false");
                return false;
            }
        }
        return true;
    }

    public static boolean isScreenOn() {
        PowerManager powerManager = (PowerManager) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext().getSystemService("power");
        if (powerManager != null) {
            return powerManager.isScreenOn();
        }
        H5Log.d(TAG, "powerManager == null");
        return true;
    }

    public static void whiteScreenSnapshotUpload(final H5Page h5Page) {
        if (h5Page != null) {
            try {
                JSONObject jsonObject = H5Utils.parseObject(H5Environment.getConfig("h5_whiteScreenImageUpload"));
                if (jsonObject != null && !jsonObject.isEmpty()) {
                    String enable = H5Utils.getString(jsonObject, (String) "enable");
                    if (!TextUtils.isEmpty(enable) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(enable)) {
                        final int pixelNum = Integer.parseInt(H5Utils.getString(jsonObject, (String) "pixelNum"));
                        final float matchRatio = Float.parseFloat(H5Utils.getString(jsonObject, (String) "matchRatio"));
                        H5Log.d(TAG, "pixelNum : " + pixelNum + " matchRatio : " + matchRatio);
                        if (pixelNum != 0 && matchRatio != 0.0f) {
                            H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                                public final void run() {
                                    Bitmap snapshot = H5PerformanceUtils.takeScreenShot(h5Page);
                                    if (snapshot != null && snapshot.getWidth() > 0 && snapshot.getHeight() > 0) {
                                        int width = snapshot.getWidth();
                                        int height = snapshot.getHeight();
                                        SparseIntArray pixelArray = new SparseIntArray();
                                        Random random = new Random();
                                        boolean isWhiteScreen = false;
                                        int i = 0;
                                        while (true) {
                                            if (i >= pixelNum) {
                                                break;
                                            }
                                            int randomPixel = snapshot.getPixel(random.nextInt(width - 1), random.nextInt(height - 1));
                                            if (pixelArray.indexOfKey(randomPixel) >= 0) {
                                                int number = pixelArray.get(randomPixel) + 1;
                                                pixelArray.put(randomPixel, number);
                                                if (((float) number) >= ((float) pixelNum) * matchRatio) {
                                                    isWhiteScreen = true;
                                                    H5Log.d(NebulaUtil.TAG, "isWhiteScreen = true, number : " + number + " pixelNum * matchRatio : " + (((float) pixelNum) * matchRatio));
                                                    break;
                                                }
                                            } else {
                                                pixelArray.put(randomPixel, 1);
                                            }
                                            i++;
                                        }
                                        H5ImageUploadProvider imageUploadProvider = (H5ImageUploadProvider) Nebula.getProviderManager().getProvider(H5ImageUploadProvider.class.getName());
                                        if (isWhiteScreen && imageUploadProvider != null) {
                                            imageUploadProvider.uploadImage(snapshot, new H5ImageUploadListener() {
                                                public void onSuccess(String multimediaID) {
                                                    H5Log.d(NebulaUtil.TAG, "multimediaID : " + multimediaID);
                                                    if (h5Page != null) {
                                                        H5PageData pageData = h5Page.getPageData();
                                                        if (pageData != null) {
                                                            pageData.setMultimediaID(multimediaID);
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
        }
    }

    public static boolean enableResDegrade() {
        return !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_nbresmode"));
    }

    public static boolean enableShowLoadingViewConfig() {
        String config = H5Environment.getConfig("h5_enableShowLoadingView");
        if (TextUtils.isEmpty(config) || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(config)) {
            return true;
        }
        return false;
    }

    public static boolean enableAllowFileAccess(String path) {
        boolean enableAllow = false;
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        JSONArray jsonArray = H5Utils.parseArray(H5Environment.getConfigWithProcessCache("h5_enableAllowFileAccessList"));
        if (jsonArray == null || jsonArray.isEmpty()) {
            jsonArray = new JSONArray();
            jsonArray.add("\\/apps\\/.*");
        }
        if (!jsonArray.isEmpty()) {
            for (int i = 0; i < jsonArray.size(); i++) {
                String regex = jsonArray.getString(i);
                if (H5PatternHelper.matchRegex(regex, path)) {
                    H5Log.d(TAG, "matchRegex regex : " + regex + " path : " + path);
                    enableAllow = true;
                }
            }
        }
        return enableAllow;
    }

    public static boolean enableRecordStartupParams() {
        return !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_startupParams_record"));
    }

    public static String getStartupParamsStr(Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return "";
        }
        String paramStr = "";
        Set<String> keySet = bundle.keySet();
        if (keySet == null || keySet.isEmpty()) {
            return "";
        }
        for (String key : keySet) {
            Object value = bundle.get(key);
            if (TextUtils.isEmpty(paramStr)) {
                paramStr = key + "=" + String.valueOf(value);
            } else {
                paramStr = paramStr + "^" + key + "=" + String.valueOf(value);
            }
        }
        return paramStr;
    }

    public static Map<String, String> getStartupParamsMap(Bundle bundle) {
        Map map = new HashMap();
        Bundle copyBundle = (Bundle) bundle.clone();
        if (copyBundle != null && !copyBundle.isEmpty()) {
            Set<String> keySet = copyBundle.keySet();
            if (keySet != null && !keySet.isEmpty()) {
                for (String key : keySet) {
                    map.put(key, String.valueOf(copyBundle.get(key)));
                }
            }
        }
        return map;
    }
}
