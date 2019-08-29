package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.bundle.amaphome.ajx.ModuleStatusBar;

@MultipleImpl(ema.class)
/* renamed from: cuo reason: default package */
/* compiled from: StatusBarAjxRegister */
public class cuo implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleStatusBar.class);
    }
}
