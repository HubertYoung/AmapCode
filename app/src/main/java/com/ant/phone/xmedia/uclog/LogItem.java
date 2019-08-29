package com.ant.phone.xmedia.uclog;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import java.util.HashMap;
import java.util.Map;

public class LogItem {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private Map<String, String> g;
    private Integer h = null;
    private String i = null;

    public LogItem(String caseId, String behaviorId, String seedId, String extParam1, String extParam2, String extParam3) {
        this.a = caseId;
        this.b = behaviorId;
        this.c = seedId;
        this.d = extParam1;
        this.e = extParam2;
        this.f = extParam3;
    }

    public final void a(String key, String value) {
        if (this.g == null) {
            this.g = new HashMap();
        }
        this.g.put(key, value);
    }

    public final void a(LogItem logItem) {
        Behavor behavor = new Behavor();
        behavor.setAppID("xMedia");
        behavor.setUserCaseID(logItem.a);
        behavor.setSeedID(logItem.c);
        behavor.setParam1(logItem.d);
        behavor.setParam2(logItem.e);
        behavor.setParam3(logItem.f);
        behavor.setBehaviourPro("APMultiMedia");
        a(behavor);
        if (logItem.g != null) {
            for (String key : logItem.g.keySet()) {
                behavor.addExtParam(key, logItem.g.get(key));
            }
        }
        if ("clicked".equals(logItem.b)) {
            LoggerFactory.getBehavorLogger().click(behavor);
        } else {
            LoggerFactory.getBehavorLogger().event("", behavor);
        }
    }

    private void a(Behavor behavor) {
        if (!TextUtils.isEmpty(this.i)) {
            behavor.setBehaviourPro(this.i);
        }
        if (this.h != null) {
            behavor.setLoggerLevel(this.h.intValue());
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("caseId:" + this.a + ",");
        sb.append("behaviorID:" + this.b + ",");
        sb.append("seedId:" + this.c + ",");
        sb.append("extParam1:" + this.d + ",");
        sb.append("extParam2:" + this.e + ",");
        sb.append("extParam3:" + this.f + ",");
        sb.append("extParams:" + this.g);
        return sb.toString();
    }
}
