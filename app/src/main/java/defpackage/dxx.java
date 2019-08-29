package defpackage;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/* renamed from: dxx reason: default package */
/* compiled from: RealTimeBusTools */
public final class dxx {
    public static String a(int i) {
        return i == 0 ? "暂无数据" : i == 1 ? "" : i == 2 ? "暂未发车" : i == 3 ? "非运营时间" : "";
    }

    public static String a() {
        int i;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String[] split = new SimpleDateFormat("HH:mm").format(new Date()).split(":");
        int i2 = 0;
        try {
            int b = ahh.b(split[0]);
            i = ahh.b(split[1]);
            i2 = b;
        } catch (NumberFormatException unused) {
            i = 0;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(decimalFormat.format((long) i2));
        sb.append(":");
        sb.append(decimalFormat.format((long) i));
        return sb.toString();
    }

    public static void a(Context context, ImageView imageView) {
        if (imageView != null) {
            imageView.setVisibility(0);
            Animation loadAnimation = AnimationUtils.loadAnimation(context, R.anim.route_rt_item_progressbar);
            loadAnimation.setInterpolator(new LinearInterpolator());
            loadAnimation.setRepeatCount(-1);
            imageView.startAnimation(loadAnimation);
        }
    }

    public static void a(ImageView imageView) {
        if (imageView != null && imageView.getVisibility() != 8) {
            imageView.setVisibility(8);
            imageView.clearAnimation();
        }
    }

    public static <T extends PointOverlayItem> void a(PointOverlay<T> pointOverlay, int i, boolean z, boolean z2) {
        if (pointOverlay != null && i >= 0) {
            PointOverlayItem pointOverlayItem = (PointOverlayItem) pointOverlay.getItem(i);
            if (pointOverlayItem != null) {
                pointOverlay.setPointItemVisble(pointOverlayItem, z, z2);
            }
        }
    }

    public static <T> boolean a(Collection<T> collection) {
        return collection == null || collection.size() == 0;
    }

    public static <K, T> boolean a(Map<K, T> map) {
        return map == null || map.size() == 0;
    }

    public static GeoPoint[] a(int[] iArr, int[] iArr2) {
        GeoPoint[] geoPointArr = new GeoPoint[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            geoPointArr[i] = new GeoPoint(iArr[i], iArr2[i]);
        }
        return geoPointArr;
    }
}
