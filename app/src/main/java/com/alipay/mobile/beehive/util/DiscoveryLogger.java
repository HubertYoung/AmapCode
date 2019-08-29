package com.alipay.mobile.beehive.util;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.behavor.BehavorID;

public class DiscoveryLogger extends Logger {
    public static final String DISCOVERY = "DISCOVERY";

    public static void click(Behavor behavor) {
        event("clicked", behavor);
    }

    public static void openPage(Behavor behavor) {
        event(BehavorID.OPENPAGE, behavor);
    }

    public static void longClick(Behavor behavor) {
        event(BehavorID.LONGCLICK, behavor);
    }

    public static void submit(Behavor behavor) {
        event(BehavorID.SUBMITE, behavor);
    }

    public static void slide(Behavor behavor) {
        event(BehavorID.SLIDE, behavor);
    }

    public static void event(String behavorType, Behavor behavor) {
        if (behavor != null) {
            behavor.setUserCaseID(DISCOVERY);
            LoggerFactory.getBehavorLogger().event(behavorType, behavor);
            return;
        }
        e(DISCOVERY, "埋点日志数据不能为null!!!");
    }
}
