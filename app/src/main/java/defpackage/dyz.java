package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.busnavi.ajx.ModuleBus;
import com.autonavi.minimap.ajx3.Ajx;

@MultipleImpl(ema.class)
/* renamed from: dyz reason: default package */
/* compiled from: BusAjxRegister */
public class dyz implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleBus.class);
    }
}
