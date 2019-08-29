package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.manger.IIntentUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.intent.BaseIntent;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: dlj reason: default package */
/* compiled from: IntentUtil */
public final class dlj implements IIntentUtil {
    Activity a;
    Intent b;
    String c;
    @SuppressFBWarnings({"URF_UNREAD_FIELD"})
    String d;
    protected boolean e = false;
    dlo f = null;
    dlk g = null;
    BaseIntent h = null;
    protected cqd i = null;

    public dlj(Activity activity, Intent intent) {
        this.a = activity;
        this.b = intent;
        if (this.a != null && this.b != null) {
            this.c = this.b.getAction();
            this.d = this.b.getDataString();
            this.e = false;
        }
    }

    public final void setMapCallBack(cqd cqd) {
        this.i = cqd;
    }

    public final boolean haveSuspendTask() {
        return this.e;
    }

    public final void startSuspendTask() {
        if (this.e) {
            if (this.h != null) {
                this.h.c = this.b;
                this.h.d();
            }
            this.e = false;
        }
    }

    public static void a(Context context) {
        ToastHelper.showLongToast(context.getResources().getString(R.string.para_wrong));
    }

    public static String a(String str, String str2) {
        if (str == null || str.length() == 0 || str2.length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("=");
        String sb2 = sb.toString();
        String[] split = str.split("&");
        int length = split.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (split[i2].indexOf(sb2) >= 0) {
                return split[i2].substring(sb2.length());
            }
        }
        return null;
    }

    public static POI a(String str, boolean z) {
        String str2;
        double d2;
        GeoPoint geoPoint;
        String[] split = str.split(",");
        if (split == null || split.length == 1) {
            return null;
        }
        POI createPOI = POIFactory.createPOI();
        if (split.length >= 2) {
            double parseDouble = Double.parseDouble(split[0]);
            Matcher matcher = Pattern.compile("\\(.*\\)").matcher(split[1]);
            if (matcher.find()) {
                int start = matcher.start();
                str2 = split[1].substring(start + 1, matcher.end() - 1);
                d2 = Double.parseDouble(split[1].substring(0, start));
            } else {
                str2 = "";
                d2 = Double.parseDouble(split[1]);
            }
            Point a2 = cfg.a(parseDouble, d2);
            if (z) {
                geoPoint = cff.a(a2.x, a2.y);
            } else {
                geoPoint = new GeoPoint(a2.x, a2.y);
            }
            createPOI.setPoint(geoPoint);
            createPOI.setName(str2);
        }
        if (split.length >= 3) {
            createPOI.setName(split[2]);
        }
        if (split.length >= 4) {
            createPOI.setAddr(split[3]);
        }
        if (split.length >= 5) {
            createPOI.setPhone(split[4]);
        }
        if (TextUtils.isEmpty(createPOI.getName())) {
            createPOI.setName(AMapAppGlobal.getApplication().getString(R.string.geo_poi_default_name));
        }
        createPOI.setIconId(R.drawable.b_poi_hl);
        return createPOI;
    }

    public final boolean processIntent() {
        boolean z = false;
        this.e = false;
        this.f = null;
        this.g = null;
        this.h = null;
        this.c = null;
        if (this.a == null || this.b == null) {
            return false;
        }
        this.c = this.b.getAction();
        if (this.c != null) {
            this.b.putExtra("FromActivity", 10);
            if (this.c.equals("android.intent.action.VIEW")) {
                this.f = new dlo(this.a, this.b);
                this.f.a(this.i);
                this.h = this.f;
                z = this.f.c();
                this.e = this.f.f;
            } else if (this.c.equals("com.autonavi.minimap.ACTION")) {
                this.g = new dlk(this.a, this.b);
                this.g.a(this.i);
                this.h = this.g;
                z = this.g.c();
                this.e = this.g.f;
            }
            startSuspendTask();
            return z;
        } else if (this.b.getIntExtra("routeType", -1) < 0) {
            return false;
        } else {
            this.h = new dlp(this.a, this.b);
            boolean c2 = this.h.c();
            this.e = this.h.f;
            return c2;
        }
    }
}
