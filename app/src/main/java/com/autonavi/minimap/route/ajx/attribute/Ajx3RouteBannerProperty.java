package com.autonavi.minimap.route.ajx.attribute;

import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.route.common.view.RouteBanner;

public class Ajx3RouteBannerProperty extends BaseProperty<RouteBanner> {
    private static final String BANNER_KEY = "type";
    private static final String BUS_RESULT = "buslist";
    private static final String TAB_BUS = "bus";
    private static final String TAB_COACH = "coach";
    private static final String TAB_FOOT = "foot";
    private static final String TAB_RIDE = "ride";
    private static final String TAB_TRAIN = "train";

    public Ajx3RouteBannerProperty(@NonNull RouteBanner routeBanner, @NonNull IAjxContext iAjxContext) {
        super(routeBanner, iAjxContext);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0063, code lost:
        if (r0.equals("bus") != false) goto L_0x0067;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r5, java.lang.Object r6) {
        /*
            r4 = this;
            if (r6 != 0) goto L_0x0003
            return
        L_0x0003:
            int r0 = r5.hashCode()
            r1 = 3575610(0x368f3a, float:5.010497E-39)
            r2 = 0
            r3 = -1
            if (r0 == r1) goto L_0x000f
            goto L_0x001a
        L_0x000f:
            java.lang.String r0 = "type"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x001a
            r0 = 0
            goto L_0x001b
        L_0x001a:
            r0 = -1
        L_0x001b:
            if (r0 == 0) goto L_0x001f
            goto L_0x00a6
        L_0x001f:
            r0 = r6
            java.lang.String r0 = (java.lang.String) r0
            int r1 = r0.hashCode()
            switch(r1) {
                case 97920: goto L_0x005d;
                case 3148910: goto L_0x0053;
                case 3500280: goto L_0x0049;
                case 94831770: goto L_0x003f;
                case 110621192: goto L_0x0034;
                case 240185118: goto L_0x002a;
                default: goto L_0x0029;
            }
        L_0x0029:
            goto L_0x0066
        L_0x002a:
            java.lang.String r1 = "buslist"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0066
            r2 = 5
            goto L_0x0067
        L_0x0034:
            java.lang.String r1 = "train"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0066
            r2 = 3
            goto L_0x0067
        L_0x003f:
            java.lang.String r1 = "coach"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0066
            r2 = 4
            goto L_0x0067
        L_0x0049:
            java.lang.String r1 = "ride"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0066
            r2 = 2
            goto L_0x0067
        L_0x0053:
            java.lang.String r1 = "foot"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0066
            r2 = 1
            goto L_0x0067
        L_0x005d:
            java.lang.String r1 = "bus"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0066
            goto L_0x0067
        L_0x0066:
            r2 = -1
        L_0x0067:
            switch(r2) {
                case 0: goto L_0x009d;
                case 1: goto L_0x0093;
                case 2: goto L_0x0089;
                case 3: goto L_0x007f;
                case 4: goto L_0x0075;
                case 5: goto L_0x006b;
                default: goto L_0x006a;
            }
        L_0x006a:
            goto L_0x00a6
        L_0x006b:
            android.view.View r0 = r4.mView
            com.autonavi.minimap.route.common.view.RouteBanner r0 = (com.autonavi.minimap.route.common.view.RouteBanner) r0
            java.lang.String r1 = "26"
            r0.loadbanner(r1)
            goto L_0x00a6
        L_0x0075:
            android.view.View r0 = r4.mView
            com.autonavi.minimap.route.common.view.RouteBanner r0 = (com.autonavi.minimap.route.common.view.RouteBanner) r0
            java.lang.String r1 = "37"
            r0.loadbanner(r1)
            goto L_0x00a6
        L_0x007f:
            android.view.View r0 = r4.mView
            com.autonavi.minimap.route.common.view.RouteBanner r0 = (com.autonavi.minimap.route.common.view.RouteBanner) r0
            java.lang.String r1 = "33"
            r0.loadbanner(r1)
            goto L_0x00a6
        L_0x0089:
            android.view.View r0 = r4.mView
            com.autonavi.minimap.route.common.view.RouteBanner r0 = (com.autonavi.minimap.route.common.view.RouteBanner) r0
            java.lang.String r1 = "36"
            r0.loadbanner(r1)
            goto L_0x00a6
        L_0x0093:
            android.view.View r0 = r4.mView
            com.autonavi.minimap.route.common.view.RouteBanner r0 = (com.autonavi.minimap.route.common.view.RouteBanner) r0
            java.lang.String r1 = "38"
            r0.loadbanner(r1)
            goto L_0x00a6
        L_0x009d:
            android.view.View r0 = r4.mView
            com.autonavi.minimap.route.common.view.RouteBanner r0 = (com.autonavi.minimap.route.common.view.RouteBanner) r0
            java.lang.String r1 = "25"
            r0.loadbanner(r1)
        L_0x00a6:
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            if (r0 == 0) goto L_0x00ad
            return
        L_0x00ad:
            super.updateAttribute(r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.ajx.attribute.Ajx3RouteBannerProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }
}
