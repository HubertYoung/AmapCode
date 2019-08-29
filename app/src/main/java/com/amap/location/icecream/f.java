package com.amap.location.icecream;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.location.common.f.e;
import com.amap.location.icecream.a.a;
import com.amap.location.icecream.a.c;
import com.amap.location.uptunnel.UpTunnel;
import com.autonavi.common.SuperId;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.json.JSONObject;

/* compiled from: IcecreamFilter */
public class f {
    private static final HashMap<String, h> a = new HashMap<>();

    protected static void a(Context context, JSONObject jSONObject, List<h> list, List<h> list2) {
        HashMap<String, h> a2 = a(context, jSONObject);
        synchronized (a) {
            a.clear();
            for (h next : a2.values()) {
                if (next != null) {
                    a.put(next.a.split("_")[0], next);
                }
            }
        }
        a(context, a2, list, list2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001d, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static java.lang.String a(java.lang.String r2) {
        /*
            java.util.HashMap<java.lang.String, com.amap.location.icecream.h> r0 = a
            monitor-enter(r0)
            if (r2 == 0) goto L_0x001b
            java.util.HashMap<java.lang.String, com.amap.location.icecream.h> r1 = a     // Catch:{ all -> 0x0019 }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x0019 }
            if (r1 == 0) goto L_0x001b
            java.util.HashMap<java.lang.String, com.amap.location.icecream.h> r1 = a     // Catch:{ all -> 0x0019 }
            java.lang.Object r2 = r1.get(r2)     // Catch:{ all -> 0x0019 }
            com.amap.location.icecream.h r2 = (com.amap.location.icecream.h) r2     // Catch:{ all -> 0x0019 }
            java.lang.String r2 = r2.c     // Catch:{ all -> 0x0019 }
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            return r2
        L_0x0019:
            r2 = move-exception
            goto L_0x001e
        L_0x001b:
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            r2 = 0
            return r2
        L_0x001e:
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.icecream.f.a(java.lang.String):java.lang.String");
    }

    private static void a(Context context, HashMap<String, h> hashMap, List<h> list, List<h> list2) {
        File[] listFiles;
        try {
            File file = new File(a.a(context));
            if ((file.exists() && file.isDirectory()) || file.mkdirs()) {
                for (File file2 : file.listFiles()) {
                    String name = file2.getName();
                    if (hashMap.containsKey(name)) {
                        h hVar = hashMap.get(name);
                        if (a.a(file2, hVar.c)) {
                            list.add(hVar);
                            hashMap.remove(name);
                        } else {
                            e.b(file2);
                        }
                    } else {
                        e.b(file2);
                    }
                }
                for (Entry next : hashMap.entrySet()) {
                    if (next != null) {
                        h hVar2 = (h) next.getValue();
                        if (hVar2 != null) {
                            list2.add(hVar2);
                        }
                    }
                }
            }
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
        }
    }

    private static HashMap<String, h> a(Context context, JSONObject jSONObject) {
        String a2 = c.a(context);
        c.b(context);
        HashMap<String, h> hashMap = new HashMap<>();
        if (jSONObject != null) {
            try {
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    if (!TextUtils.isEmpty(next) && next.startsWith("list_")) {
                        JSONObject optJSONObject = jSONObject.optJSONObject(next);
                        if (optJSONObject != null) {
                            String optString = optJSONObject.optString(SuperId.BIT_1_MAIN_BUSSTATION, "");
                            String optString2 = optJSONObject.optString("md");
                            String optString3 = optJSONObject.optString("url");
                            String optString4 = optJSONObject.optString("mc");
                            boolean optBoolean = optJSONObject.optBoolean(H5Param.SHOW_OPTION_MENU, false);
                            int optInt = optJSONObject.optInt("v");
                            if (optInt <= 5 && optString.endsWith(".apk")) {
                                String str = optString.split("_")[0];
                                StringBuilder sb = new StringBuilder();
                                sb.append(str);
                                sb.append("_");
                                sb.append(optString2);
                                String sb2 = sb.toString();
                                if (a2.contains(sb2)) {
                                    UpTunnel.reportEvent(100071, sb2.getBytes());
                                    c.a(context, str, optString2);
                                } else {
                                    hashMap.put(optString, a(optString, optString2, optString3, optString4, optInt, optBoolean));
                                }
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }
        return hashMap;
    }

    private static h a(String str, String str2, String str3, String str4, int i, boolean z) {
        h hVar = new h();
        hVar.a = str;
        hVar.c = str2;
        hVar.b = str3;
        hVar.d = str4;
        hVar.e = i;
        hVar.f = z;
        return hVar;
    }
}
