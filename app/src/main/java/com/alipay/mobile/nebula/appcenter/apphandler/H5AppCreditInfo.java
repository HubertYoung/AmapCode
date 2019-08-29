package com.alipay.mobile.nebula.appcenter.apphandler;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class H5AppCreditInfo {
    private Map<String, List<String>> creditMap = new HashMap();
    private Map<Integer, List<String>> strategyMap = new HashMap();

    public Map<Integer, List<String>> getStrategyMap() {
        return this.strategyMap;
    }

    public Map<String, List<String>> getCreditMap() {
        return this.creditMap;
    }

    public void addCreditAppInfo(String credit, String appId) {
        if (!TextUtils.isEmpty(credit) && !TextUtils.isEmpty(appId)) {
            if (!this.creditMap.containsKey(credit) || this.creditMap.get(credit) == null) {
                List appList = new ArrayList();
                appList.add(appId);
                this.creditMap.put(credit, appList);
                return;
            }
            this.creditMap.get(credit).add(appId);
        }
    }

    public void addStrategyInfo(int strategy, List<String> appList) {
        if (appList != null && appList.size() != 0) {
            if (!this.strategyMap.containsKey(Integer.valueOf(strategy)) || this.strategyMap.get(Integer.valueOf(strategy)) == null) {
                List list = new ArrayList();
                list.addAll(appList);
                this.strategyMap.put(Integer.valueOf(strategy), list);
                return;
            }
            this.strategyMap.get(Integer.valueOf(strategy)).addAll(appList);
        }
    }

    public String toString() {
        return "H5AppCreditInfo{strategyMap=" + this.strategyMap + ", creditMap=" + this.creditMap + '}';
    }
}
