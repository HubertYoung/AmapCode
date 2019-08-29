package defpackage;

import android.graphics.Rect;
import com.google.zxing.ResultPoint;

/* renamed from: dty reason: default package */
/* compiled from: IQRCodeFinderView */
public interface dty {

    /* renamed from: dty$a */
    /* compiled from: IQRCodeFinderView */
    public interface a {
        void a(Rect rect);
    }

    void addPossibleResultPoint(ResultPoint resultPoint);

    void cleanUp();

    void drawViewfinder();

    void setCameraManager(dtt dtt);

    void setStateChangedListner(a aVar);
}
