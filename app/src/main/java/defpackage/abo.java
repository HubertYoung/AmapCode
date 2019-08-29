package defpackage;

import com.amap.bundle.pay.ajx.ModuleAlipayFreepay;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.minimap.ajx3.Ajx;

@MultipleImpl(ema.class)
/* renamed from: abo reason: default package */
/* compiled from: AjxRegister */
public class abo implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleAlipayFreepay.class);
    }
}
