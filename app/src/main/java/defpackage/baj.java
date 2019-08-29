package defpackage;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.bundle.routecommute.drive.tips.RainbowBarView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

/* renamed from: baj reason: default package */
/* compiled from: DriveCommuteTipsOverlayCPointItem */
public class baj {
    private static final String l = "baj";
    public View a = LayoutInflater.from(this.j.d()).inflate(R.layout.drive_commute_cpoint_tips_layout, null);
    public View b;
    public TextView c;
    public TextView d;
    public RainbowBarView e;
    public RainbowBarView f;
    public LinearLayout g;
    public LinearLayout h;
    public a i;
    public bty j;
    public PointOverlay<PointOverlayItem<PointOverlay>> k;

    /* renamed from: baj$a */
    /* compiled from: DriveCommuteTipsOverlayCPointItem */
    public interface a {
        void a();

        void b();

        void c();
    }

    public baj(@NonNull bty bty, @NonNull PointOverlay<PointOverlayItem<PointOverlay>> pointOverlay) {
        this.j = bty;
        this.k = pointOverlay;
        this.a.setLayoutParams(new LayoutParams(-2, -2));
        this.b = this.a.findViewById(R.id.commute_tips_close);
        this.c = (TextView) this.a.findViewById(R.id.commute_tips_destination_home);
        this.d = (TextView) this.a.findViewById(R.id.commute_tips_destination_company);
        this.e = (RainbowBarView) this.a.findViewById(R.id.commute_rainbow_bar_home);
        this.f = (RainbowBarView) this.a.findViewById(R.id.commute_rainbow_bar_company);
        this.g = (LinearLayout) this.a.findViewById(R.id.commute_tips_home);
        this.h = (LinearLayout) this.a.findViewById(R.id.commute_tips_company);
    }
}
