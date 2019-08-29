package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import java.util.HashMap;
import java.util.List;

/* renamed from: daw reason: default package */
/* compiled from: MessageBoxSortUtil */
public final class daw {
    public static int a;
    public static int b;

    public static void a(List<AmapMessage> list) {
        a = 0;
        b = 0;
        HashMap hashMap = new HashMap();
        AMapAppGlobal.getApplication();
        List<btb> d = dbc.b().d();
        for (int i = 0; i < d.size(); i++) {
            btb btb = d.get(i);
            if ("1".equals(btb.f)) {
                hashMap.put(btb.a, btb.a);
            }
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            AmapMessage amapMessage = list.get(i2);
            if (!amapMessage.isADMsg()) {
                if (hashMap.get(amapMessage.category) != null) {
                    a++;
                } else {
                    b++;
                }
            }
        }
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        mapSharePreference.putIntValue("myMsgUnreadCount", mapSharePreference.getIntValue("myMsgUnreadCount", 0) + a);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:0|1|2|3|4|(2:7|5)|18|8|(5:9|10|(2:13|11)|19|14)|15|17) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0026 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0072 */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x007d A[Catch:{ Exception -> 0x00fa }, LOOP:1: B:11:0x0077->B:13:0x007d, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0032 A[Catch:{ Exception -> 0x0072 }, LOOP:0: B:5:0x002c->B:7:0x0032, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.util.List<com.autonavi.minimap.bundle.msgbox.entity.AmapMessageModel> r9, java.util.List<defpackage.btb> r10) {
        /*
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r1 = 0
            com.amap.bundle.mapstorage.MapSharePreference r2 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ Exception -> 0x0026 }
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r3 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ Exception -> 0x0026 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0026 }
            java.lang.String r3 = "myMsgUnreadCount"
            int r2 = r2.getIntValue(r3, r1)     // Catch:{ Exception -> 0x0026 }
            java.lang.String r3 = "myMsgUnreadCount"
            r0.put(r3, r2)     // Catch:{ Exception -> 0x0026 }
            java.lang.String r2 = "myMsgUpdateCount"
            int r3 = a     // Catch:{ Exception -> 0x0026 }
            r0.put(r2, r3)     // Catch:{ Exception -> 0x0026 }
            java.lang.String r2 = "infoUpdateCount"
            int r3 = b     // Catch:{ Exception -> 0x0026 }
            r0.put(r2, r3)     // Catch:{ Exception -> 0x0026 }
        L_0x0026:
            org.json.JSONArray r2 = new org.json.JSONArray     // Catch:{ Exception -> 0x0072 }
            r2.<init>()     // Catch:{ Exception -> 0x0072 }
            r3 = 0
        L_0x002c:
            int r4 = r10.size()     // Catch:{ Exception -> 0x0072 }
            if (r3 >= r4) goto L_0x006d
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x0072 }
            r4.<init>()     // Catch:{ Exception -> 0x0072 }
            java.lang.Object r5 = r10.get(r3)     // Catch:{ Exception -> 0x0072 }
            btb r5 = (defpackage.btb) r5     // Catch:{ Exception -> 0x0072 }
            java.lang.String r6 = "id"
            java.lang.String r7 = r5.a     // Catch:{ Exception -> 0x0072 }
            r4.put(r6, r7)     // Catch:{ Exception -> 0x0072 }
            java.lang.String r6 = "name"
            java.lang.String r7 = r5.b     // Catch:{ Exception -> 0x0072 }
            r4.put(r6, r7)     // Catch:{ Exception -> 0x0072 }
            java.lang.String r6 = "pattern"
            java.lang.String r7 = r5.c     // Catch:{ Exception -> 0x0072 }
            r4.put(r6, r7)     // Catch:{ Exception -> 0x0072 }
            java.lang.String r6 = "icon"
            java.lang.String r7 = r5.d     // Catch:{ Exception -> 0x0072 }
            r4.put(r6, r7)     // Catch:{ Exception -> 0x0072 }
            java.lang.String r6 = "top_image"
            java.lang.String r7 = r5.e     // Catch:{ Exception -> 0x0072 }
            r4.put(r6, r7)     // Catch:{ Exception -> 0x0072 }
            java.lang.String r6 = "section_type"
            java.lang.String r5 = r5.f     // Catch:{ Exception -> 0x0072 }
            r4.put(r6, r5)     // Catch:{ Exception -> 0x0072 }
            r2.put(r4)     // Catch:{ Exception -> 0x0072 }
            int r3 = r3 + 1
            goto L_0x002c
        L_0x006d:
            java.lang.String r10 = "categoryConfig"
            r0.put(r10, r2)     // Catch:{ Exception -> 0x0072 }
        L_0x0072:
            org.json.JSONArray r10 = new org.json.JSONArray     // Catch:{ Exception -> 0x00fa }
            r10.<init>()     // Catch:{ Exception -> 0x00fa }
        L_0x0077:
            int r2 = r9.size()     // Catch:{ Exception -> 0x00fa }
            if (r1 >= r2) goto L_0x00f5
            java.lang.Object r2 = r9.get(r1)     // Catch:{ Exception -> 0x00fa }
            com.autonavi.minimap.bundle.msgbox.entity.AmapMessageModel r2 = (com.autonavi.minimap.bundle.msgbox.entity.AmapMessageModel) r2     // Catch:{ Exception -> 0x00fa }
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x00fa }
            r3.<init>()     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "id"
            java.lang.String r5 = r2.id     // Catch:{ Exception -> 0x00fa }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "title"
            java.lang.String r5 = r2.descMessage     // Catch:{ Exception -> 0x00fa }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "categoryId"
            java.lang.String r5 = r2.category     // Catch:{ Exception -> 0x00fa }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "msgType"
            int r5 = r2.msgType     // Catch:{ Exception -> 0x00fa }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "read"
            boolean r5 = r2.isUnRead     // Catch:{ Exception -> 0x00fa }
            r5 = r5 ^ 1
            r3.put(r4, r5)     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "scheme"
            java.lang.String r5 = r2.actionUri     // Catch:{ Exception -> 0x00fa }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "label"
            java.lang.String r5 = r2.label     // Catch:{ Exception -> 0x00fa }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "labelColor"
            java.lang.String r5 = r2.labelColor     // Catch:{ Exception -> 0x00fa }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "imgUrl"
            java.lang.String r5 = r2.msgImgUri     // Catch:{ Exception -> 0x00fa }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "imagUrl_v2"
            java.lang.String r5 = r2.msgImgUriV2     // Catch:{ Exception -> 0x00fa }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "showType"
            int r5 = r2.showType     // Catch:{ Exception -> 0x00fa }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "time"
            long r5 = r2.createdTime     // Catch:{ Exception -> 0x00fa }
            r7 = 1000(0x3e8, double:4.94E-321)
            long r5 = r5 / r7
            r3.put(r4, r5)     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "timestamp"
            long r5 = r2.updateTime     // Catch:{ Exception -> 0x00fa }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x00fa }
            java.lang.String r4 = "impression"
            java.lang.String r2 = r2.impression     // Catch:{ Exception -> 0x00fa }
            r3.put(r4, r2)     // Catch:{ Exception -> 0x00fa }
            r10.put(r3)     // Catch:{ Exception -> 0x00fa }
            int r1 = r1 + 1
            goto L_0x0077
        L_0x00f5:
            java.lang.String r9 = "msgList"
            r0.put(r9, r10)     // Catch:{ Exception -> 0x00fa }
        L_0x00fa:
            java.lang.String r9 = "-----xing---->ajxJson"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r1 = "ajxJson===>"
            r10.<init>(r1)
            java.lang.String r1 = r0.toString()
            r10.append(r1)
            java.lang.String r10 = r10.toString()
            com.amap.bundle.logs.AMapLog.i(r9, r10)
            java.lang.String r9 = r0.toString()
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.daw.a(java.util.List, java.util.List):java.lang.String");
    }
}
