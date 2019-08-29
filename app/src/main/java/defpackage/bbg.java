package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.searcharound.ajx.ModuleSearchAround;
import com.autonavi.minimap.ajx3.Ajx;

@MultipleImpl(ema.class)
/* renamed from: bbg reason: default package */
/* compiled from: SearchAroundAjxRegister */
public class bbg implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleSearchAround.class);
    }
}
