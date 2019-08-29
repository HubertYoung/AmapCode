package defpackage;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: eo reason: default package */
/* compiled from: SeqGen */
public final class eo {
    private static AtomicInteger a = new AtomicInteger(0);

    public static String a(String str, String str2) {
        StringBuilder sb = new StringBuilder(16);
        if (str != null) {
            sb.append(str);
            sb.append(DjangoUtils.EXTENSION_SEPARATOR);
        }
        sb.append(str2);
        sb.append(a.incrementAndGet() & Integer.MAX_VALUE);
        return sb.toString();
    }
}
