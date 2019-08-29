package com.alipay.mobile.tinyappcommon.template;

import android.os.Bundle;
import android.support.annotation.Keep;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.startParam.H5StartParamInfo;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5TabbarUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.appcenter.parse.H5PackageParser;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcommon.api.TinyAppMixActionService;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Keep
public class TemplateTinyApp {
    public static final String EXT_ENABLE_KEY = "extEnable";
    private static final String EXT_PAGES_KEY = "extPages";
    private static int MAX_RETRY_TIME = 10;
    private static int RETRY_TIME_INTERNAL = 200;
    private static final String TABBAR_ITEMS_KEY = "items";
    private static final String TABBAR_ITEMS_STARTUP_KEY = "tabBarItems";
    public static final String TABBAR_KEY = "tabBar";
    /* access modifiers changed from: private */
    public static final String TAG = TemplateTinyApp.class.getSimpleName();
    private static final String TEMPLATE_APP_ID_KEY = "templateAppId";
    private static final String TEMPLATE_APP_KEY = "templateApp";
    public static final String TEMPLATE_CONFIG_KEY = "templateConfig";
    private static final String TEMPLATE_CONFIG_URL_KEY = "templateConfigUrl";
    public static final String WINDOW_KEY = "window";
    private int mCurrentRetryCount;
    private ConcurrentHashMap<String, JSONArray> mTabBarStartupParamsMap;
    private ConcurrentHashMap<String, String> mTemplateAppMap;

    private static class a {
        /* access modifiers changed from: private */
        public static final TemplateTinyApp a = new TemplateTinyApp();
    }

    public static TemplateTinyApp getInstance() {
        return a.a;
    }

    private TemplateTinyApp() {
        this.mCurrentRetryCount = 0;
        this.mTemplateAppMap = new ConcurrentHashMap<>();
        this.mTabBarStartupParamsMap = new ConcurrentHashMap<>();
    }

    public void prepareTemplateConfig(final AppInfo appInfo, final Bundle startupParams) {
        H5Utils.runNotOnMain(H5ThreadType.URGENT, new Runnable() {
            public final void run() {
                TinyAppMixActionService mixActionService = TinyAppService.get().getMixActionService();
                if (mixActionService != null && !mixActionService.isEnableTemplateApp()) {
                    return;
                }
                if (appInfo == null) {
                    H5Log.w(TemplateTinyApp.TAG, "prepareTemplateConfig...appInfo is null");
                    return;
                }
                String extendInfo = appInfo.extend_info_jo;
                if (TextUtils.isEmpty(extendInfo)) {
                    H5Log.w(TemplateTinyApp.TAG, "prepareTemplateConfig...extendInfo is null");
                    return;
                }
                try {
                    JSONObject extendInfoJson = JSONObject.parseObject(extendInfo);
                    String launchParams = extendInfoJson.getString(H5Param.LAUNCHER_PARAM);
                    if (TextUtils.isEmpty(launchParams)) {
                        H5Log.d(TemplateTinyApp.TAG, "prepareTemplateConfig...launchParams is null");
                        return;
                    }
                    JSONObject launchParamsJson = H5Utils.parseObject(launchParams);
                    if (launchParamsJson != null && !TextUtils.isEmpty(launchParamsJson.getString(TemplateTinyApp.TEMPLATE_APP_ID_KEY))) {
                        String templateConfigUrl = extendInfoJson.getString(TemplateTinyApp.TEMPLATE_CONFIG_URL_KEY);
                        if (TextUtils.isEmpty(templateConfigUrl)) {
                            JSONObject templateConfig = extendInfoJson.getJSONObject(TemplateTinyApp.TEMPLATE_CONFIG_KEY);
                            if (templateConfig != null && !templateConfig.isEmpty()) {
                                H5Log.d(TemplateTinyApp.TAG, "prepareTemplateConfig..." + appInfo.app_id);
                            } else if (H5Utils.isDebug()) {
                                H5Log.d(TemplateTinyApp.TAG, "prepareTemplateConfig...not template app: " + appInfo.app_id);
                                return;
                            } else {
                                return;
                            }
                        } else {
                            H5Log.d(TemplateTinyApp.TAG, "prepareTemplateConfig url mode..." + appInfo.app_id);
                            if (startupParams != null) {
                                startupParams.putString(TemplateTinyApp.TEMPLATE_CONFIG_URL_KEY, templateConfigUrl);
                            }
                            TemplateTinyApp.this.downloadTemplateConfig(appInfo.app_id, templateConfigUrl);
                        }
                        if (startupParams != null) {
                            startupParams.putBoolean(TemplateTinyApp.TEMPLATE_APP_KEY, true);
                        }
                        H5LogData logData = H5LogData.seedId("TINY_TEMPLATE_APP");
                        H5LogData add = logData.param4().add("appId", appInfo.app_id);
                        if (TextUtils.isEmpty(templateConfigUrl)) {
                            templateConfigUrl = Token.SEPARATOR;
                        }
                        add.add(TemplateTinyApp.TEMPLATE_CONFIG_URL_KEY, templateConfigUrl);
                        H5LogUtil.logNebulaTech(logData);
                    }
                } catch (Throwable e) {
                    H5Log.e(TemplateTinyApp.TAG, "prepareTemplateConfig e..." + e);
                }
            }
        });
    }

    private JSONObject readTemplateConfigFromExtendInfo(String appId, Bundle startupParams) {
        String appVersion = startupParams.getString("appVersion");
        try {
            H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (h5AppProvider == null) {
                return null;
            }
            String extendInfo = h5AppProvider.getExtraJo(appId, appVersion);
            if (TextUtils.isEmpty(extendInfo)) {
                H5Log.d(TAG, "readTemplateConfigFromExtendInfo...extendInfo is null");
                return null;
            }
            JSONObject extendInfoJo = JSONObject.parseObject(extendInfo);
            if (extendInfoJo == null) {
                H5Log.d(TAG, "readTemplateConfigFromExtendInfo...extendInfoJo is null");
                return null;
            }
            if (H5Utils.isDebug()) {
                H5Log.d(TAG, "readTemplateConfigFromExtendInfo..." + extendInfoJo.toJSONString());
            }
            return extendInfoJo.getJSONObject(TEMPLATE_CONFIG_KEY);
        } catch (Throwable e) {
            H5Log.e(TAG, "readTemplateConfigFromExtendInfo...e: " + e);
            return null;
        }
    }

    private JSONObject readTemplateConfigFromInstalledPath(String appId, String templateConfigUrl) {
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(templateConfigUrl)) {
            return null;
        }
        a.a();
        String downloadPath = a.b(appId, templateConfigUrl);
        if (TextUtils.isEmpty(downloadPath)) {
            H5Log.w(TAG, "readTemplateConfig...downloadPath is null");
            return null;
        }
        File file = new File(downloadPath);
        if (file.isFile() && file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                inputStream.close();
                if (H5Utils.isDebug()) {
                    H5Log.d(TAG, "readTemplateConfig... " + new String(buffer));
                }
                return JSONObject.parseObject(new String(buffer));
            } catch (Throwable e) {
                H5Log.e(TAG, "readTemplateConfig..read file e: " + e);
                return null;
            }
        } else if (this.mCurrentRetryCount >= MAX_RETRY_TIME) {
            H5Log.w(TAG, "readTemplateConfig...timeout null");
            this.mCurrentRetryCount = 0;
            H5LogData logData = H5LogData.seedId("TINY_GET_TEMPLATE_CONFIG_FAILED");
            logData.param4().add("appId", appId).add(TEMPLATE_CONFIG_URL_KEY, templateConfigUrl);
            H5LogUtil.logNebulaTech(logData);
            return null;
        } else {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e2) {
                H5Log.e(TAG, "readTemplateConfig...e: " + e2);
            }
            this.mCurrentRetryCount++;
            return readTemplateConfigFromInstalledPath(appId, templateConfigUrl);
        }
    }

    private void initMaxRetryTime() {
        TinyAppMixActionService mixActionService = TinyAppService.get().getMixActionService();
        if (mixActionService != null) {
            MAX_RETRY_TIME = mixActionService.getTemplateAppWaitTime() / RETRY_TIME_INTERNAL;
        }
    }

    public JSONObject readTemplateConfig(String appId, Bundle startupParams) {
        if (startupParams == null) {
            return null;
        }
        initMaxRetryTime();
        try {
            if (startupParams.getBoolean("fromPreload")) {
                JSONObject extendInfo = readTemplateConfigFromExtendInfo(appId, startupParams);
                String templateConfigUrl = extendInfo.getString(TEMPLATE_CONFIG_URL_KEY);
                if (!TextUtils.isEmpty(templateConfigUrl)) {
                    return readTemplateConfigFromInstalledPath(appId, templateConfigUrl);
                }
                JSONObject templateConfig = extendInfo.getJSONObject(TEMPLATE_CONFIG_KEY);
                if (templateConfig == null || templateConfig.isEmpty()) {
                    return null;
                }
                H5Log.d(TAG, "readTemplateConfig..." + appId);
                return templateConfig;
            } else if (!startupParams.getBoolean(TEMPLATE_APP_KEY)) {
                return null;
            } else {
                String templateConfigUrl2 = startupParams.getString(TEMPLATE_CONFIG_URL_KEY);
                H5Log.d(TAG, "readTemplateConfig...url: " + templateConfigUrl2);
                if (TextUtils.isEmpty(templateConfigUrl2)) {
                    return readTemplateConfigFromExtendInfo(appId, startupParams);
                }
                return readTemplateConfigFromInstalledPath(appId, templateConfigUrl2);
            }
        } catch (Throwable e) {
            H5Log.e(TAG, "readTemplateConfig..e=" + e);
            return null;
        }
    }

    public String getTemplateAppId(String instanceAppId, Bundle startupParams) {
        String templateAppId = null;
        TinyAppMixActionService mixActionService = TinyAppService.get().getMixActionService();
        if (mixActionService == null || mixActionService.isEnableTemplateApp()) {
            if (TextUtils.isEmpty(H5Utils.getString(startupParams, (String) "fromPreload"))) {
                templateAppId = H5Utils.getString(startupParams, (String) TEMPLATE_APP_ID_KEY);
            } else {
                String appVersion = startupParams.getString("appVersion");
                H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                if (h5AppProvider != null) {
                    Map extendInfo = h5AppProvider.getExtra(instanceAppId, appVersion);
                    if (extendInfo == null) {
                        H5Log.d(TAG, "getTemplateAppId...extendInfo is null");
                    } else {
                        String launchParams = extendInfo.get(H5Param.LAUNCHER_PARAM);
                        if (TextUtils.isEmpty(launchParams)) {
                            H5Log.d(TAG, "getTemplateAppId...launchParams is null");
                        } else {
                            JSONObject launchParamsJson = H5Utils.parseObject(launchParams);
                            if (launchParamsJson != null) {
                                templateAppId = launchParamsJson.getString(TEMPLATE_APP_ID_KEY);
                            }
                        }
                    }
                }
            }
            if (!TextUtils.isEmpty(templateAppId)) {
                this.mTemplateAppMap.put(instanceAppId, templateAppId);
            }
        }
        return templateAppId;
    }

    public String modifyApiPermission(String instanceAppId, String apiPermission) {
        TinyAppMixActionService mixActionService = TinyAppService.get().getMixActionService();
        if (mixActionService != null && !mixActionService.isEnableTemplateApp()) {
            return apiPermission;
        }
        String templateAppId = this.mTemplateAppMap.get(instanceAppId);
        if (TextUtils.isEmpty(templateAppId) || TextUtils.isEmpty(instanceAppId) || TextUtils.isEmpty(apiPermission)) {
            return apiPermission;
        }
        H5Log.d(TAG, "modifyApiPermission...template app modify api_per:" + instanceAppId);
        return apiPermission.replace(templateAppId, instanceAppId);
    }

    public void dynamicMergeTemplateConfig(String appId, Bundle startupParams, Map<String, byte[]> resPkg) {
        TinyAppMixActionService mixActionService = TinyAppService.get().getMixActionService();
        if ((mixActionService == null || mixActionService.isEnableTemplateApp()) && resPkg != null && !resPkg.isEmpty()) {
            JSONObject config = readTemplateConfig(appId, startupParams);
            if (config != null) {
                Object object = config.get(EXT_ENABLE_KEY);
                if (!(object instanceof Boolean) || ((Boolean) object).booleanValue()) {
                    H5Log.d(TAG, "dynamicMergeTemplateConfig..." + appId);
                    mergeWindowTag(appId, config.get(WINDOW_KEY), resPkg);
                    mergeExtPagesTag(appId, config.get(EXT_PAGES_KEY), config.get(WINDOW_KEY), resPkg);
                    mergeTabBarTag(appId, config.get(TABBAR_KEY), resPkg);
                    return;
                }
                H5Log.w(TAG, "dynamicMergeTemplateConfig...ext disabled");
            }
        }
    }

    private void mergeTabBarTag(String appId, Object object, Map<String, byte[]> resPkg) {
        if (!(object instanceof JSONObject)) {
            H5Log.w(TAG, "dynamicMergeTemplateConfig...object is null");
            return;
        }
        try {
            JSONObject tabBarValue = (JSONObject) object;
            JSONArray items = tabBarValue.getJSONArray(TABBAR_ITEMS_KEY);
            if (items != null && !items.isEmpty()) {
                JSONArray tabBarItems = new JSONArray();
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    Object itemObject = iterator.next();
                    if (itemObject instanceof JSONObject) {
                        JSONObject item = (JSONObject) itemObject;
                        Object pagePathObj = item.remove("pagePath");
                        if (pagePathObj instanceof String) {
                            String pagePath = (String) pagePathObj;
                            if (!TextUtils.isEmpty(pagePath)) {
                                item.put((String) "tag", (Object) pagePath);
                                item.put((String) H5StartParamManager.launchParamsTag, (Object) pagePath);
                                item.put((String) "url", (Object) "index.html#" + pagePath);
                                JSONObject tabBarItem = new JSONObject();
                                String name = item.getString("name");
                                if (TextUtils.isEmpty(name)) {
                                    name = "";
                                }
                                tabBarItem.put((String) "name", (Object) name);
                                tabBarItem.put((String) "pagePath", (Object) pagePath);
                                tabBarItems.add(tabBarItem);
                            }
                        }
                    }
                }
                this.mTabBarStartupParamsMap.put(appId, tabBarItems);
            }
            H5Log.w(TAG, "dynamicMergeTemplateConfig...tabBar merge");
            String tabBarKey = getEntryKey(appId, H5PackageParser.ENTRY_TABBAR_JSON, resPkg);
            byte[] tabBarBytes = tabBarValue.toJSONString().getBytes();
            resPkg.put(tabBarKey, tabBarBytes);
            H5TabbarUtils.setTabData(appId, tabBarBytes);
        } catch (Throwable e) {
            H5Log.e(TAG, "mergeTabBarTag...e:" + e);
        }
    }

    private String getEntryKey(String appId, String key, Map<String, byte[]> resPkg) {
        if (resPkg == null) {
            return null;
        }
        String keyName = null;
        Iterator<String> it = resPkg.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String tmpKey = it.next();
            if (tmpKey.endsWith(key)) {
                keyName = tmpKey;
                break;
            }
        }
        if (TextUtils.isEmpty(keyName)) {
            return new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS).append(appId).append(".hybrid.alipay-eco.com/").append(key).toString();
        }
        return keyName;
    }

    private void mergeExtPagesTag(String appId, Object extPageObject, Object windowObject, Map<String, byte[]> resPkg) {
        if (!(extPageObject instanceof JSONObject)) {
            H5Log.w(TAG, "mergeExtPages...extPage is null");
            return;
        }
        JSONObject extPages = (JSONObject) extPageObject;
        if (extPages.isEmpty()) {
            H5Log.w(TAG, "mergeExtPages...extPage is empty");
            return;
        }
        if (windowObject instanceof JSONObject) {
            JSONObject window = (JSONObject) windowObject;
            for (String key : extPages.keySet()) {
                JSONObject item = extPages.getJSONObject(key);
                if (item != null) {
                    JSONObject afterWindow = (JSONObject) window.clone();
                    for (String itemKey : item.keySet()) {
                        if (!TextUtils.isEmpty(itemKey)) {
                            afterWindow.put(itemKey, (Object) item.getString(itemKey));
                        }
                    }
                    extPages.put(key, (Object) afterWindow);
                }
            }
        }
        for (String key2 : extPages.keySet()) {
            if (!TextUtils.isEmpty(key2)) {
                JSONObject value = extPages.getJSONObject(key2);
                boolean pageExist = false;
                List startParamInfos = H5StartParamManager.getInstance().getH5StartParamTag(appId);
                if (startParamInfos != null) {
                    Iterator it = startParamInfos.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        H5StartParamInfo paramInfo = (H5StartParamInfo) it.next();
                        if (paramInfo != null && key2.equals(paramInfo.tag)) {
                            paramInfo.param = value;
                            pageExist = true;
                            break;
                        }
                    }
                } else {
                    H5Log.w(TAG, "mergeExtPages startParamInfos is null");
                    startParamInfos = new ArrayList();
                    H5StartParamManager.getInstance().setH5StartParamTag(appId, startParamInfos);
                }
                H5Log.d(TAG, "mergeExtPages...pageExist=" + pageExist + ",key=" + key2);
                if (!pageExist) {
                    H5StartParamInfo startParamInfo = new H5StartParamInfo();
                    startParamInfo.tag = key2;
                    startParamInfo.param = value;
                    startParamInfos.add(startParamInfo);
                }
            }
        }
    }

    private void mergeWindowTag(String appId, Object object, Map<String, byte[]> resPkg) {
        if (!(object instanceof JSONObject)) {
            H5Log.w(TAG, "mergeWindowTag...object is null");
            return;
        }
        H5Log.w(TAG, "mergeWindowTag...window");
        JSONObject originalWindow = H5StartParamManager.getInstance().getWindowTag(appId);
        if (originalWindow == null || originalWindow.isEmpty()) {
            H5Log.w(TAG, "mergeWindowTag...originalWindow is null");
            return;
        }
        JSONObject extWindow = (JSONObject) object;
        if (extWindow.isEmpty()) {
            H5Log.w(TAG, "mergeWindowTag...extWindow is null");
            return;
        }
        List startParamInfos = H5StartParamManager.getInstance().getH5StartParamTag(appId);
        if (startParamInfos != null) {
            for (H5StartParamInfo startParamInfo : startParamInfos) {
                if (startParamInfo != null) {
                    JSONObject itemStartParams = startParamInfo.param;
                    for (String extWindowItem : extWindow.keySet()) {
                        if (!TextUtils.isEmpty(extWindowItem) && !CommonEvents.H5_TITLEBAR_OPTIONS.equals(extWindowItem)) {
                            try {
                                H5Log.d(TAG, "mergeWindowTag...item=" + extWindowItem);
                                Object newValue = extWindow.get(extWindowItem);
                                Object originalValue = originalWindow.get(extWindowItem);
                                Object finalValue = itemStartParams.get(extWindowItem);
                                if (newValue instanceof Integer) {
                                    if (finalValue == null || (originalValue != null && ((Integer) finalValue).intValue() == ((Integer) originalValue).intValue())) {
                                        if (originalValue == null || ((Integer) originalValue).intValue() != ((Integer) newValue).intValue()) {
                                            itemStartParams.put(extWindowItem, newValue);
                                        }
                                    }
                                } else if (newValue instanceof Boolean) {
                                    if (finalValue == null || (originalValue != null && ((Boolean) finalValue).booleanValue() == ((Boolean) originalValue).booleanValue())) {
                                        if (originalValue == null || ((Boolean) originalValue).booleanValue() != ((Boolean) newValue).booleanValue()) {
                                            itemStartParams.put(extWindowItem, newValue);
                                        }
                                    }
                                } else if (newValue instanceof String) {
                                    if (TextUtils.equals((String) finalValue, (String) originalValue) && !TextUtils.equals((String) originalValue, (String) newValue)) {
                                        itemStartParams.put(extWindowItem, newValue);
                                    }
                                } else if (newValue instanceof Long) {
                                    if (finalValue == null || (originalValue != null && ((Long) finalValue).longValue() == ((Long) originalValue).longValue())) {
                                        if (originalValue == null || ((Long) originalValue).longValue() != ((Long) newValue).longValue()) {
                                            itemStartParams.put(extWindowItem, newValue);
                                        }
                                    }
                                } else if (newValue instanceof Double) {
                                    if (finalValue == null || (originalValue != null && Math.abs(((Double) finalValue).doubleValue() - ((Double) originalValue).doubleValue()) <= 0.0d)) {
                                        if (originalValue == null || Math.abs(((Double) originalValue).doubleValue() - ((Double) newValue).doubleValue()) > 0.0d) {
                                            itemStartParams.put(extWindowItem, newValue);
                                        }
                                    }
                                } else if ((newValue instanceof Float) && (finalValue == null || (originalValue != null && Math.abs(((Float) finalValue).floatValue() - ((Float) originalValue).floatValue()) <= 0.0f))) {
                                    if (originalValue == null || Math.abs(((Float) originalValue).floatValue() - ((Float) newValue).floatValue()) > 0.0f) {
                                        itemStartParams.put(extWindowItem, newValue);
                                    }
                                }
                            } catch (Throwable e) {
                                H5Log.e(TAG, "mergeWindowTag...item:" + extWindowItem + "...e:" + e);
                            }
                        }
                    }
                }
            }
            resPkg.put(getEntryKey(appId, H5StartParamManager.appConfig, resPkg), extWindow.toJSONString().getBytes());
        }
    }

    /* access modifiers changed from: private */
    public void downloadTemplateConfig(String appId, String configUrl) {
        a.a().a(appId, configUrl);
    }

    public void addStartupParamsForTemplateApp(String appId, Bundle startupParams) {
        if (startupParams != null && !TextUtils.isEmpty(appId) && !this.mTabBarStartupParamsMap.isEmpty()) {
            TinyAppMixActionService mixActionService = TinyAppService.get().getMixActionService();
            if ((mixActionService == null || mixActionService.isEnableTemplateApp()) && startupParams.getBoolean(TEMPLATE_APP_KEY)) {
                JSONArray tabBarStartupParams = this.mTabBarStartupParamsMap.get(appId);
                if (tabBarStartupParams != null && !tabBarStartupParams.isEmpty()) {
                    startupParams.putSerializable(TABBAR_ITEMS_STARTUP_KEY, tabBarStartupParams);
                }
            }
        }
    }
}
