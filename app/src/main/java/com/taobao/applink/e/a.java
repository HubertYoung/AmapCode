package com.taobao.applink.e;

import com.taobao.applink.param.TBAuthParam;
import com.taobao.applink.param.TBDetailParam;
import com.taobao.applink.param.TBNavParam;
import com.taobao.applink.param.TBShopParam;
import com.taobao.applink.param.TBURIParam;
import java.util.HashMap;
import java.util.Map;

public class a {
    private static Map a;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put("jumpShop", TBShopParam.class);
        a.put("jumpDetail", TBDetailParam.class);
        a.put("jumpH5", TBURIParam.class);
        a.put("doAuth", TBAuthParam.class);
        a.put("jumpNav", TBNavParam.class);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.taobao.applink.param.TBBaseParam a(java.lang.String r4) {
        /*
            r0 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0032 }
            r1.<init>(r4)     // Catch:{ JSONException -> 0x0032 }
            java.lang.String r4 = "method"
            java.lang.String r4 = r1.getString(r4)     // Catch:{ JSONException -> 0x0032 }
            java.util.Map r2 = a
            java.lang.Object r4 = r2.get(r4)
            java.lang.Class r4 = (java.lang.Class) r4
            r2 = 0
            java.lang.Class[] r3 = new java.lang.Class[r2]     // Catch:{  }
            java.lang.reflect.Constructor r4 = r4.getDeclaredConstructor(r3)     // Catch:{  }
            r3 = 1
            r4.setAccessible(r3)     // Catch:{  }
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{  }
            java.lang.Object r4 = r4.newInstance(r2)     // Catch:{  }
            com.taobao.applink.param.TBBaseParam r4 = (com.taobao.applink.param.TBBaseParam) r4     // Catch:{  }
            boolean r2 = r4.checkParams(r1)     // Catch:{  }
            if (r2 != 0) goto L_0x002e
            return r0
        L_0x002e:
            r4.setParams(r1)     // Catch:{  }
            return r4
        L_0x0032:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.applink.e.a.a(java.lang.String):com.taobao.applink.param.TBBaseParam");
    }
}
