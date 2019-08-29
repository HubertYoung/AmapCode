package com.alipay.mobile.nebula.util;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5WarningTipProvider;

public class H5WarningTipHelper {
    private static final String TAG = "H5WarningTipHelper";
    private H5WarningTipProvider dataExceededProvider;
    private boolean enableShowDataTip = false;
    private H5Page h5Page;
    private boolean hasShowDataTip = false;
    private float innerSiteData;
    private boolean isAliDomains = false;
    private float mobileDataUsage = 0.0f;
    private float outerSiteData;

    public void initDataExceededConfig(H5Page h5Page2) {
        this.isAliDomains = false;
        this.enableShowDataTip = false;
        this.hasShowDataTip = false;
        this.mobileDataUsage = 0.0f;
        this.h5Page = h5Page2;
        H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (configProvider != null && !H5Utils.isInWifi() && h5Page2 != null) {
            if ("YES".equals(H5Utils.getString(h5Page2.getParams(), (String) "fromLiveChannel"))) {
                H5Log.d(TAG, "showDataFlow disable by fromLiveChannel");
                return;
            }
            String value = configProvider.getConfigWithProcessCache("h5_dataFlowConfig");
            if (!TextUtils.isEmpty(value)) {
                JSONObject jsonObject = H5Utils.parseObject(value);
                if ("yes".equalsIgnoreCase(H5Utils.getString(jsonObject, (String) "enable"))) {
                    this.enableShowDataTip = true;
                    if (("yes".equals(h5Page2.getPageData().getIsTinyApp()) || !TextUtils.isEmpty(H5Utils.getString(h5Page2.getParams(), (String) "MINI-PROGRAM-WEB-VIEW-TAG"))) && BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Utils.getString(jsonObject, (String) "tinyAppEnable"))) {
                        this.enableShowDataTip = false;
                        return;
                    }
                    JSONArray whiteListArray = H5Utils.getJSONArray(jsonObject, "whiteList", null);
                    if (whiteListArray == null || whiteListArray.isEmpty() || TextUtils.isEmpty(h5Page2.getUrl()) || !H5DomainUtil.isSomeDomainInternal(H5DomainUtil.getNewDomainSuffix(h5Page2.getUrl()), whiteListArray.toString())) {
                        float innerSite = H5Utils.parseFloat(H5Utils.getString(jsonObject, (String) "innerSite"));
                        float outerSite = H5Utils.parseFloat(H5Utils.getString(jsonObject, (String) "outerSite"));
                        this.innerSiteData = innerSite * 1024.0f * 1024.0f;
                        this.outerSiteData = outerSite * 1024.0f * 1024.0f;
                        H5Log.d(TAG, "h5_dataFlowConfig enable：" + this.enableShowDataTip);
                        this.isAliDomains = configProvider.isAliDomains(h5Page2.getUrl());
                        this.dataExceededProvider = (H5WarningTipProvider) H5Utils.getProvider(H5WarningTipProvider.class.getName());
                        return;
                    }
                    this.enableShowDataTip = false;
                }
            }
        }
    }

    public void showWarningTip(long size) {
        if (this.enableShowDataTip && this.dataExceededProvider != null && !this.hasShowDataTip) {
            this.mobileDataUsage += (float) size;
            if (this.isAliDomains && this.innerSiteData != 0.0f && this.mobileDataUsage > this.innerSiteData) {
                this.dataExceededProvider.showWarningTip(this.h5Page, 1, String.valueOf(this.innerSiteData));
                this.hasShowDataTip = true;
            } else if (!this.isAliDomains && this.outerSiteData != 0.0f && this.mobileDataUsage > this.outerSiteData) {
                this.dataExceededProvider.showWarningTip(this.h5Page, 2, String.valueOf(this.outerSiteData));
                this.hasShowDataTip = true;
            }
        }
    }
}
