package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.searchhome.ajx.ModuleSearchHome;
import com.autonavi.minimap.ajx3.Ajx;

@MultipleImpl(ema.class)
/* renamed from: bbz reason: default package */
/* compiled from: SearchHomeAjxRegister */
public class bbz implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleSearchHome.class);
    }
}
