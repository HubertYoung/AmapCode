package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.scenicarea.ajx.ModuleSearchScenic;
import com.autonavi.bundle.smart.scenic.ajx.ModuleSmartSearchScenicSet;
import com.autonavi.minimap.ajx3.Ajx;

@MultipleImpl(ema.class)
/* renamed from: baz reason: default package */
/* compiled from: AjxRegister */
public class baz implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleSearchScenic.class);
        Ajx.getInstance().registerModule(ModuleSmartSearchScenicSet.class);
    }
}
