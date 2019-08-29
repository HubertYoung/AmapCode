package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.bundle.notification.ajx.ModuleNotification;

@MultipleImpl(ema.class)
/* renamed from: dbn reason: default package */
/* compiled from: NotificationAjxRegister */
public class dbn implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleNotification.class);
    }
}
