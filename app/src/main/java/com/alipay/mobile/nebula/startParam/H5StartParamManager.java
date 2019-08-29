package com.alipay.mobile.nebula.startParam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5StartParamManager {
    public static final String appConfig = "appConfig.json";
    public static final String index = "index";
    private static H5StartParamManager instance = null;
    public static final String launchParamsTag = "launchParamsTag";
    private Map<String, String> homePageMap = new ConcurrentHashMap();
    private Map<String, String> prerenderPageInfoMap = new ConcurrentHashMap();
    private Map<String, List<H5StartParamInfo>> startParamInfoMap = new ConcurrentHashMap();
    private Map<String, JSONObject> windowTagMap = new ConcurrentHashMap();

    public static synchronized H5StartParamManager getInstance() {
        H5StartParamManager h5StartParamManager;
        synchronized (H5StartParamManager.class) {
            try {
                if (instance == null) {
                    instance = new H5StartParamManager();
                }
                h5StartParamManager = instance;
            }
        }
        return h5StartParamManager;
    }

    private H5StartParamManager() {
    }

    public void put(String appId, byte[] bytes) {
        List startParamInfoList = new ArrayList();
        StringBuilder prerenderPageInfo = new StringBuilder();
        StringBuilder homePageContainer = new StringBuilder();
        JSONObject windowContainer = new JSONObject();
        H5StartParamUtil.byteToInfo(appId, bytes, startParamInfoList, prerenderPageInfo, homePageContainer, windowContainer);
        if (!startParamInfoList.isEmpty()) {
            this.startParamInfoMap.put(appId, startParamInfoList);
        }
        String homePageStr = homePageContainer.toString();
        if (!TextUtils.isEmpty(homePageStr)) {
            this.homePageMap.put(appId, homePageStr);
        }
        String prerenderPageInfoStr = prerenderPageInfo.toString();
        if (!TextUtils.isEmpty(prerenderPageInfoStr)) {
            this.prerenderPageInfoMap.put(appId, prerenderPageInfoStr);
        }
        this.windowTagMap.put(appId, windowContainer);
    }

    public void clear(String appId) {
        if (this.startParamInfoMap.get(appId) != null) {
            this.startParamInfoMap.remove(appId);
        }
        if (this.prerenderPageInfoMap.get(appId) != null) {
            this.prerenderPageInfoMap.remove(appId);
        }
        if (this.homePageMap.get(appId) != null) {
            this.homePageMap.remove(appId);
        }
    }

    @Nullable
    public Bundle getH5StartParam(String appId, String key) {
        List<H5StartParamInfo> list = this.startParamInfoMap.get(appId);
        if (list != null && !list.isEmpty()) {
            for (H5StartParamInfo h5StartParamInfo : list) {
                if (TextUtils.equals(h5StartParamInfo.tag, key)) {
                    return H5Utils.toBundle(h5StartParamInfo.param);
                }
            }
        }
        return null;
    }

    @Nullable
    public String getHomePage(String appId) {
        return this.homePageMap != null ? this.homePageMap.get(appId) : "";
    }

    public String getH5PreRenderPage(String appId) {
        return this.prerenderPageInfoMap != null ? this.prerenderPageInfoMap.get(appId) : "";
    }

    public JSONObject getWindowTag(String appId) {
        if (this.windowTagMap != null) {
            return this.windowTagMap.get(appId);
        }
        return null;
    }

    public List<H5StartParamInfo> getH5StartParamTag(String appId) {
        return this.startParamInfoMap.get(appId);
    }

    public void setH5StartParamTag(String appId, List<H5StartParamInfo> startParamInfos) {
        if (!TextUtils.isEmpty(appId) && startParamInfos != null) {
            this.startParamInfoMap.put(appId, startParamInfos);
        }
    }
}
