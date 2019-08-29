package defpackage;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

/* renamed from: lb reason: default package */
/* compiled from: BLAppOps */
public final class lb {
    public static String a() {
        String str = a.a().a;
        if (str == null) {
            return "";
        }
        if (str.contains(Token.SEPARATOR)) {
            str = str.substring(0, str.lastIndexOf(Token.SEPARATOR));
        }
        String[] split = str.split("[.]");
        if (split.length < 4) {
            return "";
        }
        return split[3];
    }
}
