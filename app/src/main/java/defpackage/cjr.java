package defpackage;

import android.view.View;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseDialog;
import java.lang.ref.WeakReference;

/* renamed from: cjr reason: default package */
/* compiled from: AGroupViewLayerUtils */
public final class cjr {
    public static WeakReference<IViewLayer> a;
    public static WeakReference<AbstractBaseDialog> b;

    public static boolean a() {
        if (b == null) {
            return false;
        }
        AbstractBaseDialog abstractBaseDialog = (AbstractBaseDialog) b.get();
        if (abstractBaseDialog == null || !abstractBaseDialog.isShowing()) {
            return false;
        }
        return true;
    }

    public static boolean b() {
        if (a == null) {
            return false;
        }
        IViewLayer iViewLayer = (IViewLayer) a.get();
        if (iViewLayer == null) {
            return false;
        }
        View view = iViewLayer.getView();
        if (view == null || !view.isShown()) {
            return false;
        }
        return true;
    }
}
