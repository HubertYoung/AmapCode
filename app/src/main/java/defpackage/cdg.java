package defpackage;

import android.content.Context;
import com.autonavi.map.suspend.refactor.compass.CompassView;
import com.autonavi.minimap.R;

/* renamed from: cdg reason: default package */
/* compiled from: CompassManager */
public final class cdg {
    private cdc a;

    public cdg(cdc cdc) {
        this.a = cdc;
    }

    public final cdh a(boolean z, Context context) {
        CompassView compassView = new CompassView(context);
        if (!z) {
            compassView.setCompassWidgetIcon(R.drawable.suspend_compass);
        }
        compassView.getCompassWidget().setVisibility(8);
        cdh cdh = new cdh(this.a.a(), this.a.b().getMapView(), this.a.d());
        cdh.a(compassView);
        compassView.setContentDescription(null);
        return cdh;
    }
}
