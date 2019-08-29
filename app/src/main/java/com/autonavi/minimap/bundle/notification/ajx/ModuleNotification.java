package com.autonavi.minimap.bundle.notification.ajx;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;

@AjxModule("localNotification")
public class ModuleNotification extends AbstractModule {
    public ModuleNotification(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("sendDefaultLocalNotification")
    public void sendDefaultLocalNotification(String str, String str2, String str3, String str4, int i) {
        fhb fhb = (fhb) a.a.a(fhb.class);
        if (fhb != null) {
            fhb.a(str, str2, str3, str4, i);
        }
    }

    @AjxMethod("cancelAllLocalNotifications")
    public void cancelAllLocalNotifications() {
        fhb fhb = (fhb) a.a.a(fhb.class);
        if (fhb != null) {
            fhb.b();
        }
    }
}
