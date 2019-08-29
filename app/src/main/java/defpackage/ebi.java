package defpackage;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;

/* renamed from: ebi reason: default package */
/* compiled from: RoutePlanUtil */
public final class ebi {
    public static boolean a(final bid bid, GeoPoint geoPoint, GeoPoint geoPoint2, int i) {
        if (cfe.a(geoPoint, geoPoint2) < 100000.0f) {
            return true;
        }
        switch (i) {
            case 1:
                aho.a(new Runnable() {
                    public final void run() {
                        if (bid != null && bid.isAlive() && bid.getContext() != null) {
                            a aVar = new a(bid.getContext());
                            aVar.a((CharSequence) "当前位置距离终点太远\n建议选择其他出行方式").a(R.string.i_know, (a) new a() {
                                public final void onClick(AlertView alertView, int i) {
                                    if (bid != null) {
                                        bid.dismissViewLayer(alertView);
                                    }
                                }
                            });
                            aVar.a(true);
                            bid.showViewLayer(aVar.a());
                        }
                    }
                }, 0);
                break;
            case 2:
                ToastHelper.showLongToast("距离过长，建议采用其他出行方式");
                break;
        }
        return false;
    }
}
