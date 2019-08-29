package com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic;

import com.alibaba.fastjson.JSON;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class BaseStatistics implements Statistics {
    public static final String BIZ_PRO = "APMultiMedia";

    public abstract String getCaseId();

    public abstract String getParam1();

    public abstract String getParam2();

    public abstract String getParam3();

    public abstract String getSeedId();

    public void submitRemote() {
        Behavor behavor = new Behavor();
        behavor.setAppID("APMultiMedia");
        behavor.setBehaviourPro("APMultiMedia");
        behavor.setUserCaseID(getCaseId());
        behavor.setSeedID(getSeedId());
        behavor.setLoggerLevel(2);
        behavor.setParam1(getParam1());
        behavor.setParam2(getParam2());
        behavor.setParam3(getParam3());
        Map ext = getExtParam();
        if (ext != null && !ext.isEmpty()) {
            for (Entry entry : ext.entrySet()) {
                behavor.addExtParam((String) entry.getKey(), (String) entry.getValue());
            }
        }
        LoggerFactory.getTraceLogger().debug("MMStatistics", JSON.toJSONString(behavor));
        LoggerFactory.getBehavorLogger().event("event", behavor);
    }

    public void submitRemoteAsync() {
        StatisticsManager.get().submit(this);
    }

    public Map<String, String> getExtParam() {
        Map ext = new HashMap();
        fillExtParams(ext);
        return ext;
    }

    /* access modifiers changed from: protected */
    public void fillExtParams(Map<String, String> ext) {
    }
}
