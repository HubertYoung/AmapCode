package defpackage;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

/* renamed from: dud reason: default package */
/* compiled from: ViewfinderResultPointCallback */
public final class dud implements ResultPointCallback {
    private final dty a;

    public dud(dty dty) {
        this.a = dty;
    }

    public final void foundPossibleResultPoint(ResultPoint resultPoint) {
        this.a.addPossibleResultPoint(resultPoint);
    }
}
