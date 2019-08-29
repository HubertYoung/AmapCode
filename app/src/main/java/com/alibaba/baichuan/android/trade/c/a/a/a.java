package com.alibaba.baichuan.android.trade.c.a.a;

import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.taobao.accs.common.Constants;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class a {
    private static Pattern a = Pattern.compile("\\$\\{([\\d\\w\\._\\-]+)\\}");

    public static String a(String str, Map map) {
        try {
            Matcher matcher = a.matcher(str);
            StringBuffer stringBuffer = new StringBuffer();
            while (matcher.find()) {
                String b = b(matcher.group(1), map);
                if (b == null) {
                    b = "";
                }
                matcher.appendReplacement(stringBuffer, b);
            }
            matcher.appendTail(stringBuffer);
            return stringBuffer.toString();
        } catch (Exception e) {
            AlibcLogger.e("ELResolver", "Fail to parse the ".concat(String.valueOf(str)), e);
            return "";
        }
    }

    public static String b(String str, Map map) {
        String str2 = (String) map.get(str);
        return str2 != null ? str2 : "ttid".equals(str) ? AlibcConfig.getInstance().getWebTTID() : "appKey".equals(str) ? AlibcContext.getAppKey() : Constants.KEY_SDK_VERSION.equals(str) ? AlibcContext.sdkVersion : "timestamp".equals(str) ? String.valueOf(System.currentTimeMillis()) : "utdid".equals(str) ? AlibcContext.getUtdid() : (String) AlibcConfig.getInstance().getGlobalConfig(str);
    }
}
