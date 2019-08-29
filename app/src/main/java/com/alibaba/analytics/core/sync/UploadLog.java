package com.alibaba.analytics.core.sync;

import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.config.UTBaseConfMgr;
import com.alibaba.analytics.core.network.NetworkUtil;
import com.alibaba.analytics.utils.Logger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class UploadLog {
    protected NetworkStatus mAllowedNetworkStatus = NetworkStatus.ALL;
    protected IUploadExcuted mIUploadExcuted = null;
    protected int mMaxUploadTimes = 3;

    public enum NetworkStatus {
        ALL,
        WIFI,
        TWO_GENERATION,
        THRID_GENERATION,
        FOUR_GENERATION,
        NONE
    }

    public void setUploadTimes(int i) {
        this.mMaxUploadTimes = i;
    }

    public void setAllowedNetworkStatus(NetworkStatus networkStatus) {
        this.mAllowedNetworkStatus = networkStatus;
    }

    public void setIUploadExcuted(IUploadExcuted iUploadExcuted) {
        this.mIUploadExcuted = iUploadExcuted;
    }

    /* access modifiers changed from: protected */
    public NetworkStatus getNetworkStatus() {
        String networkType = NetworkUtil.getNetworkType();
        if ("2G".equalsIgnoreCase(networkType)) {
            return NetworkStatus.TWO_GENERATION;
        }
        if ("3G".equalsIgnoreCase(networkType)) {
            return NetworkStatus.THRID_GENERATION;
        }
        if ("4G".equalsIgnoreCase(networkType)) {
            return NetworkStatus.FOUR_GENERATION;
        }
        if ("Wi-Fi".equalsIgnoreCase(networkType)) {
            return NetworkStatus.WIFI;
        }
        return NetworkStatus.NONE;
    }

    public void parserConfig(String str) {
        Object obj;
        if (!TextUtils.isEmpty(str)) {
            try {
                UTBaseConfMgr confMgr = Variables.getInstance().getConfMgr();
                if (confMgr != null) {
                    JSONObject jSONObject = new JSONObject(str).getJSONObject("config");
                    if (jSONObject != null) {
                        Iterator<String> keys = jSONObject.keys();
                        if (keys == null || !keys.hasNext()) {
                            Logger.i((String) null, "No Config Update");
                        } else {
                            while (keys.hasNext()) {
                                String next = keys.next();
                                if (!TextUtils.isEmpty(next)) {
                                    HashMap hashMap = new HashMap();
                                    JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                                    if (jSONObject2 != null) {
                                        Iterator<String> keys2 = jSONObject2.keys();
                                        if (keys2 != null) {
                                            while (keys2.hasNext()) {
                                                String next2 = keys2.next();
                                                if (jSONObject2.get(next2) == null) {
                                                    obj = null;
                                                } else {
                                                    StringBuilder sb = new StringBuilder();
                                                    sb.append(jSONObject2.get(next2));
                                                    obj = sb.toString();
                                                }
                                                hashMap.put(next2, obj);
                                            }
                                        }
                                    }
                                    Logger.d((String) "Config Update", "namespace", next, "configs", hashMap);
                                    confMgr.updateAndDispatch(next, (Map<String, String>) hashMap);
                                }
                            }
                            UTBaseConfMgr.sendConfigTimeStamp("1");
                        }
                    }
                }
            } catch (Throwable th) {
                Logger.e("", th, new Object[0]);
            }
        } else {
            Logger.w((String) null, "Config Is Empty");
        }
    }
}
