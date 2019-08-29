package com.alibaba.baichuan.android.trade.utils.c;

import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alipay.multimedia.js.video.H5VideoUploadPlugin.UploadVideoParams;
import java.util.HashMap;
import java.util.Map;

public class a {
    private static final Map a = new HashMap();
    private static final Map b = new HashMap();
    private static final Map c = new HashMap();
    private static final Map d = new HashMap();
    private static final String e = "a";

    static {
        a.put(UploadVideoParams.TYPE_SHORT, Short.TYPE);
        a.put("int", Integer.TYPE);
        a.put("long", Long.TYPE);
        a.put("double", Double.TYPE);
        a.put("float", Float.TYPE);
        a.put("char", Character.TYPE);
    }

    public static Object a(Class cls, Class[] clsArr, Object[] objArr) {
        if (clsArr != null) {
            try {
                if (clsArr.length != 0) {
                    return cls.getConstructor(clsArr).newInstance(objArr);
                }
            } catch (Exception e2) {
                String str = e;
                StringBuilder sb = new StringBuilder("Fail to create the instance of type ");
                sb.append(cls != null ? cls.getName() : null);
                sb.append(", the error is ");
                sb.append(e2.getMessage());
                AlibcLogger.e(str, sb.toString());
                return null;
            }
        }
        return cls.newInstance();
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x005b A[Catch:{ Exception -> 0x006a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object a(java.lang.String r6, java.lang.String r7, java.lang.String[] r8, java.lang.Object r9, java.lang.Object[] r10) {
        /*
            java.util.Map r0 = b     // Catch:{ Exception -> 0x006a }
            java.lang.Object r0 = r0.get(r6)     // Catch:{ Exception -> 0x006a }
            java.lang.Class r0 = (java.lang.Class) r0     // Catch:{ Exception -> 0x006a }
            if (r0 != 0) goto L_0x0013
            java.lang.Class r0 = java.lang.Class.forName(r6)     // Catch:{ Exception -> 0x006a }
            java.util.Map r1 = b     // Catch:{ Exception -> 0x006a }
            r1.put(r6, r0)     // Catch:{ Exception -> 0x006a }
        L_0x0013:
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x006a }
            r1.<init>()     // Catch:{ Exception -> 0x006a }
            r2 = 0
            if (r8 == 0) goto L_0x0027
            int r3 = r8.length     // Catch:{ Exception -> 0x006a }
            r4 = 0
        L_0x001d:
            if (r4 >= r3) goto L_0x0027
            r5 = r8[r4]     // Catch:{ Exception -> 0x006a }
            r1.append(r5)     // Catch:{ Exception -> 0x006a }
            int r4 = r4 + 1
            goto L_0x001d
        L_0x0027:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006a }
            r3.<init>()     // Catch:{ Exception -> 0x006a }
            r3.append(r6)     // Catch:{ Exception -> 0x006a }
            r3.append(r7)     // Catch:{ Exception -> 0x006a }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x006a }
            r3.append(r1)     // Catch:{ Exception -> 0x006a }
            java.lang.String r1 = r3.toString()     // Catch:{ Exception -> 0x006a }
            java.util.Map r3 = c     // Catch:{ Exception -> 0x006a }
            java.lang.Object r3 = r3.get(r1)     // Catch:{ Exception -> 0x006a }
            java.lang.reflect.Method r3 = (java.lang.reflect.Method) r3     // Catch:{ Exception -> 0x006a }
            if (r3 != 0) goto L_0x0065
            if (r8 == 0) goto L_0x0056
            int r3 = r8.length     // Catch:{ Exception -> 0x006a }
            if (r3 != 0) goto L_0x004d
            goto L_0x0056
        L_0x004d:
            java.lang.Class[] r8 = a(r8)     // Catch:{ Exception -> 0x006a }
        L_0x0051:
            java.lang.reflect.Method r8 = r0.getMethod(r7, r8)     // Catch:{ Exception -> 0x006a }
            goto L_0x0059
        L_0x0056:
            java.lang.Class[] r8 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x006a }
            goto L_0x0051
        L_0x0059:
            if (r8 == 0) goto L_0x0092
            java.util.Map r0 = c     // Catch:{ Exception -> 0x006a }
            r0.put(r1, r8)     // Catch:{ Exception -> 0x006a }
            java.lang.Object r8 = r8.invoke(r9, r10)     // Catch:{ Exception -> 0x006a }
            return r8
        L_0x0065:
            java.lang.Object r8 = r3.invoke(r9, r10)     // Catch:{ Exception -> 0x006a }
            return r8
        L_0x006a:
            r8 = move-exception
            java.lang.String r9 = e
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r0 = "Fail to invoke the "
            r10.<init>(r0)
            r10.append(r6)
            java.lang.String r6 = "."
            r10.append(r6)
            r10.append(r7)
            java.lang.String r6 = ", the error is "
            r10.append(r6)
            java.lang.String r6 = r8.getMessage()
            r10.append(r6)
            java.lang.String r6 = r10.toString()
            com.alibaba.baichuan.android.trade.utils.log.AlibcLogger.e(r9, r6)
        L_0x0092:
            r6 = 0
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.trade.utils.c.a.a(java.lang.String, java.lang.String, java.lang.String[], java.lang.Object, java.lang.Object[]):java.lang.Object");
    }

    public static Object a(String str, String[] strArr, Object[] objArr) {
        try {
            return a((Class) Class.forName(str), a(strArr), objArr);
        } catch (RuntimeException e2) {
            throw e2;
        } catch (Exception e3) {
            String str2 = e;
            StringBuilder sb = new StringBuilder("Fail to create the instance of type ");
            sb.append(str);
            sb.append(", the error is ");
            sb.append(e3.getMessage());
            AlibcLogger.e(str2, sb.toString());
            return null;
        }
    }

    public static Class[] a(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        Class[] clsArr = new Class[strArr.length];
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            if (str.length() < 7) {
                clsArr[i] = (Class) a.get(str);
            }
            if (clsArr[i] == null) {
                if ("boolean".equals(str)) {
                    clsArr[i] = Boolean.TYPE;
                } else {
                    clsArr[i] = Class.forName(str);
                }
            }
        }
        return clsArr;
    }
}
