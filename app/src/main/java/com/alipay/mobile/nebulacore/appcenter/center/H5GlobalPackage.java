package com.alipay.mobile.nebulacore.appcenter.center;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppScoreList;
import com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigs;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage;
import com.alipay.mobile.nebulacore.core.H5ContentProviderImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class H5GlobalPackage {
    public static final String HEADER_ENTRY_NAME = "header.json";
    public static final String TAG = "H5GlobalPackage";
    public static final String TINY_RES_KEY = "tinyRes";
    private static List<H5ContentPackage> a;
    private static Map<String, Map<String, H5ContentPackage>> b;
    private static Map<String, Set<String>> c = new HashMap();
    private static Map<String, Map<String, String>> d = new HashMap();

    public static void prepare() {
        H5Log.d(TAG, "prepare global package");
        a();
        if (a == null) {
            H5Log.d(TAG, "sPackageList == null");
            return;
        }
        synchronized (a) {
            for (H5ContentPackage prepareContent : a) {
                prepareContent.prepareContent(false);
            }
        }
    }

    public static void addHeader(String appId, byte[] data) {
        if (!"NO".equalsIgnoreCase(H5Utils.getString(H5Utils.parseObject(H5WalletWrapper.getConfigWithProcessCache("h5_resManifest")), (String) "matchHeaders", (String) "YES"))) {
            if (d == null) {
                d = new ConcurrentHashMap();
            }
            String tmpData = new String(data);
            H5Log.d(TAG, "addHeader from header.json: " + tmpData);
            JSONObject headerMapJsonSet = H5Utils.parseObject(tmpData);
            if (headerMapJsonSet != null && headerMapJsonSet.size() > 0) {
                Set<String> headerUrlList = new HashSet<>(headerMapJsonSet.keySet());
                c.put(appId, headerUrlList);
                for (String url : headerUrlList) {
                    JSONObject headerMapJson = H5Utils.getJSONObject(headerMapJsonSet, url, null);
                    if (headerMapJson != null) {
                        Map headerMap = new HashMap();
                        for (String headerKey : headerMapJson.keySet()) {
                            String headerMapVal = H5Utils.getString(headerMapJson, headerKey);
                            if (!TextUtils.isEmpty(headerMapVal)) {
                                headerMap.put(headerKey, headerMapVal);
                            }
                        }
                        d.put(url, headerMap);
                    }
                }
            }
        }
    }

    @Nullable
    public static Map<String, String> getHeader(String url) {
        return d.get(url);
    }

    public static void addResourcePackage(String key, String appId, boolean canDegrade, boolean preLoad) {
        addResourcePackageSync(key, appId, canDegrade, preLoad, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void addResourcePackageSync(java.lang.String r6, java.lang.String r7, boolean r8, boolean r9, boolean r10) {
        /*
            java.lang.String r3 = "H5GlobalPackage"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "addResourcePackage "
            r4.<init>(r5)
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.String r5 = " "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r7)
            java.lang.String r5 = " "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r8)
            java.lang.String r4 = r4.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r4)
            boolean r3 = android.text.TextUtils.isEmpty(r6)
            if (r3 != 0) goto L_0x0034
            boolean r3 = android.text.TextUtils.isEmpty(r7)
            if (r3 == 0) goto L_0x0035
        L_0x0034:
            return
        L_0x0035:
            a()
            java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage>> r3 = b
            if (r3 != 0) goto L_0x0043
            java.util.concurrent.ConcurrentHashMap r3 = new java.util.concurrent.ConcurrentHashMap
            r3.<init>()
            b = r3
        L_0x0043:
            java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage>> r4 = b
            monitor-enter(r4)
            java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage>> r3 = b     // Catch:{ all -> 0x0074 }
            if (r3 == 0) goto L_0x0091
            java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage>> r3 = b     // Catch:{ all -> 0x0074 }
            boolean r3 = r3.containsKey(r6)     // Catch:{ all -> 0x0074 }
            if (r3 == 0) goto L_0x0093
            java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage>> r3 = b     // Catch:{ all -> 0x0074 }
            java.lang.Object r2 = r3.get(r6)     // Catch:{ all -> 0x0074 }
            java.util.Map r2 = (java.util.Map) r2     // Catch:{ all -> 0x0074 }
            java.lang.String r3 = "tinyRes"
            boolean r3 = r3.equals(r6)     // Catch:{ all -> 0x0074 }
            if (r3 == 0) goto L_0x007f
            if (r2 == 0) goto L_0x007f
            java.lang.Object r1 = r2.get(r7)     // Catch:{ all -> 0x0074 }
            com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage r1 = (com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage) r1     // Catch:{ all -> 0x0074 }
            if (r1 == 0) goto L_0x0077
            r1.setPreload(r9)     // Catch:{ all -> 0x0074 }
            r1.prepareContent(r10)     // Catch:{ all -> 0x0074 }
        L_0x0072:
            monitor-exit(r4)     // Catch:{ all -> 0x0074 }
            goto L_0x0034
        L_0x0074:
            r3 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0074 }
            throw r3
        L_0x0077:
            java.lang.String r3 = "H5GlobalPackage"
            java.lang.String r5 = "h5ContentPackage==null"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ all -> 0x0074 }
            goto L_0x0072
        L_0x007f:
            if (r2 == 0) goto L_0x0091
            boolean r3 = r2.containsKey(r7)     // Catch:{ all -> 0x0074 }
            if (r3 != 0) goto L_0x0091
            com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage r0 = a(r7, r8)     // Catch:{ all -> 0x0074 }
            r2.put(r7, r0)     // Catch:{ all -> 0x0074 }
            r0.prepareContent(r10)     // Catch:{ all -> 0x0074 }
        L_0x0091:
            monitor-exit(r4)     // Catch:{ all -> 0x0074 }
            goto L_0x0034
        L_0x0093:
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ all -> 0x0074 }
            r2.<init>()     // Catch:{ all -> 0x0074 }
            com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage r0 = a(r7, r8)     // Catch:{ all -> 0x0074 }
            r0.setPreload(r9)     // Catch:{ all -> 0x0074 }
            r2.put(r7, r0)     // Catch:{ all -> 0x0074 }
            java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage>> r3 = b     // Catch:{ all -> 0x0074 }
            r3.put(r6, r2)     // Catch:{ all -> 0x0074 }
            r0.prepareContent(r10)     // Catch:{ all -> 0x0074 }
            goto L_0x0091
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.appcenter.center.H5GlobalPackage.addResourcePackageSync(java.lang.String, java.lang.String, boolean, boolean, boolean):void");
    }

    private static H5ContentPackage a(String appId, boolean canDegrade) {
        H5Log.d(TAG, "append resource package : " + appId);
        Bundle bundle = new Bundle();
        bundle.putString("appId", appId);
        bundle.putInt("appType", 2);
        H5ContentPackage content = new H5ContentPackage(bundle, true);
        content.setCanDegrade(canDegrade);
        return content;
    }

    public static void clearResourcePackages(String keyStr) {
        if (b != null && !TextUtils.isEmpty(keyStr)) {
            if (b.containsKey(keyStr)) {
                H5Log.d(TAG, "clearResourcePackages remove : " + keyStr);
                b.remove(keyStr);
            }
            Set<String> urlList = c.remove(keyStr);
            if (urlList != null) {
                for (String url : urlList) {
                    d.remove(url);
                }
            }
        }
    }

    public static void clearAllResourcePackages() {
        if (b != null) {
            H5Log.d(TAG, "clearAllResourcePackages");
            H5AppCenterPresetProvider provider = (H5AppCenterPresetProvider) H5Utils.getProvider(H5AppCenterPresetProvider.class.getName());
            if (provider != null) {
                Set commonAppIdSet = provider.getCommonResourceAppList();
                List<String> removeAppIdList = new ArrayList<>();
                for (String key : b.keySet()) {
                    if (!commonAppIdSet.contains(key) && !TINY_RES_KEY.equals(key)) {
                        removeAppIdList.add(key);
                    }
                }
                for (String clearResourcePackages : removeAppIdList) {
                    clearResourcePackages(clearResourcePackages);
                }
            }
        }
    }

    public static H5ContentPackage getResContentByAppId(String mainAppId, String resAppId) {
        if (b == null || TextUtils.isEmpty(mainAppId)) {
            return null;
        }
        Map resMap = b.get(mainAppId);
        if (resMap != null) {
            return (H5ContentPackage) resMap.get(resAppId);
        }
        return null;
    }

    public static byte[] getContent(String url, boolean isMainDoc) {
        if (a == null) {
            return null;
        }
        for (H5ContentPackage content : a) {
            byte[] bytes = content.get(url);
            if (bytes != null) {
                H5Log.d(H5ContentProviderImpl.TAG, "[res] load response from " + content.getAppId() + " version:" + content.currentUseVersion + " package " + url);
                return bytes;
            }
        }
        if (b == null || b.size() == 0) {
            return null;
        }
        for (Entry<String, Map<String, H5ContentPackage>> value : b.entrySet()) {
            Map packageMap = (Map) value.getValue();
            if (packageMap != null && packageMap.size() > 0) {
                for (Entry value2 : packageMap.entrySet()) {
                    H5ContentPackage content2 = (H5ContentPackage) value2.getValue();
                    byte[] bytes2 = content2.get(url);
                    if (bytes2 != null) {
                        if (!isMainDoc || !a(content2)) {
                            H5Log.d(H5ContentProviderImpl.TAG, "[res] load response from " + content2.getAppId() + " version:" + content2.currentUseVersion + " package " + url);
                            return bytes2;
                        }
                        H5Log.d(TAG, "isMainDocInvalid");
                        AppInfo info = new AppInfo();
                        info.app_id = content2.getAppId();
                        H5AppUtil.appCenterLog("H5_APP_EXCEP", info, "^step=maininvalid");
                        return null;
                    }
                }
                continue;
            }
        }
        return null;
    }

    private static boolean a(H5ContentPackage contentPackage) {
        H5AppProvider provider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (provider != null && contentPackage.isResPackage && "yes".equalsIgnoreCase(H5WalletWrapper.getConfig("h5_mainUrlDegrade"))) {
            AppInfo appInfo = provider.getAppInfo(contentPackage.getAppId());
            if (appInfo != null) {
                long outDatedSecond = H5Utils.parseLong(provider.getConfigExtra(H5NebulaAppConfigs.RES_INVALID_TIME));
                if (outDatedSecond == 0) {
                    outDatedSecond = 259200;
                }
                long updateAppTime = H5Utils.parseLong(appInfo.update_app_time);
                H5Log.d(TAG, "main doc resource check updateAppTime: " + updateAppTime + ", config: " + outDatedSecond);
                if ((1000 * outDatedSecond) + updateAppTime < System.currentTimeMillis()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void a() {
        if (a == null) {
            a = Collections.synchronizedList(new ArrayList());
            H5AppCenterPresetProvider h5AppCenterPresetProvider = (H5AppCenterPresetProvider) H5Utils.getProvider(H5AppCenterPresetProvider.class.getName());
            if (h5AppCenterPresetProvider != null) {
                Set appSet = h5AppCenterPresetProvider.getCommonResourceAppList();
                if (appSet != null && !appSet.isEmpty()) {
                    for (String appId : appSet) {
                        if (!TextUtils.isEmpty(appId)) {
                            if (TextUtils.equals(appId, h5AppCenterPresetProvider.getTinyCommonApp())) {
                                H5Log.d(TAG, appId + " is tinyAppCommRes not add in PackageList");
                            } else {
                                Bundle bundle = new Bundle();
                                H5Log.d(TAG, "init global app " + appId);
                                bundle.putString("appId", appId);
                                bundle.putInt("appType", 2);
                                a.add(new H5ContentPackage(bundle, true));
                            }
                        }
                    }
                    return;
                }
            }
            H5Log.e((String) TAG, (String) "init global app fail !! ");
        }
    }

    public static String getResPkgInfo(String key) {
        String result = "";
        if (a != null && a.size() > 0) {
            for (H5ContentPackage content : a) {
                result = a(result, content);
            }
        }
        if (b != null && b.size() > 0) {
            Map pkgMap = b.get(key);
            if (pkgMap != null && pkgMap.size() > 0) {
                for (Entry value : pkgMap.entrySet()) {
                    result = a(result, (H5ContentPackage) value.getValue());
                }
            }
            Map tinyResMap = b.get(TINY_RES_KEY);
            if (tinyResMap != null && tinyResMap.size() > 0) {
                for (Entry value2 : tinyResMap.entrySet()) {
                    result = a(result, (H5ContentPackage) value2.getValue());
                }
            }
        }
        if (result.length() > 0 && result.toString().endsWith(MergeUtil.SEPARATOR_KV)) {
            result = result.substring(0, result.length() - 1);
        }
        return result.toString();
    }

    private static String a(String result, H5ContentPackage content) {
        if (content.size() > 0) {
            return result + content.getAppId() + "_Y_" + content.getVersion() + "_" + H5AppScoreList.getInstance().getAppCredit(content.getAppId()) + MergeUtil.SEPARATOR_KV;
        }
        String tempInfo = H5GlobalDegradePkg.getInstance().getPkgInfo(content.getAppId());
        if (TextUtils.isEmpty(tempInfo)) {
            return result + content.getAppId() + "_N_" + content.getVersion() + "_" + H5AppScoreList.getInstance().getAppCredit(content.getAppId()) + MergeUtil.SEPARATOR_KV;
        }
        return result + tempInfo;
    }
}
