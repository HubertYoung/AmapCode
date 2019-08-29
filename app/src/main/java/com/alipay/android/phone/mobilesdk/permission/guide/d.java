package com.alipay.android.phone.mobilesdk.permission.guide;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import java.util.Locale;

/* compiled from: PermissionGuideLogger */
public final class d {
    private Behavor a = new Behavor();

    private d(String type) {
        this.a.setBehaviourPro("PermissionGuide");
        this.a.setSeedID(type);
    }

    private static d a(String type) {
        return new d(type);
    }

    private d a(String key, String value) {
        this.a.addExtParam(key, value);
        return this;
    }

    private d b(String value) {
        this.a.setParam1(value);
        return this;
    }

    private d c(String value) {
        this.a.setParam2(value);
        return this;
    }

    private void a() {
        this.a.setLoggerLevel(2);
        LoggerFactory.getBehavorLogger().event(null, this.a);
    }

    public static void a(String pgCode, String pgCategory, String mobilePgTemplateCode) {
        try {
            a("PERMISSION_GUIDE").b(pgCode).c(pgCategory).a("mobilePgTemplateCode", mobilePgTemplateCode).a();
            LoggerFactory.getTraceLogger().info("PermissionGuideLogger", String.format(Locale.US, "logPermissionGuide, seedId: %s, pgCode: %s, pgCategory: %s, mobilePgTemplateCode: %s", new Object[]{"PERMISSION_GUIDE", pgCode, pgCategory, mobilePgTemplateCode}));
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error("PermissionGuideLogger", "logPermissionGuide", tr);
        }
    }
}
