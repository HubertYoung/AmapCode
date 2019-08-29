package com.alipay.mobile.nebulaappproxy.api;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppScoreList;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppRes;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5TimeService;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.amap.bundle.cloudconfig.appinit.request.AppInitCallback;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class H5SyncRunnable implements Runnable {
    private static long d;
    private boolean a = true;
    private H5AppCenterService b;
    private String c;

    public H5SyncRunnable(String syncMessage) {
        this.c = syncMessage;
    }

    public void run() {
        this.b = (H5AppCenterService) H5Utils.findServiceByInterface(H5AppCenterService.class.getName());
        try {
            if (this.b == null) {
                H5Log.e((String) "H5SyncUtil", (String) "h5AppCenterService==null");
                return;
            }
            this.a = H5SyncUtil.isDownload();
            JSONArray syncMsg = H5Utils.parseArray(this.c);
            List listForceSync = Collections.synchronizedList(new ArrayList());
            Map map = new ConcurrentHashMap();
            if (syncMsg != null && !syncMsg.isEmpty()) {
                H5Log.d("H5SyncUtil", "sync size:" + syncMsg.size());
                for (int i = 0; i < syncMsg.size(); i++) {
                    String plStr = H5Utils.getString((JSONObject) syncMsg.get(i), (String) H5Param.PREFETCH_LOCATION);
                    if (!TextUtils.isEmpty(plStr)) {
                        a(plStr, map, listForceSync, "", this.b);
                    }
                }
                for (Entry value : map.entrySet()) {
                    List listAppRes = (List) value.getValue();
                    H5Log.debug("H5SyncUtil", "map size:" + map.size());
                    if (listAppRes != null && !listAppRes.isEmpty()) {
                        for (int i2 = 0; i2 < listAppRes.size(); i2++) {
                            H5Log.debug("H5SyncUtil", "listAppRes size:" + listAppRes.size());
                            if (i2 == listAppRes.size() - 1) {
                                if (this.a) {
                                    H5Log.debug("H5SyncUtil", "h5AppCenterService.setUpInfo appId" + ((AppRes) listAppRes.get(i2)).data.get(0).app_id + " version:" + ((AppRes) listAppRes.get(i2)).data.get(0).version + " download true");
                                    this.b.setUpInfo((AppRes) listAppRes.get(i2), true, true, true, H5DownloadRequest.SYNC_SCENE);
                                } else {
                                    H5Log.debug("H5SyncUtil", "h5AppCenterService.setUpInfo appId" + ((AppRes) listAppRes.get(i2)).data.get(0).app_id + " version:" + ((AppRes) listAppRes.get(i2)).data.get(0).version + " download false");
                                }
                            }
                            this.b.setUpInfo((AppRes) listAppRes.get(i2), true, false, true, H5DownloadRequest.SYNC_SCENE);
                        }
                    }
                }
                if (d > 0 && System.currentTimeMillis() < d) {
                    H5Log.d("H5SyncUtil", "updateAppScoreInfo from sync");
                    H5AppScoreList.getInstance().updateAppScoreInfo(true, null);
                    d = 0;
                    H5LogUtil.logNebulaTech(H5LogData.seedId("H5_APP_BIZ_SYNC").param1().add("monitor", null).param2().add("info", "syncInfo").add("type", "syncType"));
                }
                if (listForceSync != null && !listForceSync.isEmpty()) {
                    forceSync((JSONObject) listForceSync.get(listForceSync.size() - 1));
                }
                if (!TextUtils.isEmpty("")) {
                    H5LogUtil.logNebulaTech(H5LogData.seedId("H5_APP_BIZ_SYNC").param1().add("monitor", null).param3().add("step", "app").param4().add("step", "app").add("list", ""));
                }
            }
        } catch (Throwable e) {
            H5Log.e((String) "H5SyncUtil", e);
        }
    }

    private static void a(String syncData, Map<String, List<AppRes>> map, List<JSONObject> listForceSync, String app_sync_log, H5AppCenterService h5AppCenterService) {
        JSONObject jsonObject = H5Utils.parseObject(syncData);
        String syncType = H5Utils.getString(jsonObject, (String) "syncType");
        JSONObject syncInfo = H5Utils.getJSONObject(jsonObject, "syncInfo", null);
        long timeStamp = H5Utils.getLong(syncInfo, (String) "timeStamp", 0);
        if ("APP_SYNC".equals(syncType)) {
            AppRes appRes = new AppRes();
            H5AppUtil.setConfig(syncInfo, appRes);
            JSONObject dataList = H5Utils.getJSONObject(syncInfo, "app", null);
            appRes.data = new ArrayList();
            AppInfo appInfo = H5AppUtil.toAppInfo(dataList);
            if (timeStamp != 0 && a()) {
                appInfo.syncTime = String.valueOf(timeStamp);
            }
            if (appInfo != null && !TextUtils.isEmpty(appInfo.app_id)) {
                appRes.data.add(appInfo);
                List list = new ArrayList();
                if (map.containsKey(appInfo.app_id)) {
                    List list2 = map.get(appInfo.app_id);
                    list2.add(appRes);
                    map.remove(appInfo.app_id);
                    map.put(appInfo.app_id, list2);
                } else {
                    list.add(appRes);
                    map.put(appInfo.app_id, list);
                }
            }
            List appIdList = new ArrayList();
            for (AppInfo info : appRes.data) {
                appIdList.add(appInfo.app_id);
                app_sync_log = app_sync_log + info.app_id + "_" + info.version + MergeUtil.SEPARATOR_KV;
            }
            H5Utils.setNebulaAppCallback(1, appIdList);
        } else if ("CLIENT_SYNC".equals(syncType)) {
            JSONObject clientConfig = H5Utils.getJSONObject(syncInfo, H5Param.CLIENT_CONFIG, null);
            if (clientConfig != null && !clientConfig.isEmpty()) {
                AppRes appRes2 = new AppRes();
                appRes2.config = H5Utils.jsonToMap(H5Utils.toJSONString(clientConfig));
                h5AppCenterService.setUpInfo(appRes2, true);
                H5LogUtil.logNebulaTech(H5LogData.seedId("H5_APP_BIZ_SYNC").param1().add("monitor", null).param3().add("step", "config").param4().add("step", "config").add("list", clientConfig));
            }
        } else if ("FORCE_SYNC".equals(syncType)) {
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_FORCE_SYNC"))) {
                H5Log.d("H5SyncUtil", "add force sync to list");
                listForceSync.add(syncInfo);
            }
        } else if ("Request".equals(syncType)) {
            long time = Long.parseLong(H5Utils.getString(syncInfo, (String) "expire")) * 1000;
            if (time <= d) {
                time = d;
            }
            d = time;
            H5Log.d("H5SyncUtil", "expireTime : " + d);
        }
    }

    public static void forceSync(JSONObject syncInfo) {
        H5Log.d("H5SyncUtil", "forceSync");
        String endTime = H5Utils.getString(syncInfo, (String) AppInitCallback.SP_KEY_endTime);
        String hash = H5Utils.getString(syncInfo, (String) "hash");
        JSONArray apps = H5Utils.getJSONArray(syncInfo, "apps", null);
        final H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider == null) {
            H5Log.e((String) "H5SyncUtil", (String) "h5AppProvider == null");
        } else if (!TextUtils.isEmpty(endTime) && !TextUtils.isEmpty(hash)) {
            try {
                SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                bartDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                Date endTimeDate = bartDateFormat.parse(endTime);
                long time = 0;
                H5TimeService h5TimeService = (H5TimeService) H5Utils.getProvider(H5TimeService.class.getName());
                if (h5TimeService != null) {
                    time = h5TimeService.getServerTime();
                }
                if (time == 0) {
                    time = System.currentTimeMillis();
                }
                if (time < endTimeDate.getTime() && apps != null) {
                    if (apps.size() == 0) {
                        AnonymousClass1 r0 = new TimerTask() {
                            public final void run() {
                                h5AppProvider.updateApp(H5UpdateAppParam.newBuilder().setDownLoadAmr(true).build());
                            }
                        };
                        ScheduledThreadPoolExecutor scheduledExecutor = H5Utils.getScheduledExecutor();
                        scheduledExecutor.schedule(r0, (long) (Math.random() * ((double) Integer.parseInt(hash))), TimeUnit.SECONDS);
                    } else {
                        final Map appMap = new HashMap();
                        for (int index = 0; index <= apps.size() - 1; index++) {
                            appMap.put(H5Utils.getString((JSONObject) apps.get(index), (String) "app_id"), null);
                        }
                        AnonymousClass2 r02 = new Runnable() {
                            public final void run() {
                                h5AppProvider.updateApp(H5UpdateAppParam.newBuilder().setAppMap(appMap).setDownLoadAmr(true).build());
                            }
                        };
                        ScheduledThreadPoolExecutor scheduledExecutor2 = H5Utils.getScheduledExecutor();
                        scheduledExecutor2.schedule(r02, (long) (Math.random() * ((double) Integer.parseInt(hash))), TimeUnit.SECONDS);
                    }
                }
            } catch (Exception e) {
                H5Log.e("H5SyncUtil", LogCategory.CATEGORY_EXCEPTION, e);
            }
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_APP_BIZ_SYNC").param1().add("monitor", null).param3().add("step", "forceReq").param4().add("step", "forceReq").add("list", syncInfo));
        }
    }

    private static boolean a() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_use_syncTime"))) {
            return true;
        }
        return false;
    }
}
