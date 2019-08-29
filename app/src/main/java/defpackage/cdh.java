package defpackage;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: cdh reason: default package */
/* compiled from: CompassPresenterForPage */
public final class cdh implements OnClickListener, cdf, cdj {
    public cdi a;
    public Context b;
    public OnClickListener c;
    private bty d;
    private cdz e;

    public cdh(Context context, bty bty, cdz cdz) {
        cdd.n().a((cdf) this);
        this.b = context;
        this.d = bty;
        this.e = cdz;
    }

    public final void a(cdi cdi) {
        this.a = cdi;
        this.a.setOnClickListener(this);
        this.a.getCompassWidget().attachMapView(this.d);
        this.a.getCompassWidget().setAngleListener(this);
    }

    public final void paintCompass() {
        if (!(this.a == null || this.a.getCompassWidget() == null || this.a.getCompassWidget() == null)) {
            float I = this.d.I();
            float J = this.d.J();
            if (this.a.getCompassWidget().getVisibility() == 0) {
                if (!(this.a == null || this.a.getCompassLayout() == null)) {
                    int i = 0;
                    boolean z = this.a.getCompassLayout() != null && (I > 0.0f || J > 0.0f);
                    RelativeLayout compassLayout = this.a.getCompassLayout();
                    if (!z) {
                        i = 8;
                    }
                    compassLayout.setVisibility(i);
                }
                this.a.getCompassWidget().paintCompass();
            } else if (I > 0.0f || J > 0.0f) {
                Integer b2 = b();
                if (b2 != null && b2.intValue() == 0) {
                    this.a.getCompassWidget().paintCompass();
                }
            }
        }
    }

    private Integer b() {
        if (this.a == null || this.a.getCompassLayout() == null || this.a.getCompassWidget() == null) {
            return null;
        }
        int i = (this.d.I() > 0.0f || this.d.J() > 0.0f) ? 0 : 8;
        this.a.getCompassLayout().setVisibility(i);
        this.a.getCompassWidget().setVisibility(i);
        if (this.a.getCompassWidget().getVisibility() != 0) {
            this.a.setCompassLayerTipVisibility(false);
        }
        return Integer.valueOf(i);
    }

    public final void a() {
        float I = this.d.I();
        float J = this.d.J();
        if (this.a != null) {
            StringBuilder sb = new StringBuilder("camera:");
            sb.append(J);
            sb.append(" angle:");
            sb.append(I);
            if (J <= 0.0f && I <= 0.0f) {
                this.a.setCompassLayerTipVisibility(false);
            } else if (!(this.a == null || this.a.getCompassLayout() == null || this.a.getCompassLayout().getResources().getConfiguration().orientation != 1)) {
                MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                if (mapSharePreference.getBooleanValue("IsShowCompassTip", true)) {
                    if (!(this.a == null || this.a.getCompassLayout() == null)) {
                        this.a.setCompassLayerTipVisibility(true);
                    }
                    mapSharePreference.putBooleanValue("IsShowCompassTip", false);
                }
            }
            b();
        }
    }

    public final void onClick(View view) {
        if (this.a != null && this.a.getCompassWidget() != null && this.a.getCompassLayerTip() != null) {
            if (view.getId() == this.a.getCompassWidget().getId()) {
                if (this.a != null) {
                    this.a.setCompassLayerTipVisibility(false);
                    if (this.d.J() > 0.0f) {
                        if (bin.a.o("201")) {
                            this.d.e(true);
                        } else {
                            this.d.B();
                        }
                    }
                    this.d.K();
                    if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                        this.e.e();
                    }
                }
                if (this.c != null) {
                    this.c.onClick(this.a.getCompassWidget());
                }
                brm brm = (brm) ank.a(brm.class);
                if (brm != null) {
                    brm.h();
                }
                return;
            }
            if (view.getId() == this.a.getCompassLayerTip().getId() && this.a != null) {
                this.a.setCompassLayerTipVisibility(false);
            }
        }
    }
}
