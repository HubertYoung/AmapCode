package defpackage;

import com.amap.bundle.impressionreporter.ajx.ModuleImpressionReporter;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.minimap.ajx3.Ajx;

@MultipleImpl(ema.class)
/* renamed from: vv reason: default package */
/* compiled from: AjxRegister */
public class vv implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleImpressionReporter.class);
    }
}
