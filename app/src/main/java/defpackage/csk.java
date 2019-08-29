package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.basemap.traffic.ajx.ModuleTrafficEvent;

@MultipleImpl(ema.class)
/* renamed from: csk reason: default package */
/* compiled from: AjxRegister */
public class csk implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleTrafficEvent.class);
    }
}
