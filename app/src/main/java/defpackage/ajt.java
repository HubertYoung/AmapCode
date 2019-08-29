package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import java.net.URLDecoder;
import java.util.List;

/* renamed from: ajt reason: default package */
/* compiled from: PageBackImpl */
public class ajt implements ajw {
    public final boolean a(List<String> list, bid bid) {
        boolean z = false;
        if (bid == null || list.size() <= 0) {
            return false;
        }
        for (String next : list) {
            if (!TextUtils.isEmpty(next)) {
                String a = ajx.a(next);
                if (a == null) {
                    a = next;
                }
                if (a.startsWith("amapuri://ajx?") || a.startsWith("amapuri://ajx_smallbridge?") || a.startsWith("amapuri://ajx-activity?")) {
                    z = ajv.a(bid, a, null, null, new ajs());
                } else {
                    z = ajv.a(bid, a, null, null, new aju());
                }
                if (z) {
                    break;
                }
            }
        }
        return z;
    }

    public final boolean a(List<String> list, bid bid, ResultType resultType, PageBundle pageBundle) {
        boolean z = false;
        if (bid == null || list == null || list.size() <= 0) {
            return false;
        }
        for (String next : list) {
            if (!TextUtils.isEmpty(next)) {
                String a = ajx.a(next);
                if (a == null) {
                    a = next;
                }
                if (a.startsWith("amapuri://ajx?") || a.startsWith("amapuri://ajx_smallbridge?") || a.startsWith("amapuri://ajx-activity?")) {
                    z = ajv.a(bid, a, resultType, pageBundle, new ajs());
                } else {
                    z = ajv.a(bid, a, resultType, pageBundle, new aju());
                }
                if (z) {
                    break;
                }
            }
        }
        return z;
    }

    public final boolean b(List<String> list, bid bid, ResultType resultType, PageBundle pageBundle) {
        boolean z = false;
        if (bid == null || list == null || list.size() <= 0) {
            return false;
        }
        for (String next : list) {
            if (!TextUtils.isEmpty(next)) {
                String a = ajx.a(next);
                if (a == null) {
                    a = next;
                }
                if (a.startsWith("amapuri://ajx?") || a.startsWith("amapuri://ajx_smallbridge?") || a.startsWith("amapuri://ajx-activity?")) {
                    z = ajv.b(bid, a, resultType, pageBundle, new ajs());
                } else {
                    z = ajv.b(bid, a, resultType, pageBundle, new aju());
                }
                if (z) {
                    break;
                }
            }
        }
        return z;
    }

    public final boolean a(List<String> list, bid bid, ResultType resultType, PageBundle pageBundle, String str) {
        boolean z = false;
        if (bid == null || list == null || list.size() <= 0) {
            return false;
        }
        String str2 = null;
        String str3 = null;
        boolean z2 = false;
        for (String next : list) {
            if (!TextUtils.isEmpty(next)) {
                String a = ajx.a(next);
                if (a == null) {
                    a = next;
                }
                if (a.startsWith("amapuri://ajx?") || a.startsWith("amapuri://ajx_smallbridge?") || a.startsWith("amapuri://ajx-activity?")) {
                    boolean a2 = ajv.a(bid, a, resultType, pageBundle, new ajs());
                    String a3 = a(a);
                    z2 = a2;
                    str3 = a3;
                } else {
                    boolean a4 = ajv.a(bid, a, resultType, pageBundle, new aju());
                    String[] split = a.split("\\?");
                    if (split != null && split.length > 0) {
                        str3 = split[0];
                    }
                    z2 = a4;
                }
                if (z2) {
                    break;
                }
            }
        }
        if (!TextUtils.isEmpty(str)) {
            if (str.startsWith("amapuri://ajx?") || str.startsWith("amapuri://ajx_smallbridge?") || str.startsWith("amapuri://ajx-activity?")) {
                str2 = a(str);
            } else {
                String[] split2 = str.split("\\?");
                if (split2 != null && split2.length > 0) {
                    str2 = split2[0];
                }
            }
            if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && str3.equals(str2)) {
                z = true;
            }
        }
        if (z2 && !z && !TextUtils.isEmpty(str)) {
            Intent intent = new Intent();
            intent.setData(Uri.parse(str));
            DoNotUseTool.startScheme(intent);
        }
        return z2;
    }

    public final boolean b(List<String> list, bid bid, ResultType resultType, PageBundle pageBundle, String str) {
        boolean z = false;
        if (bid == null || list == null || list.size() <= 0) {
            return false;
        }
        String str2 = null;
        String str3 = null;
        boolean z2 = false;
        for (String next : list) {
            if (!TextUtils.isEmpty(next)) {
                String a = ajx.a(next);
                if (a == null) {
                    a = next;
                }
                if (a.startsWith("amapuri://ajx?") || a.startsWith("amapuri://ajx_smallbridge?") || a.startsWith("amapuri://ajx-activity?")) {
                    boolean b = ajv.b(bid, a, resultType, pageBundle, new ajs());
                    String a2 = a(a);
                    z2 = b;
                    str3 = a2;
                } else {
                    boolean b2 = ajv.b(bid, a, resultType, pageBundle, new aju());
                    String[] split = a.split("\\?");
                    if (split != null && split.length > 0) {
                        str3 = split[0];
                    }
                    z2 = b2;
                }
                if (z2) {
                    break;
                }
            }
        }
        if (!TextUtils.isEmpty(str)) {
            if (str.startsWith("amapuri://ajx?") || str.startsWith("amapuri://ajx_smallbridge?") || str.startsWith("amapuri://ajx-activity?")) {
                str2 = a(str);
            } else {
                String[] split2 = str.split("\\?");
                if (split2 != null && split2.length > 0) {
                    str2 = split2[0];
                }
            }
            if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && str3.equals(str2)) {
                z = true;
            }
        }
        if (z2 && !z && !TextUtils.isEmpty(str)) {
            Intent intent = new Intent();
            intent.setData(Uri.parse(str));
            DoNotUseTool.startScheme(intent);
        }
        return z2;
    }

    private static String a(String str) {
        String str2 = null;
        if (str == null) {
            return null;
        }
        String str3 = (!str.startsWith("amapuri://ajx?") || str.length() <= 14) ? (!str.startsWith("amapuri://ajx_smallbridge?") || str.length() <= 26) ? (!str.startsWith("amapuri://ajx-activity?") || str.length() <= 23) ? null : str.substring(23) : str.substring(26) : str.substring(14);
        if (str3 == null) {
            return null;
        }
        String[] split = str3.split("&");
        if (split == null || split.length <= 0) {
            return null;
        }
        int length = split.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String str4 = split[i];
            if (str4.startsWith("path=")) {
                str2 = str4.substring(5);
                break;
            }
            i++;
        }
        if (!TextUtils.isEmpty(str2)) {
            str2 = URLDecoder.decode(str2);
        }
        return str2;
    }
}
