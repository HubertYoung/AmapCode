package defpackage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.drive.widget.StrokedTextView;

/* renamed from: dhm reason: default package */
/* compiled from: RouteCarResultLineIconPointItem */
public final class dhm extends dhp {
    private int h;
    private int k;
    private Context l;
    private float m;

    private dhm(int i, Context context, GeoPoint geoPoint, int i2) {
        super(geoPoint);
        this.h = i2;
        this.k = i;
        this.l = context;
    }

    public static dhm a(int i, Context context, GeoPoint geoPoint, int i2) {
        return new dhm(i, context, geoPoint, i2);
    }

    public final void onPrepareAddItem(PointOverlay pointOverlay) {
        super.onPrepareAddItem(pointOverlay);
        int i = this.h;
        View inflate = LayoutInflater.from(this.l).inflate(R.layout.route_result_turn_point, null);
        StrokedTextView strokedTextView = (StrokedTextView) inflate.findViewById(R.id.turn_point_txt);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.turn_point_icon);
        if (i == 0) {
            imageView.setImageResource(R.drawable.bubble_car_walk);
            strokedTextView.setText(R.string.drive_result_turn_point_foot);
        } else {
            imageView.setImageResource(R.drawable.bubble_car_boat);
            strokedTextView.setText(R.string.drive_result_turn_point_ferry);
        }
        strokedTextView.setTextColor(Color.parseColor("#0090ff"));
        strokedTextView.setStrokedColor(this.l.getResources().getColor(R.color.f_c_1));
        strokedTextView.setStrokedWidth(agn.a(this.l, 2.0f));
        inflate.measure(0, 0);
        if (inflate.getMeasuredWidth() != 0) {
            this.m = ((float) imageView.getMeasuredWidth()) / (((float) inflate.getMeasuredWidth()) * 2.0f);
        } else {
            this.m = 0.5f;
        }
        this.mDefaultMarker = pointOverlay.createMarker(this.k, inflate, 9, this.m, 0.5f, false);
    }

    public final Rect[] a() {
        return new Rect[]{b()};
    }
}
