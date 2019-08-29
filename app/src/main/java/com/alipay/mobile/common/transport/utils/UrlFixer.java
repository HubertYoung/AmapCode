package com.alipay.mobile.common.transport.utils;

import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.net.URLEncoder;
import java.util.BitSet;

public class UrlFixer {
    static BitSet allowedChars = new BitSet() {
        {
            int n = ":/?#[]@!$&'()*+,;=abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-._~%".length();
            for (int i = 0; i < n; i++) {
                set(":/?#[]@!$&'()*+,;=abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-._~%".charAt(i));
            }
        }
    };

    public static String fixUrl(String string) {
        StringBuilder builder = new StringBuilder();
        String string2 = string.replaceAll("%(?![0-9A-Fa-f]{2})", "%25");
        int n = string2.length();
        for (int i = 0; i < n; i++) {
            char c = string2.charAt(i);
            if (allowedChars.get(c)) {
                builder.append(c);
            } else {
                String tmp = Character.toString(c);
                try {
                    builder.append(URLEncoder.encode(tmp, "UTF-8"));
                } catch (Exception e) {
                    builder.append(tmp);
                }
            }
        }
        int p = builder.indexOf(MetaRecord.LOG_SEPARATOR);
        while (p >= 0) {
            p = builder.indexOf(MetaRecord.LOG_SEPARATOR, p + 1);
            if (p > 0) {
                builder.replace(p, p + 1, "%23");
            }
        }
        return builder.toString();
    }
}
