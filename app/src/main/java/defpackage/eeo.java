package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.rideresult.ajx.ModuleRide;
import com.autonavi.minimap.ajx3.Ajx;

@MultipleImpl(ema.class)
/* renamed from: eeo reason: default package */
/* compiled from: AjxRegister */
public class eeo implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleRide.class);
    }
}
