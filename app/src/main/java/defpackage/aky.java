package defpackage;

import android.view.View;
import android.widget.FrameLayout.LayoutParams;

/* renamed from: aky reason: default package */
/* compiled from: IMapSurface */
public interface aky {
    void addView(View view, LayoutParams layoutParams);

    int getDeviceId();

    void removeView(View view);

    void setMapGestureListener(amj amj);

    void setMapSurfaceListener(amm amm);

    void setMapWidgetListener(amn amn);

    void setNaviMode(int i, boolean z);
}
