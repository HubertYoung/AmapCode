package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.onekeycheck.ajx.ModuleDetection;
import com.autonavi.minimap.ajx3.Ajx;

@MultipleImpl(ema.class)
/* renamed from: dsv reason: default package */
/* compiled from: OneKeyAjxRegister */
public class dsv implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleDetection.class);
    }
}
