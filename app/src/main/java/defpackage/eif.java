package defpackage;

import android.app.Activity;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.subway.page.SubwayWebViewPage;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.SoftReference;

/* renamed from: eif reason: default package */
/* compiled from: SubwayControllerImpl */
public final class eif {
    private static SoftReference<eif> a;

    public static eif a() {
        if (a == null || a.get() == null) {
            return new eif();
        }
        return a.get();
    }

    public eif() {
        if (a == null || a.get() == null) {
            a = new SoftReference<>(this);
        }
    }

    public static void a(Activity activity, String str) {
        if (TextUtils.isEmpty(str) && (activity instanceof buo)) {
            GLGeoPoint mapCenter = DoNotUseTool.getMapCenter();
            if (mapCenter != null) {
                str = String.valueOf(GeoPoint.glGeoPoint2GeoPoint(mapCenter).getAdCode());
            }
        }
        String str2 = null;
        boolean z = false;
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        if (latestPosition != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(latestPosition.getLongitude());
            sb.append(",");
            sb.append(latestPosition.getLatitude());
            str2 = sb.toString();
            String valueOf = String.valueOf(latestPosition.getAdCode());
            if (!TextUtils.isEmpty(valueOf) && valueOf.equals(str)) {
                z = true;
            }
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", ConfigerHelper.getInstance().getSubwayUrl());
        pageBundle.putString("adCode", str);
        bnr bnr = new bnr();
        bnr.clear("amap-subway-init");
        if (z) {
            bnr.setItem("amap-subway-init", "city", str);
            bnr.setItem("amap-subway-init", "lnglat", str2);
        } else if (str != null) {
            bnr.setItem("amap-subway-init", "city", str);
        } else {
            bnr.setItem("amap-subway-init", "city", "");
        }
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage(SubwayWebViewPage.class, pageBundle);
        }
    }
}
