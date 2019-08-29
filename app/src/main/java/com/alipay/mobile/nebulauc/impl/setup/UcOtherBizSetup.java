package com.alipay.mobile.nebulauc.impl.setup;

import android.text.TextUtils;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider.OnConfigChangeListener;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.uc.webview.export.CDParamKeys;
import com.uc.webview.export.extension.UCSettings;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UcOtherBizSetup {
    private static final String KEY_FOCUS_AUTO_POPUP_INPUT_WHITELIST = "u4_focus_auto_popup_input_list";
    private static final String TAG = "H5UcService::UcOtherBizSetup";
    private static OnConfigChangeListener focusAutoPopupInputListener = new OnConfigChangeListener() {
        public void onChange(String value) {
            UcBizSetupHelper.configure(value, "u4_focus_auto_popup_input_list");
        }
    };
    private static final Map<String, Set<String>> inPageRenderEnableDomains = new ConcurrentHashMap();

    public static void init() {
        initFocusAutoPopupInput();
        initEmbedRenderBlackList();
    }

    private static void initEmbedRenderBlackList() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            String configValue = h5ConfigProvider.getConfig("h5_ucInPageRenderWhiteList");
            parseInPageRenderConfig(configValue);
            H5Log.d(TAG, "h5_ucInPageRenderWhiteList " + configValue);
            updateInPageSettings();
        }
    }

    private static void parseInPageRenderConfig(String configStr) {
        String[] rules;
        if (!TextUtils.isEmpty(configStr)) {
            for (String rule : configStr.split(CDParamKeys.CD_VALUE_STRING_SPLITER)) {
                if (!TextUtils.isEmpty(rule)) {
                    String[] items = rule.split(",");
                    if (items.length >= 2) {
                        String host = items[0];
                        if (!TextUtils.isEmpty(host)) {
                            Set typeSet = inPageRenderEnableDomains.get(host);
                            if (typeSet == null) {
                                typeSet = new HashSet();
                                inPageRenderEnableDomains.put(host, typeSet);
                            }
                            typeSet.addAll(Arrays.asList(items).subList(1, items.length));
                        }
                    }
                }
            }
        }
    }

    public static void enableInPageRender(String host, Set<String> type) {
        Set typeSet = inPageRenderEnableDomains.get(host);
        if (typeSet == null) {
            typeSet = new HashSet();
            inPageRenderEnableDomains.put(host, typeSet);
        }
        typeSet.addAll(type);
        updateInPageSettings();
    }

    private static void updateInPageSettings() {
        StringBuilder configStr = new StringBuilder("0");
        for (Entry entry : inPageRenderEnableDomains.entrySet()) {
            configStr.append("^^").append((String) entry.getKey());
            for (String value : (Set) entry.getValue()) {
                configStr.append(",").append(value);
            }
        }
        H5Log.d(TAG, "enableInPageRender " + configStr);
        UCSettings.updateBussinessInfo(1, 1, UCSettings.CD_RESOURCE_HYBRID_RENDER_EMBED_VIEW_ENABLE_LIST, configStr.toString());
    }

    private static void initFocusAutoPopupInput() {
        if (H5Utils.isUCM57()) {
            long start = System.currentTimeMillis();
            UcSetupTracing.beginTrace("initFocusAutoPopupInput");
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider != null) {
                UcBizSetupHelper.configure(h5ConfigProvider.getConfigWithNotifyChange("h5_ucFocusAutoPopupInput", focusAutoPopupInputListener), "u4_focus_auto_popup_input_list");
            }
            UcSetupTracing.endTrace("initFocusAutoPopupInput");
            H5Log.d(TAG, "initFocusAutoPopupInput cost:" + (System.currentTimeMillis() - start));
        }
    }
}
