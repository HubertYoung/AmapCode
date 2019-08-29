package defpackage;

import android.app.Activity;
import android.view.View;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;

/* renamed from: ccf reason: default package */
/* compiled from: SearchDialogUtil */
public final class ccf {
    public static void a(bid bid, IViewLayer iViewLayer) {
        Activity activity = DoNotUseTool.getActivity();
        if (activity != null && !activity.isFinishing()) {
            View currentFocus = activity.getCurrentFocus();
            if (currentFocus != null) {
                currentFocus.clearFocus();
                KeyboardUtil.hideInputMethod(activity);
            }
        }
        bid.showViewLayer(iViewLayer);
    }
}
