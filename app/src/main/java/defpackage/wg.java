package defpackage;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: wg reason: default package */
/* compiled from: DomainUtil */
public final class wg {
    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        Matcher matcher = Pattern.compile("([-\\w]*\\.(?:com\\.cn|net\\.cn|org\\.cn|gov\\.cn|com|net|cn|org|cc|me|mobi|asia|biz|info|name|tv|hk|公司|中国|网络)$)", 2).matcher(str);
        if (matcher.find()) {
            str = matcher.group();
        }
        return str;
    }
}
