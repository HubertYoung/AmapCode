package com.autonavi.minimap.basemap.favorites.data;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import java.io.Serializable;

public class ItemKey implements Serializable {
    public static final String ID = "id";
    public static final String TYPE = "type";
    private static final long serialVersionUID = -3416024026881403347L;
    public String id = "";
    public int type = -1;

    public void generateKeyId() {
    }

    public String getKeyId() {
        if (this.id == null || this.id.length() == 0) {
            generateKeyId();
        }
        return this.id;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0028  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String createMD5(java.lang.String r4) {
        /*
            r0 = 0
            java.lang.String r1 = "MD5"
            java.security.MessageDigest r1 = java.security.MessageDigest.getInstance(r1)     // Catch:{ NoSuchAlgorithmException -> 0x001e, UnsupportedEncodingException -> 0x0018 }
            r1.reset()     // Catch:{ NoSuchAlgorithmException -> 0x0016, UnsupportedEncodingException -> 0x0014 }
            java.lang.String r0 = "UTF-8"
            byte[] r4 = r4.getBytes(r0)     // Catch:{ NoSuchAlgorithmException -> 0x0016, UnsupportedEncodingException -> 0x0014 }
            r1.update(r4)     // Catch:{ NoSuchAlgorithmException -> 0x0016, UnsupportedEncodingException -> 0x0014 }
            goto L_0x0023
        L_0x0014:
            r4 = move-exception
            goto L_0x001a
        L_0x0016:
            r4 = move-exception
            goto L_0x0020
        L_0x0018:
            r4 = move-exception
            r1 = r0
        L_0x001a:
            defpackage.kf.a(r4)
            goto L_0x0023
        L_0x001e:
            r4 = move-exception
            r1 = r0
        L_0x0020:
            defpackage.kf.a(r4)
        L_0x0023:
            if (r1 != 0) goto L_0x0028
            java.lang.String r4 = ""
            return r4
        L_0x0028:
            byte[] r4 = r1.digest()
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r1 = 0
        L_0x0032:
            int r2 = r4.length
            if (r1 >= r2) goto L_0x0063
            byte r2 = r4[r1]
            r2 = r2 & 255(0xff, float:3.57E-43)
            java.lang.String r2 = java.lang.Integer.toHexString(r2)
            int r2 = r2.length()
            r3 = 1
            if (r2 != r3) goto L_0x0055
            java.lang.String r2 = "0"
            r0.append(r2)
            byte r2 = r4[r1]
            r2 = r2 & 255(0xff, float:3.57E-43)
            java.lang.String r2 = java.lang.Integer.toHexString(r2)
            r0.append(r2)
            goto L_0x0060
        L_0x0055:
            byte r2 = r4[r1]
            r2 = r2 & 255(0xff, float:3.57E-43)
            java.lang.String r2 = java.lang.Integer.toHexString(r2)
            r0.append(r2)
        L_0x0060:
            int r1 = r1 + 1
            goto L_0x0032
        L_0x0063:
            java.lang.String r4 = r0.toString()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.basemap.favorites.data.ItemKey.createMD5(java.lang.String):java.lang.String");
    }

    public static String createPoiItemKey(POI poi) {
        GeoPoint point = poi.getPoint();
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(point.x);
        sb.append("+");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(point.y);
        sb3.append("+");
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb4);
        sb5.append(poi.getName());
        return createMD5(sb5.toString());
    }
}
