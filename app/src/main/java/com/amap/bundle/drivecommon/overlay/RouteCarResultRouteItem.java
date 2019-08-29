package com.amap.bundle.drivecommon.overlay;

import android.content.Context;
import com.autonavi.ae.gmap.gloverlay.GLRouteProperty.EAMapRouteTexture;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.jni.ae.route.model.LineItem;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.RouteItem;
import com.autonavi.minimap.base.overlay.RouteItem.Property;

public final class RouteCarResultRouteItem extends RouteItem {
    public static final int a = agn.a((Context) AMapAppGlobal.getApplication(), 16.0f);
    public static final int b = agn.a((Context) AMapAppGlobal.getApplication(), 14.0f);
    public static final int c = agn.a((Context) AMapAppGlobal.getApplication(), 20.0f);
    public static final int d = agn.a((Context) AMapAppGlobal.getApplication(), 18.0f);
    public static final int e = agn.a((Context) AMapAppGlobal.getApplication(), 2.0f);
    private static Property[] f = a(true, true, true);
    private static Property[] g = a(true, false, true);
    private static Property[] h = a(false, true, true);
    private static Property[] i = a(false, false, true);
    private static Property[] j = a(true, false, false);
    private static Property[] k = a(true, true, false);
    private static Property[] l = a(false, false, false);
    private static Property[] m = a(false, true, false);

    public enum Texture {
        INNER_NAVI_HL(1, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_NAVI, R.drawable.map_frontlr, -16739841, R.drawable.map_frontlr, -16553003, RouteCarResultRouteItem.a, true),
        FERRY_HL(4, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_FERRY, R.drawable.drive_map_lr_dott_gray_light, -1, -1, -1, RouteCarResultRouteItem.a, false),
        INNER_NOT_NAVI_HL(4, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_NONAVI, R.drawable.drive_map_lr_dott_gray_light, -1, -1, -1, RouteCarResultRouteItem.a, false),
        UNKNOWN_HL(1, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_DEFAULT, R.drawable.map_frontlr, -16739841, R.drawable.map_frontlr, -16553003, RouteCarResultRouteItem.a, true),
        TRAFFIC_CLEAR_HL(1, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_OPEN, R.drawable.map_frontlr, -16729569, R.drawable.map_frontlr, -16749038, RouteCarResultRouteItem.a, true),
        TRAFFIC_SLOW_HL(1, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_AMBLE, R.drawable.map_frontlr, -17920, R.drawable.map_frontlr, -3047422, RouteCarResultRouteItem.a, true),
        TRAFFIC_BLOCK_HL(1, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_JAM, R.drawable.map_frontlr, -844512, R.drawable.map_frontlr, -5566703, RouteCarResultRouteItem.a, true),
        TRAFFIC_BLOCK_SERIOUS_HL(1, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_CONGESTED, R.drawable.map_frontlr, -5764853, R.drawable.map_frontlr, -7665397, RouteCarResultRouteItem.a, true),
        RESTRICT_HL(3, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_LIMIT, R.drawable.map_traffic_platenum_restrict_hl, -1, -1, -1, RouteCarResultRouteItem.a, false),
        ARROW_HL(6, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_ARROW, R.drawable.map_aolr, -1, -1, -1, RouteCarResultRouteItem.a, false),
        LONG_COST_HL(2, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_CHARGE, R.drawable.drive_map_lr_feeroad, -1, R.drawable.map_frontlr, -4174591, RouteCarResultRouteItem.c, false),
        LONG_FREE_HL(2, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_FREE, R.drawable.drive_map_lr_nofeeroad, -1, R.drawable.map_frontlr, -16751156, RouteCarResultRouteItem.c, false),
        INNER_NAVI(1, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_NAVI, R.drawable.map_frontlr, -6239252, R.drawable.map_frontlr, -8276762, RouteCarResultRouteItem.b, true),
        FERRY(4, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_FERRY, R.drawable.map_lr_dott_gray, -1, -1, -1, RouteCarResultRouteItem.b, false),
        INNER_NOT_NAVI(4, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_NONAVI, R.drawable.map_lr_dott_gray, -1, -1, -1, RouteCarResultRouteItem.b, false),
        UNKNOWN(1, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_DEFAULT, R.drawable.map_frontlr, -6239252, R.drawable.map_frontlr, -8276762, RouteCarResultRouteItem.b, true),
        TRAFFIC_CLEAR(1, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_OPEN, R.drawable.map_frontlr, -6564923, R.drawable.map_frontlr, -8537177, RouteCarResultRouteItem.b, true),
        TRAFFIC_SLOW(1, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_AMBLE, R.drawable.map_frontlr, -272715, R.drawable.map_frontlr, -2378091, RouteCarResultRouteItem.b, true),
        TRAFFIC_BLOCK(1, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_JAM, R.drawable.map_frontlr, -1985857, R.drawable.map_frontlr, -3170388, RouteCarResultRouteItem.b, true),
        TRAFFIC_BLOCK_SERIOUS(1, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_CONGESTED, R.drawable.map_frontlr, -3172444, R.drawable.map_frontlr, -4487018, RouteCarResultRouteItem.b, true),
        RESTRICT(3, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_LIMIT, R.drawable.map_traffic_platenum_restrict_light, -266567, -1, -1, RouteCarResultRouteItem.b, false),
        ARROW(6, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_ARROW, R.drawable.map_aolr, -1, -1, -1, RouteCarResultRouteItem.b, false),
        LONG_COST(2, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_CHARGE, R.drawable.drive_map_lr_feeroad, -1, -1, -1, RouteCarResultRouteItem.d, false),
        LONG_FREE(2, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_FREE, R.drawable.drive_map_lr_nofeeroad, -1, -1, -1, RouteCarResultRouteItem.d, false),
        DEFAULT(2, EAMapRouteTexture.AMAP_ROUTE_TEXTURE_DEFAULT, R.drawable.map_lr_nodata, -1, -1, -1, RouteCarResultRouteItem.b, true);
        
        public EAMapRouteTexture eaMapRouteTexture;
        public boolean isShowArrow;
        public int lineBgColor;
        public int lineBgResId;
        public int lineColor;
        public int lineResId;
        public int lineStyle;
        public int lineWidth;

        private Texture(int i, EAMapRouteTexture eAMapRouteTexture, int i2, int i3, int i4, int i5, int i6, boolean z) {
            this.lineStyle = i;
            this.eaMapRouteTexture = eAMapRouteTexture;
            this.lineResId = i2;
            this.lineColor = i3;
            this.lineBgResId = i4;
            this.lineBgColor = i5;
            this.lineWidth = i6;
            this.isShowArrow = z;
        }
    }

    public RouteCarResultRouteItem(LineItem lineItem) {
        super(lineItem, f);
        setScene(0);
        setSelected(true);
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public RouteCarResultRouteItem(LineItem lineItem, boolean z, boolean z2) {
        // Property[] propertyArr = z ? z2 ? k : j : z2 ? m : l;
        super(lineItem, propertyArr);
        setScene(0);
        setSelected(z);
    }

    private static Property[] a(boolean z, boolean z2, boolean z3) {
        Property[] propertyArr = new Property[12];
        if (z) {
            propertyArr[0] = a(Texture.INNER_NAVI_HL, z3, z2);
            propertyArr[1] = a(Texture.INNER_NOT_NAVI_HL, z3, z2);
            propertyArr[2] = a(Texture.UNKNOWN_HL, z3, z2);
            propertyArr[3] = a(Texture.TRAFFIC_CLEAR_HL, z3, z2);
            propertyArr[4] = a(Texture.TRAFFIC_SLOW_HL, z3, z2);
            propertyArr[5] = a(Texture.TRAFFIC_BLOCK_HL, z3, z2);
            propertyArr[6] = a(Texture.TRAFFIC_BLOCK_SERIOUS_HL, z3, z2);
            propertyArr[7] = a(Texture.RESTRICT_HL, z3, z2);
            propertyArr[8] = a(Texture.ARROW_HL, z3, z2);
            propertyArr[9] = a(Texture.LONG_COST_HL, z3, z2);
            propertyArr[10] = a(Texture.LONG_FREE_HL, z3, z2);
            propertyArr[11] = a(Texture.FERRY_HL, z3, z2);
        } else {
            propertyArr[0] = a(Texture.INNER_NAVI, z3, z2);
            propertyArr[1] = a(Texture.INNER_NOT_NAVI, z3, z2);
            propertyArr[2] = a(Texture.UNKNOWN, z3, z2);
            propertyArr[3] = a(Texture.TRAFFIC_CLEAR, z3, z2);
            propertyArr[4] = a(Texture.TRAFFIC_SLOW, z3, z2);
            propertyArr[5] = a(Texture.TRAFFIC_BLOCK, z3, z2);
            propertyArr[6] = a(Texture.TRAFFIC_BLOCK_SERIOUS, z3, z2);
            propertyArr[7] = a(Texture.RESTRICT, z3, z2);
            propertyArr[8] = a(Texture.ARROW, z3, z2);
            propertyArr[9] = a(Texture.LONG_COST, z3, z2);
            propertyArr[10] = a(Texture.LONG_FREE, z3, z2);
            propertyArr[11] = a(Texture.FERRY, z3, z2);
        }
        return propertyArr;
    }

    private static Property a(Texture texture, boolean z, boolean z2) {
        Property property = new Property(texture.lineStyle, texture.lineResId);
        property.setFillLineId(texture.lineResId);
        property.setFillLineColor(texture.lineColor);
        property.setBackgrondId(texture.lineBgResId);
        property.setBackgroundColor(texture.lineBgColor);
        property.setBorderLineWidth(texture.lineWidth + (e * 2));
        property.euRouteTexture = texture.eaMapRouteTexture;
        property.setLineWidth(texture.lineWidth);
        property.setShowArrow(texture.isShowArrow && z2);
        property.setIsCanCovered(z);
        return property;
    }
}
