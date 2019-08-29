package defpackage;

import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;

/* renamed from: dcw reason: default package */
/* compiled from: ShareUtil */
public final class dcw {
    public static final String a = AMapAppGlobal.getApplication().getApplicationContext().getResources().getString(R.string.find_my_location);

    public static String a(dcq dcq) {
        if (!dcq.needToShortUrl) {
            return dcq.url;
        }
        if (dcq.useCustomUrl != 0 || TextUtils.isEmpty(dcq.url) || dcq.url.toLowerCase().startsWith(AjxHttpLoader.DOMAIN_HTTP)) {
            return dcq.url;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ConfigerHelper.getInstance().getShareMsgUrl());
        sb.append("?");
        sb.append(dcq.url);
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x049f  */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x04a4  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0511  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0549  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0559  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x055c  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x05ce  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0626  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x010d  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0472  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(android.content.Context r24, defpackage.dct r25, com.autonavi.common.model.POI r26, java.lang.String r27) {
        /*
            r1 = r24
            r2 = r25
            android.app.Application r3 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.Context r3 = r3.getApplicationContext()
            android.content.res.Resources r3 = r3.getResources()
            if (r26 == 0) goto L_0x0637
            if (r2 != 0) goto L_0x0016
            goto L_0x0637
        L_0x0016:
            java.lang.String r5 = ""
            com.autonavi.minimap.bundle.share.entity.ShareParam$f r7 = new com.autonavi.minimap.bundle.share.entity.ShareParam$f
            r7.<init>()
            com.autonavi.minimap.bundle.share.entity.ShareParam$e r8 = new com.autonavi.minimap.bundle.share.entity.ShareParam$e
            r6 = 0
            r8.<init>(r6)
            com.autonavi.minimap.bundle.share.entity.ShareParam$e r9 = new com.autonavi.minimap.bundle.share.entity.ShareParam$e
            r10 = 1
            r9.<init>(r10)
            com.autonavi.minimap.bundle.share.entity.ShareParam$b r11 = new com.autonavi.minimap.bundle.share.entity.ShareParam$b
            r11.<init>(r6)
            com.autonavi.minimap.bundle.share.entity.ShareParam$b r12 = new com.autonavi.minimap.bundle.share.entity.ShareParam$b
            r12.<init>(r10)
            com.autonavi.minimap.bundle.share.entity.ShareParam$c r13 = new com.autonavi.minimap.bundle.share.entity.ShareParam$c
            r13.<init>()
            com.autonavi.minimap.bundle.share.entity.ShareParam$d r14 = new com.autonavi.minimap.bundle.share.entity.ShareParam$d
            r14.<init>()
            com.autonavi.minimap.bundle.share.entity.ShareParam$DingDingParam r15 = new com.autonavi.minimap.bundle.share.entity.ShareParam$DingDingParam
            r15.<init>()
            java.lang.String r10 = ""
            java.lang.String r16 = r26.getId()
            if (r16 == 0) goto L_0x01bc
            java.lang.String r6 = r26.getId()
            int r6 = r6.length()
            if (r6 <= 0) goto L_0x01bc
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x00bf }
            r17 = r5
            java.lang.String r5 = "?p="
            r6.<init>(r5)     // Catch:{ UnsupportedEncodingException -> 0x00bd }
            java.lang.String r5 = r26.getId()     // Catch:{ UnsupportedEncodingException -> 0x00bd }
            r6.append(r5)     // Catch:{ UnsupportedEncodingException -> 0x00bd }
            java.lang.String r5 = ","
            r6.append(r5)     // Catch:{ UnsupportedEncodingException -> 0x00bd }
            com.autonavi.common.model.GeoPoint r5 = r26.getPoint()     // Catch:{ UnsupportedEncodingException -> 0x00bd }
            r19 = r10
            r18 = r11
            double r10 = r5.getLatitude()     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            r6.append(r10)     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            java.lang.String r5 = ","
            r6.append(r5)     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            com.autonavi.common.model.GeoPoint r5 = r26.getPoint()     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            double r10 = r5.getLongitude()     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            r6.append(r10)     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            java.lang.String r5 = ","
            r6.append(r5)     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            java.lang.String r5 = r26.getName()     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            java.lang.String r10 = "UTF-8"
            java.lang.String r5 = java.net.URLEncoder.encode(r5, r10)     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            r6.append(r5)     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            java.lang.String r5 = ","
            r6.append(r5)     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            java.lang.String r5 = r26.getAddr()     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            if (r5 != 0) goto L_0x00b6
            java.lang.String r5 = r26.getAddr()     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            java.lang.String r10 = "UTF-8"
            java.lang.String r5 = java.net.URLEncoder.encode(r5, r10)     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            r6.append(r5)     // Catch:{ UnsupportedEncodingException -> 0x00bb }
        L_0x00b6:
            java.lang.String r10 = r6.toString()     // Catch:{ UnsupportedEncodingException -> 0x00bb }
            goto L_0x00cc
        L_0x00bb:
            r0 = move-exception
            goto L_0x00c6
        L_0x00bd:
            r0 = move-exception
            goto L_0x00c2
        L_0x00bf:
            r0 = move-exception
            r17 = r5
        L_0x00c2:
            r19 = r10
            r18 = r11
        L_0x00c6:
            r5 = r0
            r5.printStackTrace()
            r10 = r19
        L_0x00cc:
            java.lang.String r5 = r26.getName()
            java.lang.String r6 = a
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x010d
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            int r6 = com.autonavi.minimap.R.string.shareutil_i_am_at
            java.lang.String r6 = r3.getString(r6)
            r5.append(r6)
            java.lang.String r6 = "："
            r5.append(r6)
            java.lang.String r6 = r26.getAddr()
            r5.append(r6)
            java.lang.String r6 = " "
            r5.append(r6)
            int r6 = com.autonavi.minimap.R.string.shareutil_find_me_on_amap
            java.lang.String r6 = r3.getString(r6)
            r5.append(r6)
            java.lang.String r6 = "："
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            goto L_0x0466
        L_0x010d:
            java.lang.String r5 = r26.getPhone()
            java.lang.String r6 = ""
            if (r5 == 0) goto L_0x011e
            java.lang.String r11 = ""
            boolean r11 = r11.equals(r5)
            if (r11 != 0) goto L_0x011e
            goto L_0x011f
        L_0x011e:
            r5 = r6
        L_0x011f:
            java.lang.String r6 = ""
            java.lang.String r11 = r26.getAddr()
            boolean r11 = android.text.TextUtils.isEmpty(r11)
            if (r11 != 0) goto L_0x0140
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r11 = r26.getAddr()
            r6.append(r11)
            java.lang.String r11 = ","
            r6.append(r11)
            java.lang.String r6 = r6.toString()
        L_0x0140:
            java.lang.String r11 = ""
            boolean r11 = r11.equals(r5)
            if (r11 == 0) goto L_0x0176
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r11 = "【"
            r5.<init>(r11)
            java.lang.String r11 = r26.getName()
            r5.append(r11)
            java.lang.String r11 = "】 : "
            r5.append(r11)
            r5.append(r6)
            int r6 = com.autonavi.minimap.R.string.shareutil_see_detail_at
            java.lang.String r6 = r3.getString(r6)
            r5.append(r6)
            java.lang.String r6 = "："
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r20 = r10
            goto L_0x01b4
        L_0x0176:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r20 = r10
            java.lang.String r10 = "【"
            r11.<init>(r10)
            java.lang.String r10 = r26.getName()
            r11.append(r10)
            java.lang.String r10 = "】 : "
            r11.append(r10)
            r11.append(r6)
            int r6 = com.autonavi.minimap.R.string.shareutil_phone
            java.lang.String r6 = r3.getString(r6)
            r11.append(r6)
            r11.append(r5)
            java.lang.String r5 = ","
            r11.append(r5)
            int r5 = com.autonavi.minimap.R.string.shareutil_see_detail_at
            java.lang.String r5 = r3.getString(r5)
            r11.append(r5)
            java.lang.String r5 = "："
            r11.append(r5)
            java.lang.String r5 = r11.toString()
        L_0x01b4:
            r14.a = r5
            r5 = r17
            r10 = r20
            goto L_0x0466
        L_0x01bc:
            r17 = r5
            r19 = r10
            r18 = r11
            java.lang.String r5 = r26.getAddr()
            if (r5 == 0) goto L_0x0304
            java.lang.String r5 = r26.getName()
            java.lang.String r6 = a
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0304
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x020d }
            java.lang.String r6 = "?q="
            r5.<init>(r6)     // Catch:{ UnsupportedEncodingException -> 0x020d }
            com.autonavi.common.model.GeoPoint r6 = r26.getPoint()     // Catch:{ UnsupportedEncodingException -> 0x020d }
            double r10 = r6.getLatitude()     // Catch:{ UnsupportedEncodingException -> 0x020d }
            r5.append(r10)     // Catch:{ UnsupportedEncodingException -> 0x020d }
            java.lang.String r6 = ","
            r5.append(r6)     // Catch:{ UnsupportedEncodingException -> 0x020d }
            com.autonavi.common.model.GeoPoint r6 = r26.getPoint()     // Catch:{ UnsupportedEncodingException -> 0x020d }
            double r10 = r6.getLongitude()     // Catch:{ UnsupportedEncodingException -> 0x020d }
            r5.append(r10)     // Catch:{ UnsupportedEncodingException -> 0x020d }
            java.lang.String r6 = ","
            r5.append(r6)     // Catch:{ UnsupportedEncodingException -> 0x020d }
            java.lang.String r6 = r26.getAddr()     // Catch:{ UnsupportedEncodingException -> 0x020d }
            java.lang.String r10 = "UTF-8"
            java.lang.String r6 = java.net.URLEncoder.encode(r6, r10)     // Catch:{ UnsupportedEncodingException -> 0x020d }
            r5.append(r6)     // Catch:{ UnsupportedEncodingException -> 0x020d }
            java.lang.String r10 = r5.toString()     // Catch:{ UnsupportedEncodingException -> 0x020d }
            goto L_0x0214
        L_0x020d:
            r0 = move-exception
            r5 = r0
            r5.printStackTrace()
            r10 = r19
        L_0x0214:
            java.lang.String r5 = r26.getName()
            java.lang.String r6 = a
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0255
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            int r6 = com.autonavi.minimap.R.string.shareutil_i_am_at
            java.lang.String r6 = r3.getString(r6)
            r5.append(r6)
            java.lang.String r6 = "："
            r5.append(r6)
            java.lang.String r6 = r26.getAddr()
            r5.append(r6)
            java.lang.String r6 = " "
            r5.append(r6)
            int r6 = com.autonavi.minimap.R.string.shareutil_find_me_on_amap
            java.lang.String r6 = r3.getString(r6)
            r5.append(r6)
            java.lang.String r6 = "："
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            goto L_0x0466
        L_0x0255:
            java.lang.String r5 = r26.getPhone()
            java.lang.String r6 = ""
            if (r5 == 0) goto L_0x0266
            java.lang.String r11 = ""
            boolean r11 = r11.equals(r5)
            if (r11 != 0) goto L_0x0266
            goto L_0x0267
        L_0x0266:
            r5 = r6
        L_0x0267:
            java.lang.String r6 = ""
            java.lang.String r11 = r26.getAddr()
            boolean r11 = android.text.TextUtils.isEmpty(r11)
            if (r11 != 0) goto L_0x0288
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r11 = r26.getAddr()
            r6.append(r11)
            java.lang.String r11 = ","
            r6.append(r11)
            java.lang.String r6 = r6.toString()
        L_0x0288:
            java.lang.String r11 = ""
            boolean r11 = r11.equals(r5)
            if (r11 == 0) goto L_0x02be
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r11 = "【"
            r5.<init>(r11)
            java.lang.String r11 = r26.getName()
            r5.append(r11)
            java.lang.String r11 = "】 : "
            r5.append(r11)
            r5.append(r6)
            int r6 = com.autonavi.minimap.R.string.shareutil_see_detail_at
            java.lang.String r6 = r3.getString(r6)
            r5.append(r6)
            java.lang.String r6 = "："
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r21 = r10
            goto L_0x02fc
        L_0x02be:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r21 = r10
            java.lang.String r10 = "【"
            r11.<init>(r10)
            java.lang.String r10 = r26.getName()
            r11.append(r10)
            java.lang.String r10 = "】 : "
            r11.append(r10)
            r11.append(r6)
            int r6 = com.autonavi.minimap.R.string.shareutil_phone
            java.lang.String r6 = r3.getString(r6)
            r11.append(r6)
            r11.append(r5)
            java.lang.String r5 = ","
            r11.append(r5)
            int r5 = com.autonavi.minimap.R.string.shareutil_see_detail_at
            java.lang.String r5 = r3.getString(r5)
            r11.append(r5)
            java.lang.String r5 = "："
            r11.append(r5)
            java.lang.String r5 = r11.toString()
        L_0x02fc:
            r14.a = r5
            r5 = r17
            r10 = r21
            goto L_0x0466
        L_0x0304:
            java.lang.String r5 = r26.getName()
            if (r5 == 0) goto L_0x034d
            int r6 = r5.length()
            if (r6 <= 0) goto L_0x034d
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x0345 }
            java.lang.String r10 = "?q="
            r6.<init>(r10)     // Catch:{ UnsupportedEncodingException -> 0x0345 }
            com.autonavi.common.model.GeoPoint r10 = r26.getPoint()     // Catch:{ UnsupportedEncodingException -> 0x0345 }
            double r10 = r10.getLatitude()     // Catch:{ UnsupportedEncodingException -> 0x0345 }
            r6.append(r10)     // Catch:{ UnsupportedEncodingException -> 0x0345 }
            java.lang.String r10 = ","
            r6.append(r10)     // Catch:{ UnsupportedEncodingException -> 0x0345 }
            com.autonavi.common.model.GeoPoint r10 = r26.getPoint()     // Catch:{ UnsupportedEncodingException -> 0x0345 }
            double r10 = r10.getLongitude()     // Catch:{ UnsupportedEncodingException -> 0x0345 }
            r6.append(r10)     // Catch:{ UnsupportedEncodingException -> 0x0345 }
            java.lang.String r10 = ","
            r6.append(r10)     // Catch:{ UnsupportedEncodingException -> 0x0345 }
            java.lang.String r10 = "UTF-8"
            java.lang.String r5 = java.net.URLEncoder.encode(r5, r10)     // Catch:{ UnsupportedEncodingException -> 0x0345 }
            r6.append(r5)     // Catch:{ UnsupportedEncodingException -> 0x0345 }
            java.lang.String r10 = r6.toString()     // Catch:{ UnsupportedEncodingException -> 0x0345 }
            goto L_0x0378
        L_0x0345:
            r0 = move-exception
            r5 = r0
            r5.printStackTrace()
            r10 = r19
            goto L_0x0378
        L_0x034d:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "?q="
            r5.<init>(r6)
            com.autonavi.common.model.GeoPoint r6 = r26.getPoint()
            double r10 = r6.getLatitude()
            r5.append(r10)
            java.lang.String r6 = ","
            r5.append(r6)
            com.autonavi.common.model.GeoPoint r6 = r26.getPoint()
            double r10 = r6.getLongitude()
            r5.append(r10)
            java.lang.String r6 = ","
            r5.append(r6)
            java.lang.String r10 = r5.toString()
        L_0x0378:
            java.lang.String r5 = r26.getName()
            java.lang.String r6 = a
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x03b9
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            int r6 = com.autonavi.minimap.R.string.shareutil_i_am_at
            java.lang.String r6 = r3.getString(r6)
            r5.append(r6)
            java.lang.String r6 = "："
            r5.append(r6)
            java.lang.String r6 = r26.getAddr()
            r5.append(r6)
            java.lang.String r6 = " "
            r5.append(r6)
            int r6 = com.autonavi.minimap.R.string.shareutil_find_me_on_amap
            java.lang.String r6 = r3.getString(r6)
            r5.append(r6)
            java.lang.String r6 = "："
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            goto L_0x0466
        L_0x03b9:
            java.lang.String r5 = r26.getPhone()
            java.lang.String r6 = ""
            if (r5 == 0) goto L_0x03ca
            java.lang.String r11 = ""
            boolean r11 = r11.equals(r5)
            if (r11 != 0) goto L_0x03ca
            goto L_0x03cb
        L_0x03ca:
            r5 = r6
        L_0x03cb:
            java.lang.String r6 = ""
            java.lang.String r11 = r26.getAddr()
            boolean r11 = android.text.TextUtils.isEmpty(r11)
            if (r11 != 0) goto L_0x03ec
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r11 = r26.getAddr()
            r6.append(r11)
            java.lang.String r11 = ","
            r6.append(r11)
            java.lang.String r6 = r6.toString()
        L_0x03ec:
            java.lang.String r11 = ""
            boolean r11 = r11.equals(r5)
            if (r11 == 0) goto L_0x0422
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r11 = "【"
            r5.<init>(r11)
            java.lang.String r11 = r26.getName()
            r5.append(r11)
            java.lang.String r11 = "】 : "
            r5.append(r11)
            r5.append(r6)
            int r6 = com.autonavi.minimap.R.string.shareutil_see_detail_at
            java.lang.String r6 = r3.getString(r6)
            r5.append(r6)
            java.lang.String r6 = "："
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r22 = r10
            goto L_0x0460
        L_0x0422:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r22 = r10
            java.lang.String r10 = "【"
            r11.<init>(r10)
            java.lang.String r10 = r26.getName()
            r11.append(r10)
            java.lang.String r10 = "】 : "
            r11.append(r10)
            r11.append(r6)
            int r6 = com.autonavi.minimap.R.string.shareutil_phone
            java.lang.String r6 = r3.getString(r6)
            r11.append(r6)
            r11.append(r5)
            java.lang.String r5 = ","
            r11.append(r5)
            int r5 = com.autonavi.minimap.R.string.shareutil_see_detail_at
            java.lang.String r5 = r3.getString(r5)
            r11.append(r5)
            java.lang.String r5 = "："
            r11.append(r5)
            java.lang.String r5 = r11.toString()
        L_0x0460:
            r14.a = r5
            r5 = r17
            r10 = r22
        L_0x0466:
            java.lang.String r6 = r26.getName()
            java.lang.String r11 = r26.getPhone()
            java.lang.String r16 = ""
            if (r11 == 0) goto L_0x0493
            java.lang.String r2 = ""
            boolean r2 = r2.equals(r11)
            if (r2 != 0) goto L_0x0493
            java.lang.String r2 = ";"
            int r2 = r11.indexOf(r2)
            if (r2 <= 0) goto L_0x0490
            java.lang.String r2 = ";"
            int r2 = r11.indexOf(r2)
            r23 = r15
            r15 = 0
            java.lang.String r11 = r11.substring(r15, r2)
            goto L_0x0497
        L_0x0490:
            r23 = r15
            goto L_0x0497
        L_0x0493:
            r23 = r15
            r11 = r16
        L_0x0497:
            java.lang.String r2 = ""
            boolean r2 = r2.equals(r11)
            if (r2 == 0) goto L_0x04a4
            java.lang.String r2 = r26.getAddr()
            goto L_0x04ca
        L_0x04a4:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r15 = r26.getAddr()
            r2.append(r15)
            java.lang.String r15 = " \n"
            r2.append(r15)
            int r15 = com.autonavi.minimap.R.string.shareutil_phone
            java.lang.String r15 = r3.getString(r15)
            r2.append(r15)
            java.lang.String r15 = ":"
            r2.append(r15)
            r2.append(r11)
            java.lang.String r2 = r2.toString()
        L_0x04ca:
            int r11 = com.autonavi.minimap.R.drawable.weixin_poi
            android.graphics.Bitmap r11 = defpackage.ahc.a(r1, r11)
            r8.f = r6
            r8.a = r2
            r8.g = r11
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            int r6 = com.autonavi.minimap.R.string.shareutil_share
            java.lang.String r6 = r3.getString(r6)
            r2.append(r6)
            java.lang.String r6 = "："
            r2.append(r6)
            java.lang.String r6 = r26.getName()
            r2.append(r6)
            java.lang.String r6 = "\n"
            r2.append(r6)
            java.lang.String r6 = r26.getAddr()
            r2.append(r6)
            java.lang.String r2 = r2.toString()
            java.lang.String r6 = ""
            int r11 = com.autonavi.minimap.R.drawable.weixin_poi
            android.graphics.Bitmap r11 = defpackage.ahc.a(r1, r11)
            r9.f = r2
            r9.a = r6
            r9.g = r11
            if (r27 == 0) goto L_0x051b
            int r6 = r27.length()
            if (r6 != 0) goto L_0x0518
            goto L_0x051b
        L_0x0518:
            r2 = r27
            goto L_0x0523
        L_0x051b:
            com.amap.bundle.blutils.app.ConfigerHelper r2 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()
            java.lang.String r2 = r2.getShareMsgUrl()
        L_0x0523:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r2)
            r6.append(r10)
            java.lang.String r2 = r6.toString()
            java.lang.String r6 = ""
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r5)
            r10.append(r6)
            java.lang.String r5 = r10.toString()
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 == 0) goto L_0x054f
            int r5 = com.autonavi.minimap.R.string.shareutil_click_for_location
            java.lang.String r5 = r3.getString(r5)
        L_0x054f:
            com.amap.bundle.blutils.app.ConfigerHelper r3 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()
            boolean r3 = r3.isLoadPoiPageFromInternet()
            if (r3 == 0) goto L_0x055c
            java.lang.String r3 = "http://tpl.dev.myamap.com/andh/exData2car.html"
            goto L_0x05ad
        L_0x055c:
            dde r3 = new dde
            r3.<init>()
            r24.getApplicationContext()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            com.autonavi.common.model.GeoPoint r6 = r26.getPoint()
            double r10 = r6.getLongitude()
            r3.append(r10)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            com.autonavi.common.model.GeoPoint r6 = r26.getPoint()
            double r10 = r6.getLatitude()
            r3.append(r10)
            r26.getName()
            r26.getAddr()
            java.lang.String r3 = r26.getId()
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0597
            r26.getId()
        L_0x0597:
            java.lang.String r3 = ""
            esb r6 = defpackage.esb.a.a
            java.lang.Class<bgx> r10 = defpackage.bgx.class
            esc r6 = r6.a(r10)
            bgx r6 = (defpackage.bgx) r6
            if (r6 == 0) goto L_0x05ad
            java.lang.String r3 = "shareToCar/index.html"
            java.lang.String r3 = r6.getUrl(r3)
        L_0x05ad:
            int r6 = com.autonavi.minimap.R.string.fav_send_to_car
            java.lang.String r6 = r1.getString(r6)
            r13.e = r6
            r13.b = r3
            r3 = 0
            r13.c = r3
            r14.b = r2
            r8.b = r2
            r8.e = r3
            r9.b = r2
            r9.e = r3
            r7.b = r2
            r7.a = r5
            com.autonavi.common.model.GeoPoint r3 = r26.getPoint()
            if (r3 == 0) goto L_0x05de
            com.autonavi.common.model.GeoPoint r3 = r26.getPoint()
            int r3 = r3.x
            r7.e = r3
            com.autonavi.common.model.GeoPoint r3 = r26.getPoint()
            int r3 = r3.y
            r7.f = r3
        L_0x05de:
            java.lang.String r3 = r8.f
            r4 = r18
            r4.e = r3
            java.lang.String r3 = r8.a
            r4.a = r3
            int r3 = com.autonavi.minimap.R.drawable.weixin_poi
            android.graphics.Bitmap r3 = defpackage.ahc.a(r1, r3)
            r4.g = r3
            r4.b = r2
            java.lang.String r3 = r8.f
            r12.e = r3
            java.lang.String r3 = r8.a
            r12.a = r3
            int r3 = com.autonavi.minimap.R.drawable.weixin_poi
            android.graphics.Bitmap r3 = defpackage.ahc.a(r1, r3)
            r12.g = r3
            r12.b = r2
            java.lang.String r3 = r8.f
            r5 = r23
            r5.f = r3
            java.lang.String r3 = r8.a
            r5.a = r3
            int r3 = com.autonavi.minimap.R.drawable.weixin_poi
            android.graphics.Bitmap r1 = defpackage.ahc.a(r1, r3)
            r5.g = r1
            r5.b = r2
            esb r1 = defpackage.esb.a.a
            java.lang.Class<dcb> r2 = defpackage.dcb.class
            esc r1 = r1.a(r2)
            dcb r1 = (defpackage.dcb) r1
            if (r1 == 0) goto L_0x0636
            dcw$1 r2 = new dcw$1
            r6 = r2
            r10 = r4
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r5
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14)
            r3 = r25
            r1.a(r3, r2)
        L_0x0636:
            return
        L_0x0637:
            int r1 = com.autonavi.minimap.R.string.error_share_failure
            java.lang.String r1 = r3.getString(r1)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dcw.a(android.content.Context, dct, com.autonavi.common.model.POI, java.lang.String):void");
    }
}
