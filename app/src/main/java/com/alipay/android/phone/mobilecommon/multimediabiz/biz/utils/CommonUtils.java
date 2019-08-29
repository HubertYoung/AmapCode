package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.Base64Optimization;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.HostConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.ProgressiveConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.TaskConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.TaskConstants;
import com.alipay.android.phone.mobilesdk.storage.utils.FileUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.utils.MD5Util;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.service.common.DownloadService;
import com.alipay.multimedia.img.base.SoLibLoader;
import com.alipay.streammedia.mmengine.picture.gif.GifDecoder;
import java.net.URI;
import java.util.HashMap;
import java.util.Random;
import org.apache.http.HttpHost;

public class CommonUtils {
    public static final String APN_PROP_APN = "apn";
    public static final String APN_PROP_PORT = "port";
    public static final String APN_PROP_PROXY = "proxy";
    private static final Uri a = Uri.parse("content://telephony/carriers/preferapn");
    private static Random b = new Random();
    private static boolean c = false;

    public static boolean isActiveNetwork(Context context) {
        return NetworkUtils.isNetworkAvailable(context);
    }

    public static boolean isNeedCheckActiveNet(boolean bDownLoad) {
        if (ConfigManager.getInstance().getCommonConfigItem().taskNetCheckSwitch == 1) {
            if (bDownLoad) {
                if (NBNetUtils.getNBNetDLSwitch()) {
                    return true;
                }
            } else if (NBNetUtils.getNBNetUPSwitch()) {
                return true;
            }
        }
        return false;
    }

    public static String getNetStatus(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == -1) {
            return "";
        }
        NetworkInfo networkinfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkinfo == null) {
            return "";
        }
        if (networkinfo.getType() == 1) {
            return "wifi";
        }
        String netInfo = networkinfo.getExtraInfo();
        if (TextUtils.isEmpty(netInfo)) {
            return "";
        }
        String netInfo2 = netInfo.toLowerCase();
        Logger.P("NetTag", "getNetStatus netInfo=" + netInfo2, new Object[0]);
        return netInfo2;
    }

    public static boolean isWapNetWork() {
        boolean ret = false;
        Context context = AppUtils.getApplicationContext();
        String netInfo = getNetStatus(context);
        if (TextUtils.isEmpty(netInfo)) {
            Logger.D("NetTag", "getNetStatus netInfo=null ;ret=false", new Object[0]);
            return false;
        }
        if (netInfo.equalsIgnoreCase("cmwap") || netInfo.equalsIgnoreCase("3gwap") || netInfo.equalsIgnoreCase("uniwap") || netInfo.equalsIgnoreCase("ctwap") || netInfo.equalsIgnoreCase("wap")) {
            ret = true;
        }
        if (netInfo.startsWith("#777")) {
            String proxy = getApnProxy(context);
            if (proxy == null || proxy.length() <= 0) {
                ret = false;
            } else {
                ret = true;
            }
        }
        Logger.P("NetTag", "getNetStatus netInfo=" + netInfo + ";ret=" + ret, new Object[0]);
        return ret;
    }

    public static String getApnProxy(Context context) {
        String str = null;
        Cursor c2 = null;
        try {
            Cursor c3 = context.getContentResolver().query(a, null, null, null, null);
            if (c3 != null) {
                c3.moveToFirst();
                if (!c3.isAfterLast()) {
                    str = c3.getString(c3.getColumnIndex(APN_PROP_PROXY));
                    if (c3 != null) {
                        c3.close();
                    }
                } else if (c3 != null) {
                    c3.close();
                }
            } else if (c3 != null) {
                c3.close();
            }
        } catch (Exception e) {
            if (c2 != null) {
                c2.close();
            }
        } catch (Throwable th) {
            if (c2 != null) {
                c2.close();
            }
            throw th;
        }
        return str;
    }

    public static HttpHost getProxy() {
        HttpHost proxy = null;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) AppUtils.getApplicationContext().getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
                if (ni != null && ni.isAvailable()) {
                    String proxyHost = Proxy.getDefaultHost();
                    int port = Proxy.getDefaultPort();
                    if (proxyHost != null) {
                        proxy = new HttpHost(proxyHost, port);
                    }
                }
            }
            return proxy;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isWifiNetwork() {
        Context context = AppUtils.getApplicationContext();
        if (context == null) {
            return false;
        }
        return NetworkUtils.isWiFiMobileNetwork(context);
    }

    public static boolean progressiveMobileNetwork() {
        boolean z;
        ProgressiveConfig cfg = ConfigManager.getInstance().getProgressiveConfig();
        if (1 != cfg.qosSwitch) {
            Context context = AppUtils.getApplicationContext();
            if (context == null) {
                return false;
            }
            int netType = NetworkUtils.getNetworkType(context);
            int configNet = cfg.netType;
            if (netType == 1 || netType == 2 || netType == 4) {
                z = true;
            } else {
                z = false;
            }
            if (!z || netType > configNet) {
                return false;
            }
            return true;
        } else if (AlipayQosService.getInstance().getQosLevel() >= cfg.netLevel) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNeedRetry(int code) {
        return code == 403 || code == 502 || code == 302;
    }

    public static URI changeUriByParams(URI dest, String scheme, String host, int port) {
        URI uri = dest;
        try {
            if (TextUtils.isEmpty(scheme) || TextUtils.isEmpty(host)) {
                return uri;
            }
            return new URI(scheme, dest.getUserInfo(), host, -1, dest.getPath(), dest.getQuery(), dest.getFragment());
        } catch (Exception e) {
            return dest;
        }
    }

    public static HashMap<String, String> jsonToHashMap(String json) {
        HashMap<String, String> hashMap = null;
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
            if (jsonObject != null) {
                HashMap<String, String> hashMap2 = new HashMap<>();
                try {
                    for (String key : jsonObject.keySet()) {
                        Object value = jsonObject.get(key);
                        if (value != null) {
                            hashMap2.put(key, value.toString());
                        }
                    }
                    hashMap = hashMap2;
                } catch (Exception e) {
                    e = e;
                    hashMap = hashMap2;
                    Logger.E((String) "CommonUtils", (Throwable) e, (String) "jsonToHashMap exp", new Object[0]);
                    return hashMap;
                }
            }
        } catch (Exception e2) {
            e = e2;
            Logger.E((String) "CommonUtils", (Throwable) e, (String) "jsonToHashMap exp", new Object[0]);
            return hashMap;
        }
        return hashMap;
    }

    public static String getValFromjson(String json, String subKey) {
        if (TextUtils.isEmpty(json) || TextUtils.isEmpty(subKey)) {
            return null;
        }
        try {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
            if (jsonObject == null) {
                return "";
            }
            for (String key : jsonObject.keySet()) {
                if (subKey.equalsIgnoreCase(key)) {
                    return jsonObject.getString(key);
                }
            }
            return "";
        } catch (Exception e) {
            Logger.E((String) "CommonUtils", (Throwable) e, (String) "getValFromjson exp", new Object[0]);
            return "";
        }
    }

    public static String getExceptionMsg(Exception e) {
        if (e == null) {
            r2 = "";
            return "";
        }
        try {
            String exp = getThrowableCauseMsg(e);
            if (!TextUtils.isEmpty(exp)) {
                return exp;
            }
            StackTraceElement[] elements = e.getStackTrace();
            String exp2 = e.getClass().getName() + ":";
            if (elements != null && elements.length > 0) {
                exp2 = exp2 + elements[0].toString();
            }
            return exp2;
        } catch (Exception e2) {
            r2 = "";
            return "";
        }
    }

    public static DownloadService getDownloadService() {
        LauncherApplicationAgent launcherApplicationAgent = LauncherApplicationAgent.getInstance();
        if (launcherApplicationAgent == null) {
            return null;
        }
        MicroApplicationContext microApplicationContext = launcherApplicationAgent.getMicroApplicationContext();
        if (microApplicationContext != null) {
            return (DownloadService) microApplicationContext.findServiceByInterface(DownloadService.class.getName());
        }
        return null;
    }

    public static boolean isSatisfyDownloadSpace(String bizType) {
        return a(bizType) || FileUtils.isSDcardAvailableSpace(((long) ConfigManager.getInstance().getCommonConfigItem().allowDownloadSpace) * 1048576);
    }

    private static boolean a(String bizType) {
        String[] bizs = ConfigManager.getInstance().getSdSpaceConf().sdSpaceCheckWhiteList;
        if (TextUtils.isEmpty(bizType) || bizs == null) {
            return false;
        }
        int length = bizs.length;
        int i = 0;
        while (i < length) {
            String biz = bizs[i];
            if (TextUtils.isEmpty(biz) || !bizType.startsWith(biz)) {
                i++;
            } else {
                Logger.D("CommonUtils", "checkSdcardLeftSpaceBizs biz=" + biz + ";bizType=" + bizType, new Object[0]);
                return true;
            }
        }
        return false;
    }

    public static boolean checkDownloadServiceHttpCode(int code) {
        return code == 200 || (ConfigManager.getInstance().getDownloadServiceHttpCodeSwitch() && (code == 206 || code == 304));
    }

    public static int generateRandom(int min, int max) {
        return (b.nextInt(max) % ((max - min) + 1)) + min;
    }

    public static int getCoreSize(int max) {
        int coreSize = Runtime.getRuntime().availableProcessors() - 1;
        if (coreSize > max) {
            return max;
        }
        if (coreSize <= 0) {
            return 1;
        }
        return coreSize;
    }

    public static int getTaskOccurs(int max) {
        return Math.min(Runtime.getRuntime().availableProcessors() * 2, max);
    }

    public static boolean grayscaleUtdid(String grayValuesStr) {
        return MiscUtils.grayscaleUtdid(LoggerFactory.getLogContext().getDeviceId(), grayValuesStr);
    }

    public static String getImageUrlTaskType(TaskConfig taskConfig, String path) {
        if (taskConfig == null || taskConfig.urlWhiteImage == 0) {
            return TaskConstants.IMAGE_URL_TASKSERVICE;
        }
        try {
            URI uri = new URI(path);
            HostConfig config = ConfigManager.getInstance().getHostConfig();
            String host = uri.getHost();
            if (!TextUtils.isEmpty(host) && config.taskWhiteHosts != null) {
                for (String item : config.taskWhiteHosts) {
                    if (host.equals(item)) {
                        return TaskConstants.IMAGE_WHITE_URL_TASKSERVICE;
                    }
                }
            }
        } catch (Exception e) {
            Logger.E((String) "CommonUtils", (Throwable) e, (String) "getImageUrlTaskType exp", new Object[0]);
        }
        return TaskConstants.IMAGE_BLACK_URL_TASKSERVICE;
    }

    public static String getThrowableCauseMsg(Exception e) {
        if (e == null) {
            return "";
        }
        Throwable rootCause = MiscUtils.getRootCause(e);
        return rootCause == null ? "" : rootCause.getMessage();
    }

    public static byte[] base64Decode(String base64) {
        if (!TextUtils.isEmpty(base64)) {
            return Base64.decode(base64, 2);
        }
        return null;
    }

    public static String calcBase64Key(String content, Base64Optimization optimization) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        long start = System.currentTimeMillis();
        String tinyContent = content;
        if (optimization != null) {
            int length = content.length();
            int stride = optimization.stride;
            int parts = optimization.parts;
            if (length > (stride * parts) + optimization.offset) {
                int partStride = (length - optimization.offset) / parts;
                String ret = "";
                for (int i = 0; i < parts; i++) {
                    int startPos = (i * partStride) + optimization.offset;
                    ret = ret + content.substring(startPos, startPos + stride);
                }
                tinyContent = ret;
            }
        }
        String resultKey = "base64^" + MD5Util.getMD5String(tinyContent);
        Logger.D("CommonUtils", "calcBase64Key: " + resultKey + ", cost: " + (System.currentTimeMillis() - start), new Object[0]);
        return resultKey;
    }

    public static boolean isLowEndDevice() {
        return LoggerFactory.getLogContext().isLowEndDevice();
    }

    public static void loadGifSoLibOnce() {
        if (!c) {
            try {
                GifDecoder.loadLibrariesOnce(new SoLibLoader());
                c = true;
                Logger.D("CommonUtils", "loadGifSoLibOnce success", new Object[0]);
            } catch (Throwable t) {
                Logger.E((String) "CommonUtils", t, (String) "load library error", new Object[0]);
            }
        }
    }
}
