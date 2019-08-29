package com.alipay.mobile.nebula.util;

import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5DisClaimerProvider;
import com.alipay.mobile.nebula.provider.H5WarningTipProvider;
import java.util.HashSet;

public class H5CdpAdvertisementController {
    private static final String TAG = "H5CdpAdvertisement";
    private static Boolean enable = null;
    private static float innerSiteData = 0.0f;
    private static float outerSiteData = 0.0f;

    public static void closeAdvertisement(Intent intent, H5Page h5Page) {
        HashSet hashSet = h5Page.getPageData().getWarningTipSet();
        String adId = intent.getStringExtra("adId");
        H5DisClaimerProvider disClaimerProvider = (H5DisClaimerProvider) H5Utils.getProvider(H5DisClaimerProvider.class.getName());
        if (!TextUtils.isEmpty(adId) && disClaimerProvider != null && enableShow()) {
            if (adId.startsWith("LOCAL_H5Page")) {
                adId = "disclaimer";
            }
            if (hashSet.size() == 1) {
                hashSet.clear();
            }
            if (hashSet.size() > 1 && hashSet.contains(adId)) {
                hashSet.remove(adId);
                if (hashSet.contains("dataFlow")) {
                    showDataFlowTip(h5Page);
                    H5Log.d(TAG, new StringBuilder(DataflowMonitorModel.METHOD_NAME_CLOSE).append(adId).append("and show dataFlow").toString());
                } else if (hashSet.contains("disclaimer")) {
                    disClaimerProvider.showDisclaimer(h5Page, 1);
                    H5Log.d(TAG, new StringBuilder(DataflowMonitorModel.METHOD_NAME_CLOSE).append(adId).append("and show disclaimer").toString());
                }
            }
        }
    }

    private static boolean enableShow() {
        if (enable == null) {
            H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (configProvider == null) {
                Boolean valueOf = Boolean.valueOf(false);
                enable = valueOf;
                return valueOf.booleanValue();
            }
            String value = configProvider.getConfigWithProcessCache("h5_dataFlowConfig");
            if (TextUtils.isEmpty(value)) {
                Boolean valueOf2 = Boolean.valueOf(false);
                enable = valueOf2;
                return valueOf2.booleanValue();
            }
            JSONObject jsonObject = H5Utils.parseObject(value);
            if ("yes".equalsIgnoreCase(H5Utils.getString(jsonObject, (String) "enable"))) {
                enable = Boolean.valueOf(true);
            } else {
                enable = Boolean.valueOf(false);
            }
            float innerSite = H5Utils.parseFloat(H5Utils.getString(jsonObject, (String) "innerSite"));
            float outerSite = H5Utils.parseFloat(H5Utils.getString(jsonObject, (String) "outerSite"));
            innerSiteData = innerSite * 1024.0f * 1024.0f;
            outerSiteData = outerSite * 1024.0f * 1024.0f;
        }
        return enable.booleanValue();
    }

    private static void showDataFlowTip(H5Page h5Page) {
        H5WarningTipProvider dataExceededProvider = (H5WarningTipProvider) H5Utils.getProvider(H5WarningTipProvider.class.getName());
        H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (configProvider != null && dataExceededProvider != null) {
            if (configProvider.isAliDomains(h5Page.getUrl())) {
                dataExceededProvider.showWarningTip(h5Page, 1, String.valueOf(innerSiteData));
            } else {
                dataExceededProvider.showWarningTip(h5Page, 2, String.valueOf(outerSiteData));
            }
        }
    }
}
