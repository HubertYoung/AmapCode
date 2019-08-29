package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.ae.search.SearchEngine;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleInstance;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTop;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;

/* renamed from: bby reason: default package */
/* compiled from: Utils */
public final class bby {
    public static int a(String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            i = Integer.parseInt(str);
        } catch (Exception unused) {
            i = 0;
        }
        return i;
    }

    public static double b(String str) {
        double d = 0.0d;
        if (TextUtils.isEmpty(str)) {
            return 0.0d;
        }
        try {
            d = Double.parseDouble(str);
        } catch (Exception unused) {
        }
        return d;
    }

    public static int a(String str, Context context) {
        if (TextUtils.isEmpty(str) || context == null) {
            return -1;
        }
        return context.getResources().getIdentifier(str, ResUtils.DRAWABLE, context.getPackageName());
    }

    public static Bitmap a(Drawable drawable) {
        if (!(drawable instanceof BitmapDrawable)) {
            return null;
        }
        return ((BitmapDrawable) drawable).getBitmap();
    }

    public static boolean c(String str) {
        boolean z;
        if (TextUtils.isEmpty(str)) {
            GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(DoNotUseTool.getMapCenter());
            if (glGeoPoint2GeoPoint == null) {
                return false;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(glGeoPoint2GeoPoint.getAdCode());
            str = sb.toString();
            if (TextUtils.isEmpty(str)) {
                return false;
            }
        }
        try {
            z = SearchEngine.isExistByAdCode(Integer.parseInt(str));
        } catch (Exception unused) {
            z = false;
        }
        return z;
    }

    public static void a(bid bid, Class<? extends AbstractBasePage> cls, PageBundle pageBundle) {
        if (bid != null) {
            bid.startPage((Class) cls, pageBundle);
            if (!cls.isInstance(bid) || (!launchModeSingleInstance.class.isAssignableFrom(cls) && !launchModeSingleTop.class.isAssignableFrom(cls))) {
                bid.finish();
            }
        }
    }
}
