package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.drive.search.module.ModuleSearchSingleResult;
import com.autonavi.minimap.drive.view.BarPicker;

@MultipleImpl(ema.class)
/* renamed from: diu reason: default package */
/* compiled from: AjxRegister */
public class diu implements ema {
    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleSearchSingleResult.class);
    }

    public void onWidgetRegister() {
        Ajx.getInstance().registerView("barpicker", BarPicker.class);
    }
}
