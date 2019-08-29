package defpackage;

import com.autonavi.minimap.basemap.route.external.zxingwrapper.view.ViewfinderView;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

/* renamed from: cqq reason: default package */
/* compiled from: ViewfinderResultPointCallback */
public final class cqq implements ResultPointCallback {
    private final ViewfinderView a;

    public cqq(ViewfinderView viewfinderView) {
        this.a = viewfinderView;
    }

    public final void foundPossibleResultPoint(ResultPoint resultPoint) {
        this.a.addPossibleResultPoint(resultPoint);
    }
}
