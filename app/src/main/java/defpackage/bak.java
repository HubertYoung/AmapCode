package defpackage;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.bundle.routecommute.common.bean.CommuteControlBean.b;
import com.autonavi.bundle.routecommute.drive.tips.RainbowBarView;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

/* renamed from: bak reason: default package */
/* compiled from: DriveCommuteTipsOverlayNormalItem */
public class bak {
    private static final String q = "bak";
    public View a;
    public View b;
    public ImageView c;
    public TextView d;
    public TextView e;
    public TextView f;
    public RainbowBarView g;
    public LinearLayout h;
    public LinearLayout i;
    public ImageView j;
    public View k;
    public a l;
    public bty m;
    public PointOverlay<PointOverlayItem<PointOverlay>> n;
    public boolean o = false;
    public b p;

    /* renamed from: bak$a */
    /* compiled from: DriveCommuteTipsOverlayNormalItem */
    public interface a {
        void a();

        void b();

        void c();
    }

    public bak(@NonNull bty bty, @NonNull PointOverlay<PointOverlayItem<PointOverlay>> pointOverlay) {
        this.m = bty;
        this.n = pointOverlay;
    }

    public final void a(boolean z) {
        if (z) {
            this.i.setVisibility(0);
            this.k.setVisibility(0);
            this.j.setVisibility(8);
            this.e.setMaxEms(6);
            return;
        }
        this.i.setVisibility(8);
        this.j.setVisibility(0);
        this.k.setVisibility(8);
        this.e.setMaxEms(10);
    }
}
