package com.amap.location.uptunnel.core.b;

import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.amap.location.common.e.b;
import com.amap.location.common.e.c;
import com.amap.location.common.f.f;
import com.amap.location.security.Core;
import java.util.HashMap;

/* compiled from: NetHelper */
public class a {
    public static boolean a(c cVar, String str, byte[] bArr, int i) {
        try {
            byte[] a = f.a(bArr);
            if (a != null) {
                if (a.length != 0) {
                    byte[] xxt = Core.xxt(a, 1);
                    if (xxt != null) {
                        if (xxt.length != 0) {
                            if (cVar != null) {
                                HashMap hashMap = new HashMap();
                                hashMap.put(ProcessInfo.ALIAS_EXT, "120");
                                com.amap.location.common.e.a aVar = new com.amap.location.common.e.a();
                                aVar.a = str;
                                aVar.b = hashMap;
                                aVar.c = xxt;
                                aVar.d = i;
                                b a2 = cVar.a(aVar);
                                if (a2 != null) {
                                    if (a2.c != null) {
                                        int i2 = a2.a;
                                        String str2 = new String(a2.c, "UTF-8");
                                        if (i2 != 200 || !"true".equals(str2)) {
                                            return false;
                                        }
                                        return true;
                                    }
                                }
                                com.amap.location.common.d.a.c("upnethelper", "httpPost-网络返回结果为 null");
                                return false;
                            }
                            com.amap.location.common.d.a.d("upnethelper", "http client is null");
                            return false;
                        }
                    }
                    com.amap.location.common.d.a.c("upnethelper", "xxt is null");
                    return false;
                }
            }
            com.amap.location.common.d.a.c("upnethelper", "gzip is null");
            return false;
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
            return false;
        }
    }
}
