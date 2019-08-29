package defpackage;

import com.alipay.mobile.common.transport.http.RequestMethodConstants;

/* renamed from: bpu reason: default package */
/* compiled from: Util */
public final class bpu {
    public static String a(int i) {
        return i == 0 ? "GET" : i == 1 ? "POST" : i == 2 ? RequestMethodConstants.HEAD_METHOD : "GET";
    }

    public static <T extends bpk> T a(bph bph, Class<T> cls, bpa bpa) throws RuntimeException {
        if (bpa == null) {
            return null;
        }
        try {
            T t = (bpk) cls.newInstance();
            t.setImpl(bpa);
            t.setRequest(bph);
            t.parse();
            return t;
        } catch (InstantiationException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append(cls.getName());
            sb.append(" 必须有无参数public默认构造方法 ");
            throw new RuntimeException(sb.toString());
        } catch (IllegalAccessException unused2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(cls.getName());
            sb2.append(" 必须有无参数public默认构造方法 ");
            throw new RuntimeException(sb2.toString());
        }
    }
}
