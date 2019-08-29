package defpackage;

import com.alipay.mobile.beehive.audio.Constants;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import java.util.AbstractMap.SimpleEntry;

/* renamed from: bxt reason: default package */
/* compiled from: SearchIdqPlusLogHelper */
public final class bxt {
    public boolean a = true;

    public static void a(POI poi, String str, String str2) {
        if (poi != null) {
            LogManager.actionLogV25("1000", "1", new SimpleEntry("page", "idq_plus"), new SimpleEntry("click", "short_click"), new SimpleEntry("type", str2), new SimpleEntry("status", a(str)), new SimpleEntry("new_type", poi.getType()), new SimpleEntry(Constants.KEY_AUDIO_BUSINESS_ID, poi.getIndustry()), new SimpleEntry(TrafficUtil.POIID, poi.getId()));
        }
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r2) {
        /*
            java.lang.String r0 = ""
            int r1 = r2.hashCode()
            switch(r1) {
                case 50: goto L_0x0014;
                case 51: goto L_0x000a;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x001e
        L_0x000a:
            java.lang.String r1 = "3"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x001e
            r2 = 1
            goto L_0x001f
        L_0x0014:
            java.lang.String r1 = "2"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x001e
            r2 = 0
            goto L_0x001f
        L_0x001e:
            r2 = -1
        L_0x001f:
            switch(r2) {
                case 0: goto L_0x0026;
                case 1: goto L_0x0023;
                default: goto L_0x0022;
            }
        L_0x0022:
            goto L_0x0028
        L_0x0023:
            java.lang.String r0 = "2"
            goto L_0x0028
        L_0x0026:
            java.lang.String r0 = "1"
        L_0x0028:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bxt.a(java.lang.String):java.lang.String");
    }
}
