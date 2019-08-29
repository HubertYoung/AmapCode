package defpackage;

import android.text.TextUtils;
import com.autonavi.common.model.POI;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* renamed from: ejx reason: default package */
/* compiled from: TrainUtil */
public final class ejx {
    public static String a(POI poi) {
        if (poi == null) {
            return "";
        }
        lj b = li.a().b(poi.getPoint().x, poi.getPoint().y);
        String str = "";
        if (b != null) {
            str = b.a;
            if (str.endsWith("市")) {
                str = str.substring(0, str.length() - 1);
            } else if (str.endsWith("地区")) {
                str = str.substring(0, str.length() - 2);
            }
        }
        return str;
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            String encode = URLEncoder.encode(str, "utf-8");
            StringBuilder sb = new StringBuilder("http://f.amap.com/new/redirect?target=");
            sb.append(encode);
            sb.append("&from=amap");
            return sb.toString();
        } catch (UnsupportedEncodingException unused) {
            return str;
        }
    }
}
