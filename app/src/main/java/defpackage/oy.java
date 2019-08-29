package defpackage;

import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage;
import com.amap.bundle.drive.result.view.RouteCarLongScenePanel;
import com.amap.bundle.drivecommon.model.LongDistnceSceneData.c;
import com.amap.bundle.drivecommon.widget.FreeRideExpandableIconView;
import java.util.List;

/* renamed from: oy reason: default package */
/* compiled from: AjxRouteCarResultMapSuspendHelper */
public final class oy extends sx {
    public FreeRideExpandableIconView a;
    public AjxRouteCarResultPage b;
    private RouteCarLongScenePanel h;

    public final boolean adjustWidget(int i, ViewGroup viewGroup, int i2) {
        boolean z;
        if (!(i2 == 0 || i != 5 || this.h == null)) {
            if (i2 > 0 && this.h.isWeatherShown()) {
                this.h.setWeatherBtVisibility(false);
                return true;
            } else if (i2 < 0) {
                AjxRouteCarResultPage ajxRouteCarResultPage = this.b;
                if (ajxRouteCarResultPage.g != null && !ajxRouteCarResultPage.h()) {
                    ph phVar = ajxRouteCarResultPage.g;
                    if (phVar != null && phVar.b > 0 && ajxRouteCarResultPage.g.a() != null && ajxRouteCarResultPage.g()) {
                        List<c> a2 = phVar.a().q.a();
                        if (a2 != null && a2.size() > 1) {
                            int i3 = 0;
                            int i4 = 0;
                            while (true) {
                                if (i3 < a2.size()) {
                                    if (i3 != 0 && i4 != a2.get(i3).a) {
                                        z = true;
                                        break;
                                    }
                                    i4 = a2.get(i3).a;
                                    i3++;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
                z = false;
                if (z && !this.h.isWeatherShown()) {
                    int height = ags.a(this.c).height();
                    if (!(height > 0 && height <= 800)) {
                        this.h.setWeatherBtVisibility(true);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final LayoutParams a() {
        LayoutParams a2 = super.a();
        if (a2 != null) {
            a2.bottomMargin = agn.a(this.b.getContext(), 3.0f);
        }
        return a2;
    }

    public static /* synthetic */ void a(oy oyVar, boolean z) {
        LayoutParams layoutParams = (LayoutParams) oyVar.a.getLayoutParams();
        int a2 = agn.a(oyVar.c, -15.4f);
        if (tt.b(oyVar.c) <= 1280) {
            a2 = agn.a(oyVar.c, -13.0f);
        }
        if (!z) {
            a2 = 0;
        }
        layoutParams.setMargins(0, 0, 0, a2);
        oyVar.a.setLayoutParams(layoutParams);
    }
}
