package com.alipay.mobile.antui.utils;

import android.text.TextUtils;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser {
    public static LinkedHashMap<String, String> parseALabel(String html) {
        String text;
        LinkedHashMap map = new LinkedHashMap();
        if (!TextUtils.isEmpty(html)) {
            Pattern textPattern = Pattern.compile("[\\>].*[\\<]");
            Pattern urlPattern = Pattern.compile("[\\'|\\\"].*[\\'|\\\"]");
            String[] msgArr = html.split("<a");
            for (int i = 0; i < msgArr.length; i++) {
                Matcher textMatcher = textPattern.matcher(msgArr[i]);
                if (textMatcher.find()) {
                    text = msgArr[i].substring(textMatcher.start() + 1, textMatcher.end() - 1);
                } else {
                    text = msgArr[i];
                }
                String url = null;
                Matcher urlMatcher = urlPattern.matcher(msgArr[i]);
                if (urlMatcher.find()) {
                    url = msgArr[i].substring(urlMatcher.start() + 1, urlMatcher.end() - 1);
                }
                map.put(text, url);
            }
        }
        return map;
    }
}
