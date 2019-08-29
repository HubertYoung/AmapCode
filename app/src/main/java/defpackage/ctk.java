package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.bundle.activities.ajx.ModuleActivities;

@MultipleImpl(ema.class)
/* renamed from: ctk reason: default package */
/* compiled from: ActivitiesAjxRegister */
public class ctk implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleActivities.class);
    }
}
