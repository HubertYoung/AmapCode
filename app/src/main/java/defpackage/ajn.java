package defpackage;

import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.common.tool.thirdparty.AuthServer;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: ajn reason: default package */
/* compiled from: BaseIntentHelper */
public final class ajn {
    public static boolean a(String str) {
        String str2;
        if (!TextUtils.isEmpty(str) && str.length() > 4) {
            String substring = str.substring(0, 4);
            if (!TextUtils.isEmpty(substring) && !substring.equalsIgnoreCase("http")) {
                if (substring.equalsIgnoreCase("file")) {
                    return str.toLowerCase().startsWith("file:///data/data/com.autonavi.minimap/") && !str.contains("../");
                }
                return true;
            }
        }
        try {
            Matcher matcher = Pattern.compile("[^\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)$").matcher(new URL(str).getHost().toLowerCase());
            if (matcher.find()) {
                str2 = matcher.group();
            } else {
                str2 = null;
            }
            if (!TextUtils.isEmpty(str2)) {
                for (String contains : new AuthServer().getAuthServers()) {
                    if (contains.contains(str2)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String b(String str) {
        String keyValue = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.REDIRECT);
        StringBuilder sb = new StringBuilder();
        sb.append(keyValue);
        sb.append("target=");
        String sb2 = sb.toString();
        try {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(URLEncoder.encode(str, "UTF-8"));
            return sb3.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return sb2;
        }
    }
}
