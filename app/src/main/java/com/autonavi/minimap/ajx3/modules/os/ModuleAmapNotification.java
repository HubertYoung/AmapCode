package com.autonavi.minimap.ajx3.modules.os;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;

@AjxModule("ajx.notification")
public class ModuleAmapNotification extends AbstractModule {
    public static final String MODULE_NAME = "ajx.notification";

    public ModuleAmapNotification(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("send")
    public void sendDefaultLocalNotification(String str, String str2, String str3, String str4, int i) {
        fhb fhb = (fhb) a.a.a(fhb.class);
        if (fhb != null) {
            fhb.a(str, str2, str3, str4, i);
        }
    }

    @AjxMethod("cancelAll")
    public void cancelAllLocalNotifications() {
        fhb fhb = (fhb) a.a.a(fhb.class);
        if (fhb != null) {
            fhb.b();
        }
    }
}
