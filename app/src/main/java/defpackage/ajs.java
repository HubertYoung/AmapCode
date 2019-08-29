package defpackage;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.Ajx3PageInterface;
import java.net.URLDecoder;

/* renamed from: ajs reason: default package */
/* compiled from: PageBackAjx3Equal */
final class ajs implements ajr {
    ajs() {
    }

    public final boolean a(String str, bid bid) {
        if (bid == null || TextUtils.isEmpty(str)) {
            return false;
        }
        String str2 = (!str.startsWith("amapuri://ajx?") || str.length() <= 14) ? (!str.startsWith("amapuri://ajx_smallbridge?") || str.length() <= 26) ? (!str.startsWith("amapuri://ajx-activity?") || str.length() <= 23) ? null : str.substring(23) : str.substring(26) : str.substring(14);
        if (str2 == null) {
            return false;
        }
        String[] split = str2.split("&");
        if (split == null || split.length <= 0) {
            return false;
        }
        String str3 = null;
        String str4 = null;
        for (String str5 : split) {
            if (str5.startsWith("path=")) {
                str3 = str5.substring(5);
            } else if (str5.startsWith("pageid=")) {
                str4 = str5.substring(7);
            }
        }
        if (!TextUtils.isEmpty(str3)) {
            str3 = URLDecoder.decode(str3);
        }
        if (!TextUtils.isEmpty(str4)) {
            str4 = URLDecoder.decode(str4);
        }
        boolean z = true;
        if (bid instanceof Ajx3PageInterface) {
            Ajx3PageInterface ajx3PageInterface = (Ajx3PageInterface) bid;
            if (!TextUtils.isEmpty(str3)) {
            }
        }
        z = false;
        return z;
    }
}
