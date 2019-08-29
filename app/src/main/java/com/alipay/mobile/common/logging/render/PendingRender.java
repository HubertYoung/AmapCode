package com.alipay.mobile.common.logging.render;

import com.alipay.mobile.common.logging.api.IRender;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.common.logging.strategy.LogLengthConfig;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PendingRender implements IRender {
    public static final List<String> a = new ArrayList<String>() {
        {
            add("keybiztrace");
            add(LogCategory.CATEGORY_LOGMONITOR);
            add(LogCategory.CATEGORY_EXCEPTION);
            add("crash");
            add(LogCategory.CATEGORY_HIGHAVAIL);
            add(LogCategory.CATEGORY_APM);
        }
    };
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    private BaseRender i;
    private Object[] j;

    public static PendingRender a(PerformanceRender performanceRender, Object param1, Object param2, Object param3) {
        if (performanceRender == null) {
            return null;
        }
        return new PendingRender(performanceRender, new Object[]{param1, param2, param3});
    }

    public static PendingRender a(BehavorRender behavorRender, Object param1, Object param2) {
        if (behavorRender == null) {
            return null;
        }
        return new PendingRender(behavorRender, new Object[]{param1, param2});
    }

    private PendingRender(BaseRender render, Object[] params) {
        this.i = render;
        this.j = params;
        try {
            this.b = LoggingUtil.getNowTime();
            this.c = LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_REFVIEWID);
            this.d = LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_VIEWID);
            this.e = LoggerFactory.getLogContext().getContextParam(LogContext.LOCAL_STORAGE_ACTIONID);
            this.f = LoggerFactory.getLogContext().getContextParam(LogContext.LOCAL_STORAGE_ACTIONTOKEN);
            this.g = LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_APPID);
            this.h = LoggerFactory.getLogContext().getContextParam(LogContext.LOCAL_STORAGE_REFER);
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "PendingRender", tr);
        }
    }

    public String doRender() {
        String bizType;
        String result;
        if (this.i == null) {
            return null;
        }
        try {
            if (this.i instanceof BehavorRender) {
                String result2 = ((BehavorRender) this.i).a((String) this.j[0], (Behavor) this.j[1], this);
                if (result2 == null) {
                    return result2;
                }
                int length = result2.getBytes().length;
                String bizType2 = ((Behavor) this.j[1]).getBehaviourPro();
                if (length <= 16384 || a.contains(bizType2) || !LogLengthConfig.a().b()) {
                    return result2;
                }
                Behavor behavor = new Behavor();
                behavor.setSeedID("LogLength");
                behavor.setParam1(bizType2);
                behavor.setParam2(((Behavor) this.j[1]).getSeedID());
                behavor.setParam3(String.valueOf(length));
                behavor.setBehaviourPro(LogCategory.CATEGORY_LOGMONITOR);
                return ((BehavorRender) this.i).a("event", behavor, this);
            }
            if (this.i instanceof PerformanceRender) {
                if (this.j[0] instanceof PerformanceID) {
                    bizType = ((PerformanceID) this.j[0]).getDes();
                    result = ((PerformanceRender) this.i).a((PerformanceID) this.j[0], (Performance) this.j[1], (Map) this.j[2], this);
                } else {
                    bizType = (String) this.j[0];
                    result = ((PerformanceRender) this.i).a((String) this.j[0], (Performance) this.j[1], (Map) this.j[2], this);
                }
                if (result == null) {
                    return result;
                }
                int length2 = result.getBytes().length;
                if (length2 <= 16384 || a.contains(bizType) || !LogLengthConfig.a().b()) {
                    return result;
                }
                Behavor behavor2 = new Behavor();
                behavor2.setSeedID("LogLength");
                behavor2.setParam1(bizType);
                behavor2.setParam2(((Performance) this.j[1]).getSubType());
                behavor2.setParam3(String.valueOf(length2));
                behavor2.setBehaviourPro(LogCategory.CATEGORY_LOGMONITOR);
                return new BehavorRender(LoggerFactory.getLogContext()).a("event", behavor2, this);
            }
            return null;
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "PendingRender", tr);
        }
    }
}
