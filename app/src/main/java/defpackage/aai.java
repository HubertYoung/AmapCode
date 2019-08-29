package defpackage;

import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import java.util.Map;
import java.util.regex.Pattern;

/* renamed from: aai reason: default package */
/* compiled from: URLBuilderHelper */
public final class aai {
    private static final Pattern a = Pattern.compile("(http|https):\\/\\/([\\w.]+\\/?)\\S*");

    public static String a(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            if (!a(str)) {
                str = aaf.b(str);
            }
        } else if (!a(str2)) {
            str = aaf.b(ConfigerHelper.AOS_URL_KEY);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        String sb2 = sb.toString();
        if (sb2.contains("?")) {
            return sb2;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append("?");
        return sb3.toString();
    }

    public static String a(String str, Map<String, Object> map) {
        if (!str.contains("$")) {
            return str;
        }
        int indexOf = str.indexOf(63);
        if (indexOf >= str.length() - 1) {
            return str;
        }
        String str2 = str;
        for (String split : str.substring(indexOf + 1).split("&")) {
            String str3 = split.split("=")[0];
            if (map.containsKey(str3)) {
                str2 = str2.replaceFirst("\\$", String.valueOf(map.get(str3)));
                map.remove(str3);
            }
        }
        return str2;
    }

    public static String a(String[] strArr, Map<String, Object> map) {
        StringBuffer stringBuffer = new StringBuffer("");
        if (strArr != null && strArr.length > 0 && map != null && map.size() > 0) {
            for (String str : strArr) {
                Object obj = map.get(str);
                if (obj != null) {
                    stringBuffer.append(obj);
                }
            }
        }
        return stringBuffer.toString();
    }

    private static boolean a(String str) {
        return a.matcher(str).matches();
    }
}
