package defpackage;

/* renamed from: dmx reason: default package */
/* compiled from: OpenDatePickerAction */
public class dmx extends vz {
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00c9  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00cc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(org.json.JSONObject r29, defpackage.wa r30) {
        /*
            r28 = this;
            r1 = r28
            r2 = r29
            com.amap.bundle.jsadapter.JsAdapter r3 = r28.a()
            if (r3 != 0) goto L_0x000b
            return
        L_0x000b:
            if (r2 != 0) goto L_0x000e
            return
        L_0x000e:
            r5 = 0
            java.lang.String r9 = "headerText"
            java.lang.String r9 = r2.optString(r9)     // Catch:{ JSONException -> 0x00b0 }
            java.lang.String r10 = "selectTime"
            long r10 = r2.optLong(r10)     // Catch:{ JSONException -> 0x00a9 }
            java.lang.String r12 = "minDate"
            long r12 = r2.optLong(r12)     // Catch:{ JSONException -> 0x00a5 }
            java.lang.String r14 = "maxDate"
            long r14 = r2.optLong(r14)     // Catch:{ JSONException -> 0x00a1 }
            java.lang.String r5 = "interval"
            int r5 = r2.optInt(r5)     // Catch:{ JSONException -> 0x009e }
            java.lang.String r6 = "inValidHour"
            boolean r6 = r2.has(r6)     // Catch:{ JSONException -> 0x009b }
            r4 = 24
            if (r6 == 0) goto L_0x007e
            java.lang.String r6 = "inValidHour"
            org.json.JSONArray r2 = r2.getJSONArray(r6)     // Catch:{ JSONException -> 0x009b }
            int r6 = r2.length()     // Catch:{ JSONException -> 0x009b }
            if (r6 <= 0) goto L_0x007b
            int r6 = r2.length()     // Catch:{ JSONException -> 0x009b }
            int r6 = 24 - r6
            int[] r6 = new int[r6]     // Catch:{ JSONException -> 0x009b }
            r7 = 0
            r16 = 0
        L_0x004f:
            if (r7 >= r4) goto L_0x0078
            r8 = 0
            r17 = 0
        L_0x0054:
            int r4 = r2.length()     // Catch:{ JSONException -> 0x0072 }
            if (r8 >= r4) goto L_0x0065
            int r4 = r2.getInt(r8)     // Catch:{ JSONException -> 0x0072 }
            if (r7 != r4) goto L_0x0062
            r17 = 1
        L_0x0062:
            int r8 = r8 + 1
            goto L_0x0054
        L_0x0065:
            if (r17 != 0) goto L_0x006d
            int r4 = r16 + 1
            r6[r16] = r7     // Catch:{ JSONException -> 0x0072 }
            r16 = r4
        L_0x006d:
            int r7 = r7 + 1
            r4 = 24
            goto L_0x004f
        L_0x0072:
            r0 = move-exception
            r2 = r0
            r16 = r6
            goto L_0x00b9
        L_0x0078:
            r16 = r6
            goto L_0x00bc
        L_0x007b:
            r16 = 0
            goto L_0x00bc
        L_0x007e:
            r2 = 24
            int[] r4 = new int[r2]     // Catch:{ JSONException -> 0x009b }
            r6 = 0
        L_0x0083:
            if (r6 >= r2) goto L_0x008f
            r4[r6] = r6     // Catch:{ JSONException -> 0x008a }
            int r6 = r6 + 1
            goto L_0x0083
        L_0x008a:
            r0 = move-exception
            r2 = r0
            r16 = r4
            goto L_0x00b9
        L_0x008f:
            r27 = r4
            r8 = r5
            r19 = r9
            r20 = r10
            r22 = r12
            r24 = r14
            goto L_0x00c7
        L_0x009b:
            r0 = move-exception
            r2 = r0
            goto L_0x00b7
        L_0x009e:
            r0 = move-exception
            r2 = r0
            goto L_0x00ae
        L_0x00a1:
            r0 = move-exception
            r2 = r0
            r14 = r5
            goto L_0x00ae
        L_0x00a5:
            r0 = move-exception
            r2 = r0
            r12 = r5
            goto L_0x00ad
        L_0x00a9:
            r0 = move-exception
            r2 = r0
            r10 = r5
            r12 = r10
        L_0x00ad:
            r14 = r12
        L_0x00ae:
            r5 = 0
            goto L_0x00b7
        L_0x00b0:
            r0 = move-exception
            r2 = r0
            r10 = r5
            r12 = r10
            r14 = r12
            r5 = 0
            r9 = 0
        L_0x00b7:
            r16 = 0
        L_0x00b9:
            r2.printStackTrace()
        L_0x00bc:
            r8 = r5
            r19 = r9
            r20 = r10
            r22 = r12
            r24 = r14
            r27 = r16
        L_0x00c7:
            if (r8 != 0) goto L_0x00cc
            r26 = 1
            goto L_0x00ce
        L_0x00cc:
            r26 = r8
        L_0x00ce:
            com.autonavi.map.widget.DatePickerDialog r2 = new com.autonavi.map.widget.DatePickerDialog
            bid r4 = r3.mPageContext
            android.app.Activity r18 = r4.getActivity()
            r17 = r2
            r17.<init>(r18, r19, r20, r22, r24, r26, r27)
            r4 = 1
            r2.setCancelable(r4)
            r2.show()
            dmx$1 r4 = new dmx$1
            r5 = r30
            r4.<init>(r2, r5, r3)
            r2.setPosOnClickListener(r4)
            dmx$2 r3 = new dmx$2
            r3.<init>(r2)
            r2.setNegOnClickListener(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dmx.a(org.json.JSONObject, wa):void");
    }
}
