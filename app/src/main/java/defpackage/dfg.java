package defpackage;

import android.content.Context;
import com.autonavi.ae.gmap.gloverlay.GLRouteProperty.EAMapRouteTexture;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.jni.ae.route.model.LineItem;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.RouteItem;
import com.autonavi.minimap.base.overlay.RouteItem.Property;

/* renamed from: dfg reason: default package */
/* compiled from: ErrorReportRouteItem */
public final class dfg extends RouteItem {
    private static final int a = agn.a((Context) AMapAppGlobal.getApplication(), 12.0f);
    private static final int b = R.drawable.map_alr_night;

    private dfg(LineItem lineItem, Property[] propertyArr) {
        super(lineItem, propertyArr);
        setScene(4);
    }

    public static dfg a(LineItem lineItem) {
        Property property = new Property(3, b);
        property.setLineWidth(a);
        property.setBorderLineWidth(a);
        property.setEuRouteTexture(EAMapRouteTexture.AMAP_ROUTE_TEXTURE_WRONG);
        return new dfg(lineItem, new Property[]{property});
    }
}
