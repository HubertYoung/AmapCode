package com.alipay.mobile.nebulaappcenter.dbdao;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean;
import java.util.HashMap;
import java.util.Map;

public class H5AppInfoUtil {
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0199  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean a(com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean r10, com.alipay.mobile.nebula.appcenter.model.AppInfo r11, boolean r12) {
        /*
            if (r10 != 0) goto L_0x0007
            com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean r10 = new com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean
            r10.<init>()
        L_0x0007:
            java.lang.String r7 = r11.name
            r10.setName(r7)
            java.lang.String r7 = r11.patch
            r10.setPatch(r7)
            int r7 = r11.online
            r10.setOnline(r7)
            int r7 = r11.auto_install
            r10.setAuto_install(r7)
            java.lang.String r7 = r11.app_dsec
            r10.setApp_dsec(r7)
            java.lang.String r7 = r11.fallback_base_url
            r10.setFallback_base_url(r7)
            java.lang.String r7 = r11.icon_url
            r10.setIcon_url(r7)
            java.lang.String r7 = r11.sub_url
            r10.setSub_url(r7)
            java.lang.String r7 = r11.vhost
            r10.setVhost(r7)
            java.lang.String r7 = r11.extend_info_jo
            r10.setExtend_info(r7)
            java.lang.String r7 = r11.package_url
            r10.setPackage_url(r7)
            long r8 = r11.size
            java.lang.Long r7 = java.lang.Long.valueOf(r8)
            r10.setSize(r7)
            java.lang.String r7 = r11.main_url
            r10.setMain_url(r7)
            java.lang.String r7 = r11.system_max
            r10.setSystem_max(r7)
            java.lang.String r7 = r11.system_min
            r10.setSystem_min(r7)
            java.lang.String r7 = com.alipay.mobile.nebulaappcenter.dbapi.H5DaoTemplate.a()
            r10.setUser_id(r7)
            java.lang.String r7 = r11.app_id
            r10.setApp_id(r7)
            java.lang.String r7 = r11.version
            r10.setVersion(r7)
            java.lang.String r7 = r11.third_platform
            r10.setThird_platform(r7)
            int r7 = r11.app_type
            r10.setApp_type(r7)
            int r7 = r11.app_channel
            r10.setApp_channel(r7)
            java.lang.String r7 = r11.release_type
            r10.setRelease_type(r7)
            java.lang.String r7 = r11.nbl_id
            r10.setNbl_id(r7)
            java.lang.String r7 = r11.nbAppType
            r10.setNbAppType(r7)
            java.lang.String r7 = r11.slogan
            r10.setSlogan(r7)
            java.lang.String r7 = r11.unAvailableReason
            r10.setUnAvailableReason(r7)
            int r7 = r11.is_mapping
            r10.setIs_mapping(r7)
            int r7 = r11.is_limit
            r10.setIs_limit(r7)
            java.lang.String r7 = r11.syncTime
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x01a1
            java.lang.String r7 = "H5AppInfoUtil"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "setUpdate_app_time for syncTime "
            r8.<init>(r9)
            java.lang.String r9 = com.alipay.mobile.nebulaappcenter.dbapi.H5DaoTemplate.a()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = " "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r11.app_id
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = " "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r11.version
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = " "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r11.syncTime
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r7, r8)
            java.lang.String r7 = r11.syncTime
            r11.update_app_time = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = r11.syncTime
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r10.setUpdate_app_time(r7)
        L_0x00f3:
            int r7 = r11.localReport
            r10.setLocalReport(r7)
            java.lang.String r7 = r11.extend_info_jo     // Catch:{ Exception -> 0x0236 }
            com.alibaba.fastjson.JSONObject r1 = com.alipay.mobile.nebula.util.H5Utils.parseObject(r7)     // Catch:{ Exception -> 0x0236 }
            if (r1 == 0) goto L_0x01b7
            boolean r7 = r1.isEmpty()     // Catch:{ Exception -> 0x0236 }
            if (r7 != 0) goto L_0x01b7
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ Exception -> 0x0236 }
            r6.<init>()     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "launchMode"
            java.lang.String r8 = "launchMode"
            java.lang.String r8 = r1.getString(r8)     // Catch:{ Exception -> 0x0236 }
            r6.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "host"
            r8 = 0
            com.alibaba.fastjson.JSONObject r4 = com.alipay.mobile.nebula.util.H5Utils.getJSONObject(r1, r7, r8)     // Catch:{ Exception -> 0x0236 }
            if (r4 == 0) goto L_0x0125
            boolean r7 = r4.isEmpty()     // Catch:{ Exception -> 0x0236 }
            if (r7 == 0) goto L_0x0149
        L_0x0125:
            com.alibaba.fastjson.JSONObject r4 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0236 }
            r4.<init>()     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "enable"
            r8 = 1
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r8)     // Catch:{ Exception -> 0x0236 }
            r4.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "dev"
            java.lang.String r8 = r11.vhost     // Catch:{ Exception -> 0x0236 }
            r4.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "test"
            java.lang.String r8 = r11.vhost     // Catch:{ Exception -> 0x0236 }
            r4.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "online"
            java.lang.String r8 = r11.vhost     // Catch:{ Exception -> 0x0236 }
            r4.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
        L_0x0149:
            java.lang.String r7 = "host"
            java.lang.String r8 = com.alipay.mobile.nebula.util.H5Utils.toJSONString(r4)     // Catch:{ Exception -> 0x0236 }
            r6.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "launchParams"
            r8 = 0
            com.alibaba.fastjson.JSONObject r5 = com.alipay.mobile.nebula.util.H5Utils.getJSONObject(r1, r7, r8)     // Catch:{ Exception -> 0x0236 }
            if (r5 == 0) goto L_0x0178
            boolean r7 = r5.isEmpty()     // Catch:{ Exception -> 0x0236 }
            if (r7 != 0) goto L_0x0178
            java.lang.String r7 = "url"
            boolean r7 = r5.containsKey(r7)     // Catch:{ Exception -> 0x0236 }
            if (r7 != 0) goto L_0x0178
            java.lang.String r7 = r11.main_url     // Catch:{ Exception -> 0x0236 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Exception -> 0x0236 }
            if (r7 != 0) goto L_0x0178
            java.lang.String r7 = "url"
            java.lang.String r8 = r11.main_url     // Catch:{ Exception -> 0x0236 }
            r5.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
        L_0x0178:
            if (r5 == 0) goto L_0x0180
            boolean r7 = r5.isEmpty()     // Catch:{ Exception -> 0x0236 }
            if (r7 == 0) goto L_0x018c
        L_0x0180:
            com.alibaba.fastjson.JSONObject r5 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0236 }
            r5.<init>()     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "url"
            java.lang.String r8 = r11.main_url     // Catch:{ Exception -> 0x0236 }
            r5.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
        L_0x018c:
            java.lang.String r7 = "launchParams"
            java.lang.String r8 = com.alipay.mobile.nebula.util.H5Utils.toJSONString(r5)     // Catch:{ Exception -> 0x0236 }
            r6.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            r11.extend_info = r6     // Catch:{ Exception -> 0x0236 }
        L_0x0197:
            if (r12 != 0) goto L_0x01a0
            com.alipay.mobile.nebulaappcenter.service.H5MemoryCache r7 = com.alipay.mobile.nebulaappcenter.service.H5MemoryCache.a()
            r7.a(r11)
        L_0x01a0:
            return r10
        L_0x01a1:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            long r8 = java.lang.System.currentTimeMillis()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r10.setUpdate_app_time(r7)
            goto L_0x00f3
        L_0x01b7:
            com.alibaba.fastjson.JSONObject r2 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0236 }
            r2.<init>()     // Catch:{ Exception -> 0x0236 }
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ Exception -> 0x0236 }
            r6.<init>()     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "launchMode"
            java.lang.String r8 = "NebulaApp"
            r2.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            com.alibaba.fastjson.JSONObject r4 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0236 }
            r4.<init>()     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "url"
            java.lang.String r8 = r11.main_url     // Catch:{ Exception -> 0x0236 }
            r4.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "launchParams"
            r2.put(r7, r4)     // Catch:{ Exception -> 0x0236 }
            com.alibaba.fastjson.JSONObject r3 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0236 }
            r3.<init>()     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "enable"
            r8 = 1
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r8)     // Catch:{ Exception -> 0x0236 }
            r3.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "dev"
            java.lang.String r8 = r11.vhost     // Catch:{ Exception -> 0x0236 }
            r3.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "test"
            java.lang.String r8 = r11.vhost     // Catch:{ Exception -> 0x0236 }
            r3.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "online"
            java.lang.String r8 = r11.vhost     // Catch:{ Exception -> 0x0236 }
            r3.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "host"
            r2.put(r7, r3)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "launchMode"
            java.lang.String r8 = "launchMode"
            r9 = 0
            com.alibaba.fastjson.JSONObject r8 = com.alipay.mobile.nebula.util.H5Utils.getJSONObject(r2, r8, r9)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r8 = com.alipay.mobile.nebula.util.H5Utils.toJSONString(r8)     // Catch:{ Exception -> 0x0236 }
            r6.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "host"
            java.lang.String r8 = "host"
            r9 = 0
            com.alibaba.fastjson.JSONObject r8 = com.alipay.mobile.nebula.util.H5Utils.getJSONObject(r2, r8, r9)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r8 = com.alipay.mobile.nebula.util.H5Utils.toJSONString(r8)     // Catch:{ Exception -> 0x0236 }
            r6.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r7 = "launchParams"
            java.lang.String r8 = "launchParams"
            r9 = 0
            com.alibaba.fastjson.JSONObject r8 = com.alipay.mobile.nebula.util.H5Utils.getJSONObject(r2, r8, r9)     // Catch:{ Exception -> 0x0236 }
            java.lang.String r8 = com.alipay.mobile.nebula.util.H5Utils.toJSONString(r8)     // Catch:{ Exception -> 0x0236 }
            r6.put(r7, r8)     // Catch:{ Exception -> 0x0236 }
            r11.extend_info = r6     // Catch:{ Exception -> 0x0236 }
            goto L_0x0197
        L_0x0236:
            r0 = move-exception
            java.lang.String r7 = "H5AppInfoUtil"
            com.alipay.mobile.nebula.util.H5Log.e(r7, r0)
            goto L_0x0197
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulaappcenter.dbdao.H5AppInfoUtil.a(com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean, com.alipay.mobile.nebula.appcenter.model.AppInfo, boolean):com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean");
    }

    public static AppInfo a(H5NebulaAppBean h5AppInfoBean) {
        AppInfo appInfo = new AppInfo();
        appInfo.name = h5AppInfoBean.getName();
        appInfo.patch = h5AppInfoBean.getPatch();
        appInfo.online = h5AppInfoBean.getOnline();
        appInfo.auto_install = h5AppInfoBean.getAuto_install();
        appInfo.app_dsec = h5AppInfoBean.getApp_dsec();
        appInfo.fallback_base_url = h5AppInfoBean.getFallback_base_url();
        appInfo.icon_url = h5AppInfoBean.getIcon_url();
        appInfo.sub_url = h5AppInfoBean.getSub_url();
        appInfo.vhost = h5AppInfoBean.getVhost();
        appInfo.extend_info_jo = h5AppInfoBean.getExtend_info();
        appInfo.package_url = h5AppInfoBean.getPackage_url();
        appInfo.size = h5AppInfoBean.getSize().longValue();
        appInfo.main_url = h5AppInfoBean.getMain_url();
        appInfo.system_max = h5AppInfoBean.getSystem_max();
        appInfo.system_min = h5AppInfoBean.getSystem_min();
        appInfo.app_id = h5AppInfoBean.getApp_id();
        appInfo.version = h5AppInfoBean.getVersion();
        appInfo.third_platform = h5AppInfoBean.getThird_platform();
        appInfo.app_type = h5AppInfoBean.app_type;
        appInfo.app_channel = h5AppInfoBean.app_channel;
        appInfo.release_type = h5AppInfoBean.getRelease_type();
        appInfo.is_mapping = h5AppInfoBean.getIs_mapping();
        appInfo.is_mapping = h5AppInfoBean.getIs_limit();
        appInfo.update_app_time = h5AppInfoBean.getUpdate_app_time();
        appInfo.localReport = h5AppInfoBean.getLocalReport();
        appInfo.nbAppType = h5AppInfoBean.getNbAppType();
        appInfo.nbl_id = h5AppInfoBean.getNbl_id();
        appInfo.slogan = h5AppInfoBean.getSlogan();
        appInfo.unAvailableReason = h5AppInfoBean.getUnAvailableReason();
        try {
            JSONObject extraInfo = H5Utils.parseObject(appInfo.extend_info_jo);
            if (extraInfo == null || extraInfo.isEmpty()) {
                JSONObject extraJO = new JSONObject();
                Map map = new HashMap();
                extraJO.put((String) H5Param.LAUNCHER_MODE, (Object) H5Param.NEBULA_APP);
                JSONObject hostJson = new JSONObject();
                hostJson.put((String) "url", (Object) appInfo.main_url);
                extraJO.put((String) H5Param.LAUNCHER_PARAM, (Object) hostJson);
                JSONObject host = new JSONObject();
                host.put((String) "enable", (Object) Boolean.valueOf(true));
                host.put((String) "dev", (Object) appInfo.vhost);
                host.put((String) "test", (Object) appInfo.vhost);
                host.put((String) "online", (Object) appInfo.vhost);
                extraJO.put((String) "host", (Object) host);
                map.put(H5Param.LAUNCHER_MODE, H5Utils.toJSONString(H5Utils.getJSONObject(extraJO, H5Param.LAUNCHER_MODE, null)));
                map.put("host", H5Utils.toJSONString(H5Utils.getJSONObject(extraJO, "host", null)));
                map.put(H5Param.LAUNCHER_PARAM, H5Utils.toJSONString(H5Utils.getJSONObject(extraJO, H5Param.LAUNCHER_PARAM, null)));
                appInfo.extend_info = map;
                return appInfo;
            }
            Map map2 = new HashMap();
            map2.put(H5Param.LAUNCHER_MODE, extraInfo.getString(H5Param.LAUNCHER_MODE));
            JSONObject hostJson2 = H5Utils.getJSONObject(extraInfo, "host", null);
            if (hostJson2 == null || hostJson2.isEmpty()) {
                hostJson2 = new JSONObject();
                hostJson2.put((String) "enable", (Object) Boolean.valueOf(true));
                hostJson2.put((String) "dev", (Object) appInfo.vhost);
                hostJson2.put((String) "test", (Object) appInfo.vhost);
                hostJson2.put((String) "online", (Object) appInfo.vhost);
            }
            map2.put("host", H5Utils.toJSONString(hostJson2));
            JSONObject launcherJson = H5Utils.getJSONObject(extraInfo, H5Param.LAUNCHER_PARAM, null);
            if (launcherJson != null && !launcherJson.isEmpty() && !launcherJson.containsKey("url") && !TextUtils.isEmpty(appInfo.main_url)) {
                launcherJson.put((String) "url", (Object) appInfo.main_url);
            }
            if (launcherJson == null || launcherJson.isEmpty()) {
                launcherJson = new JSONObject();
                launcherJson.put((String) "url", (Object) appInfo.main_url);
            }
            map2.put(H5Param.LAUNCHER_PARAM, H5Utils.toJSONString(launcherJson));
            appInfo.extend_info = map2;
            return appInfo;
        } catch (Exception e) {
            H5Log.e((String) "H5AppInfoUtil", (Throwable) e);
        }
    }
}
