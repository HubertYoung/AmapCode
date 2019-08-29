package com.alipay.mobile.common.logging.impl;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LogDAUTracker;
import com.alipay.mobile.common.logging.api.LogEvent;
import com.alipay.mobile.common.logging.api.LogEvent.Level;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.abtest.AbtestInfoGetter;
import com.alipay.mobile.common.logging.api.abtest.AbtestInfoGetterV2;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.behavor.BehavorID;
import com.alipay.mobile.common.logging.api.behavor.BehavorLogger;
import com.alipay.mobile.common.logging.render.BehavorRender;
import com.alipay.mobile.common.logging.render.PendingRender;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BehavorloggerImpl implements BehavorLogger {
    private static HashMap<String, Boolean> c;
    private LogContext a;
    private BehavorRender b;

    static {
        HashMap<String, Boolean> hashMap = new HashMap<>();
        c = hashMap;
        hashMap.put("a14.b62.c1226.d1937", Boolean.valueOf(true));
        c.put("a14.b62.c1226.d2202", Boolean.valueOf(true));
        c.put("a14.b62.c1226.d3164", Boolean.valueOf(true));
        c.put("a14.b62.c1226.d3165", Boolean.valueOf(true));
    }

    public BehavorloggerImpl(LogContext logContext) {
        this.a = logContext;
        this.b = new BehavorRender(logContext);
    }

    public void click(Behavor behavor) {
        a(behavor);
        String bizType = behavor.getBehaviourPro();
        if (a(bizType)) {
            bizType = LogCategory.CATEGORY_USERBEHAVOR;
        }
        LogDAUTracker logDAUTracker = LoggerFactory.getLogContext().getLogDAUTracker();
        String spm = behavor.getSeedID();
        if (spm != null && c.containsKey(spm) && logDAUTracker != null && !logDAUTracker.isUploadedToday(spm)) {
            behavor.setRenderBizType(bizType);
            behavor.addExtParam("kDAUTag", "Y");
            bizType = LogDAUTracker.BIZ_TYPE;
            logDAUTracker.updateSpmUploadState(spm);
        }
        behavor.setBehaviourPro(bizType);
        this.a.appendLogEvent(new LogEvent(bizType, "BehavorLogger", new Level(behavor.getLoggerLevel()), null, PendingRender.a(this.b, "clicked", behavor)));
    }

    public void openPage(Behavor behavor) {
        a(behavor);
        String bizType = behavor.getBehaviourPro();
        if (a(bizType)) {
            bizType = LogCategory.CATEGORY_USERBEHAVOR;
        }
        behavor.setBehaviourPro(bizType);
        this.a.appendLogEvent(new LogEvent(bizType, "BehavorLogger", new Level(behavor.getLoggerLevel()), null, PendingRender.a(this.b, BehavorID.OPENPAGE, behavor)));
    }

    public void longClick(Behavor behavor) {
        event(BehavorID.LONGCLICK, behavor);
    }

    public void submit(Behavor behavor) {
        event(BehavorID.SUBMITE, behavor);
    }

    public void slide(Behavor behavor) {
        event(BehavorID.SLIDE, behavor);
    }

    public void autoOpenPage(Behavor behavor) {
        a(behavor);
        this.a.appendLogEvent(new LogEvent(LogCategory.CATEGORY_AUTOUSERBEHAVOR, "BehavorLogger", new Level(behavor.getLoggerLevel()), null, PendingRender.a(this.b, BehavorID.AUTOOPENPAGE, behavor)));
        if (this.a.getBehavorLogListener() != null) {
            this.a.getBehavorLogListener().onAutoOpenPage(behavor);
        }
    }

    public void autoClick(Behavor behavor) {
        a(behavor);
        this.a.appendLogEvent(new LogEvent(LogCategory.CATEGORY_AUTOUSERBEHAVOR, "BehavorLogger", new Level(behavor.getLoggerLevel()), null, PendingRender.a(this.b, BehavorID.AUTOCLICK, behavor)));
        if (this.a.getBehavorLogListener() != null) {
            this.a.getBehavorLogListener().onAutoClick(behavor);
        }
    }

    public void autoEvent(Behavor behavor) {
        behavor.setRefViewID("");
        behavor.setViewID("");
        behavor.setTrackId("");
        behavor.setTrackToken("");
        behavor.setTrackDesc("");
        this.a.appendLogEvent(new LogEvent(LogCategory.CATEGORY_AUTOUSERBEHAVOR, "BehavorLogger", new Level(behavor.getLoggerLevel()), null, PendingRender.a(this.b, BehavorID.AUTOEVENT, behavor)));
    }

    public void event(String behavorID, Behavor behavor) {
        String bizType = behavor.getBehaviourPro();
        if (BehavorID.LONGCLICK.equals(behavorID) || BehavorID.SUBMITE.equals(behavorID) || "clicked".equals(behavorID) || BehavorID.EXPOSURE.equals(behavorID) || BehavorID.SLIDE.equals(behavorID) || "pageMonitor".equals(behavorID)) {
            a(behavor);
        }
        if (a(bizType)) {
            bizType = LogCategory.CATEGORY_USERBEHAVOR;
        }
        behavor.setBehaviourPro(bizType);
        this.a.appendLogEvent(new LogEvent(bizType, "BehavorLogger", new Level(behavor.getLoggerLevel()), null, PendingRender.a(this.b, behavorID, behavor)));
    }

    private static boolean a(String bizType) {
        if (TextUtils.isEmpty(bizType) || bizType.length() == 1) {
            return true;
        }
        return false;
    }

    private void a(Behavor behavor) {
        if (behavor != null && TextUtils.isEmpty(behavor.getAbTestInfo()) && this.a != null) {
            AbtestInfoGetter getter = this.a.getAbtestInfoGetter();
            if (getter != null) {
                String spm = behavor.getSeedID();
                if (!TextUtils.isEmpty(spm) && spm.contains(".")) {
                    behavor.setAbTestInfo(getter.getLogForSpmID(spm));
                    if (getter instanceof AbtestInfoGetterV2) {
                        Map extTestInfo = ((AbtestInfoGetterV2) getter).getExtInfoForSpmID(spm);
                        if (extTestInfo != null) {
                            for (Entry entry : extTestInfo.entrySet()) {
                                behavor.addExtParam((String) entry.getKey(), (String) entry.getValue());
                            }
                        }
                    }
                }
            }
        }
    }
}
