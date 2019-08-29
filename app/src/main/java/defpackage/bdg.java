package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.bundle.smart.scenic.page.SerachSmartScenicSetMapPage;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.suspend.refactor.gps.GPSButton;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import java.lang.ref.WeakReference;

/* renamed from: bdg reason: default package */
/* compiled from: SearchSmartScenicMapManager */
public final class bdg {
    public ccv a;
    public ccy b;
    public Context c;
    public bty d;
    public GPSButton e;
    public ScaleView f;
    public defpackage.ceb.a g;
    public a h;
    public a i;
    private WeakReference<SerachSmartScenicSetMapPage> j;

    /* renamed from: bdg$a */
    /* compiled from: SearchSmartScenicMapManager */
    public static class a {
        public boolean a;
        public float b;
        public GeoPoint c;
        public float d;
        public float e;
        public int f;

        private a() {
            this.a = false;
            this.b = 0.0f;
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 0;
        }

        public /* synthetic */ a(byte b2) {
            this();
        }
    }

    public bdg(@NonNull SerachSmartScenicSetMapPage serachSmartScenicSetMapPage) {
        this.j = new WeakReference<>(serachSmartScenicSetMapPage);
        this.a = new ccv(serachSmartScenicSetMapPage.getContext());
        this.b = serachSmartScenicSetMapPage.getSuspendWidgetHelper();
        this.c = serachSmartScenicSetMapPage.getContext();
        this.d = serachSmartScenicSetMapPage.getMapView();
    }

    public final void a(int i2) {
        int a2 = agn.a(this.c, (float) ((i2 / 2) + (i2 > 0 ? 0 : 4)));
        LayoutParams layoutParams = null;
        LayoutParams layoutParams2 = this.e != null ? (LayoutParams) this.e.getLayoutParams() : null;
        if (this.f != null) {
            layoutParams = (LayoutParams) this.f.getLayoutParams();
        }
        if (!(layoutParams2 == null || a2 == layoutParams2.bottomMargin)) {
            layoutParams2.bottomMargin = a2;
            this.e.requestLayout();
        }
        if (!(layoutParams == null || a2 == layoutParams.bottomMargin)) {
            layoutParams.bottomMargin = a2;
            this.f.requestLayout();
        }
        if (this.e != null) {
            this.e.setVisibility(0);
        }
        if (this.f != null) {
            this.f.setVisibility(0);
        }
    }

    public final void a() {
        if (this.j != null && this.j.get() != null) {
            cde suspendManager = ((SerachSmartScenicSetMapPage) this.j.get()).getSuspendManager();
            if (suspendManager != null) {
                cdz d2 = suspendManager.d();
                if (d2 != null) {
                    d2.f();
                }
            }
        }
    }
}
