package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.train.ajx.ModuleTrain;
import com.autonavi.minimap.ajx3.Ajx;

@MultipleImpl(ema.class)
/* renamed from: ejp reason: default package */
/* compiled from: AjxRegister */
public class ejp implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleTrain.class);
    }
}
