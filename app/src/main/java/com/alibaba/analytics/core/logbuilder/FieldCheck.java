package com.alibaba.analytics.core.logbuilder;

import com.alibaba.analytics.utils.Logger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class FieldCheck {
    private static Pattern mPattern = Pattern.compile("(\\|\\||[\t\r\n]|\u0001|\u0000)+");

    private static String _getStringNoBlankAndDLine(String str) {
        return (str == null || "".equals(str)) ? str : mPattern.matcher(str).replaceAll("");
    }

    private static String _checkField(String str) {
        return _getStringNoBlankAndDLine(str);
    }

    public static Map<String, String> checkMapFields(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        Iterator<String> it = map.keySet().iterator();
        if (it != null) {
            while (it.hasNext()) {
                try {
                    String next = it.next();
                    if (next != null) {
                        hashMap.put(next, _checkField(map.get(next)));
                    }
                } catch (Throwable th) {
                    Logger.e("[_checkMapFields]", th, new Object[0]);
                }
            }
        }
        return hashMap;
    }
}
