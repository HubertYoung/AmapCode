package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.environmentmap.ajx.ModuleSearchEnvironment;
import com.autonavi.minimap.ajx3.Ajx;

@MultipleImpl(ema.class)
/* renamed from: uo reason: default package */
/* compiled from: AjxRegister */
public class uo implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleSearchEnvironment.class);
    }
}
