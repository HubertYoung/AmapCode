package defpackage;

import android.support.annotation.NonNull;
import com.autonavi.bundle.smart.scenic.page.SerachSmartScenicSetMapPage;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;

/* renamed from: bdh reason: default package */
/* compiled from: SearchSmartScenicSetMapPresenter */
public final class bdh extends Ajx3PagePresenter {
    @NonNull
    private SerachSmartScenicSetMapPage a;

    public bdh(@NonNull SerachSmartScenicSetMapPage serachSmartScenicSetMapPage) {
        super(serachSmartScenicSetMapPage);
        this.a = serachSmartScenicSetMapPage;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:50:0x014e, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        r1.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x017f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0184, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0185, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0184 A[ExcHandler: NumberFormatException (r0v3 'e' java.lang.NumberFormatException A[CUSTOM_DECLARE]), Splitter:B:20:0x0050] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onLabelClick(java.util.List<defpackage.als> r14) {
        /*
            r13 = this;
            if (r14 == 0) goto L_0x019a
            boolean r0 = r14.isEmpty()
            if (r0 == 0) goto L_0x000a
            goto L_0x019a
        L_0x000a:
            r0 = 0
            java.lang.Object r1 = r14.get(r0)
            als r1 = (defpackage.als) r1
            if (r1 != 0) goto L_0x0018
            boolean r14 = super.onLabelClick(r14)
            return r14
        L_0x0018:
            com.autonavi.bundle.smart.scenic.page.SerachSmartScenicSetMapPage r2 = r13.a
            je r3 = r2.b
            r4 = 0
            if (r3 == 0) goto L_0x0192
            je r3 = r2.b
            boolean r3 = r3.a(r1)
            if (r3 != 0) goto L_0x0029
            goto L_0x0192
        L_0x0029:
            r2.a()
            bdi r3 = r2.a
            if (r3 == 0) goto L_0x0195
            java.lang.String r3 = r1.l
            boolean r3 = defpackage.bdi.a(r3)
            r5 = 1
            if (r3 == 0) goto L_0x018e
            r2.a()
            bdi r3 = r2.a
            if (r3 == 0) goto L_0x0195
            java.lang.String r3 = r1.l
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0195
            r2.a(r0, r4)
            bdi r2 = r2.a
            java.lang.String r1 = r1.l
            r3 = 0
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r6.<init>(r1)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            java.lang.String r1 = "center"
            java.lang.String r1 = r6.optString(r1)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            java.lang.String r7 = "scale"
            java.lang.String r7 = r6.optString(r7)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            java.lang.String r8 = "geoobj"
            java.lang.String r6 = r6.optString(r8)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            boolean r8 = android.text.TextUtils.isEmpty(r1)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r9 = 2
            if (r8 != 0) goto L_0x0094
            java.lang.String r6 = ","
            java.lang.String[] r1 = r1.split(r6)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            if (r1 == 0) goto L_0x011e
            int r6 = r1.length     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            if (r6 != r9) goto L_0x011e
            com.amap.bundle.datamodel.point.GeoPointHD r4 = new com.amap.bundle.datamodel.point.GeoPointHD     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r3 = r1[r0]     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            double r8 = defpackage.bby.b(r3)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r1 = r1[r5]     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            double r10 = defpackage.bby.b(r1)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r4.<init>(r8, r10)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            java.lang.Float r1 = java.lang.Float.valueOf(r7)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            float r3 = r1.floatValue()     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            goto L_0x011e
        L_0x0094:
            boolean r1 = android.text.TextUtils.isEmpty(r6)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            if (r1 != 0) goto L_0x011e
            java.lang.String r1 = "\\|"
            java.lang.String[] r1 = r6.split(r1)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            if (r1 == 0) goto L_0x011e
            int r6 = r1.length     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            if (r6 != r9) goto L_0x011e
            r6 = r1[r0]     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            java.lang.String r8 = ","
            java.lang.String[] r6 = r6.split(r8)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r1 = r1[r5]     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            java.lang.String r8 = ","
            java.lang.String[] r1 = r1.split(r8)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            if (r6 == 0) goto L_0x011e
            if (r1 == 0) goto L_0x011e
            int r8 = r6.length     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            if (r8 != r9) goto L_0x011e
            int r8 = r1.length     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            if (r8 != r9) goto L_0x011e
            com.amap.bundle.datamodel.point.GeoPointHD r3 = new com.amap.bundle.datamodel.point.GeoPointHD     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r4 = r6[r0]     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            double r8 = defpackage.bby.b(r4)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r4 = r6[r5]     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            double r10 = defpackage.bby.b(r4)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r3.<init>(r8, r10)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            com.amap.bundle.datamodel.point.GeoPointHD r4 = new com.amap.bundle.datamodel.point.GeoPointHD     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r6 = r1[r0]     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            double r8 = defpackage.bby.b(r6)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r1 = r1[r5]     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            double r10 = defpackage.bby.b(r1)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r4.<init>(r8, r10)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            android.graphics.Rect r1 = new android.graphics.Rect     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r1.<init>()     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r6 = r3.x     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r1.left = r6     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r6 = r3.y     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r1.top = r6     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r6 = r4.x     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r1.right = r6     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r6 = r4.y     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r1.bottom = r6     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            bty r6 = r2.a     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r8 = r3.x     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r3 = r3.y     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r9 = r4.x     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r4 = r4.y     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            float r3 = r6.a(r8, r3, r9, r4)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            java.lang.Float r4 = java.lang.Float.valueOf(r7)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            float r4 = r4.floatValue()     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r6 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r6 >= 0) goto L_0x0111
            r3 = r4
        L_0x0111:
            com.amap.bundle.datamodel.point.GeoPointHD r4 = new com.amap.bundle.datamodel.point.GeoPointHD     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r6 = r1.centerX()     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r1 = r1.centerY()     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r4.<init>(r6, r1)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
        L_0x011e:
            if (r4 == 0) goto L_0x0195
            int r11 = r4.x     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r12 = r4.y     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            bty r6 = r2.a     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r7 = 500(0x1f4, float:7.0E-43)
            bty r1 = r2.a     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            float r1 = r1.I()     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r9 = (int) r1     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            bty r1 = r2.a     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            float r1 = r1.J()     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r10 = (int) r1     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r8 = r3
            r6.a(r7, r8, r9, r10, r11, r12)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            int r1 = r4.getAdCode()     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r6.<init>()     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            java.lang.String r7 = "action_type"
            r6.put(r7, r5)     // Catch:{ JSONException -> 0x014e, NumberFormatException -> 0x0184 }
            java.lang.String r7 = "adcode"
            r6.put(r7, r1)     // Catch:{ JSONException -> 0x014e, NumberFormatException -> 0x0184 }
            goto L_0x0152
        L_0x014e:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
        L_0x0152:
            com.autonavi.minimap.ajx3.core.JsFunctionCallback r1 = r2.b     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            if (r1 == 0) goto L_0x0163
            com.autonavi.minimap.ajx3.core.JsFunctionCallback r1 = r2.b     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            java.lang.Object[] r2 = new java.lang.Object[r5]     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            java.lang.String r5 = r6.toString()     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r2[r0] = r5     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            r1.callback(r2)     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
        L_0x0163:
            int r0 = r4.getAdCode()     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x017f, NumberFormatException -> 0x0184 }
            r1.<init>()     // Catch:{ JSONException -> 0x017f, NumberFormatException -> 0x0184 }
            java.lang.String r2 = "adcode"
            r1.put(r2, r0)     // Catch:{ JSONException -> 0x017f, NumberFormatException -> 0x0184 }
            java.lang.String r0 = "index"
            double r2 = (double) r3     // Catch:{ JSONException -> 0x017f, NumberFormatException -> 0x0184 }
            r1.put(r0, r2)     // Catch:{ JSONException -> 0x017f, NumberFormatException -> 0x0184 }
            java.lang.String r0 = "P00399"
            java.lang.String r2 = "B005"
            com.amap.bundle.statistics.util.LogUtil.actionLogV2(r0, r2, r1)     // Catch:{ JSONException -> 0x017f, NumberFormatException -> 0x0184 }
            goto L_0x0195
        L_0x017f:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ JSONException -> 0x0189, NumberFormatException -> 0x0184 }
            goto L_0x0195
        L_0x0184:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0195
        L_0x0189:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0195
        L_0x018e:
            r2.a(r5, r1)
            goto L_0x0195
        L_0x0192:
            r2.a(r0, r4)
        L_0x0195:
            boolean r14 = super.onLabelClick(r14)
            return r14
        L_0x019a:
            boolean r14 = super.onLabelClick(r14)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bdh.onLabelClick(java.util.List):boolean");
    }

    public final boolean onBlankClick() {
        this.a.a(false, null);
        return super.onBlankClick();
    }
}
