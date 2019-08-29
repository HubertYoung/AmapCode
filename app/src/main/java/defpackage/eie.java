package defpackage;

import android.app.Activity;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.route.subway.page.SubwayWebViewPage;
import com.autonavi.sdk.location.LocationInstrument;

@BundleInterface(bdk.class)
/* renamed from: eie reason: default package */
/* compiled from: SubwayService */
public class eie extends esi implements bdk {
    public final void a(Activity activity, String str) {
        eif.a();
        eif.a(activity, str);
    }

    public final void a(bid bid, String str, String str2, String str3) {
        eif.a();
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", ConfigerHelper.getInstance().getSubwayUrl());
        pageBundle.putString("adCode", str);
        bnr bnr = new bnr();
        bnr.clear("amap-subway-init");
        bnr.setItem("amap-subway-init", "city", str);
        bnr.setItem("amap-subway-init", LocationInstrument.LOCATION_EXTRAS_KEY_POIID, str2);
        bnr.setItem("amap-subway-init", "lnglat", str3);
        bnr.setItem("amap-subway-init", "detail", "true");
        bid.startPage(SubwayWebViewPage.class, pageBundle);
    }
}
