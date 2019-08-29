package defpackage;

import android.text.TextUtils;

/* renamed from: euc reason: default package */
/* compiled from: Md5FileNameGenerator */
public final class euc implements etz {
    public final String generate(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        String substring = (lastIndexOf == -1 || lastIndexOf <= str.lastIndexOf(47) || (lastIndexOf + 2) + 4 <= str.length()) ? "" : str.substring(lastIndexOf + 1, str.length());
        String c = ett.c(str);
        if (TextUtils.isEmpty(substring)) {
            return c;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(c);
        sb.append(".");
        sb.append(substring);
        return sb.toString();
    }
}
