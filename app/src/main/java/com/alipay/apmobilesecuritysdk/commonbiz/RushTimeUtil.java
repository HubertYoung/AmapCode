package com.alipay.apmobilesecuritysdk.commonbiz;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RushTimeUtil {
    private static TraceLogger a = LoggerFactory.f();

    public static boolean a() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] strArr = {"2016-11-10 2016-11-11", "2016-12-11 2016-12-12", "2017-01-27 2017-01-28"};
        int random = ((int) (Math.random() * 24.0d * 60.0d * 60.0d)) * 1;
        int i = 0;
        while (i < 3) {
            try {
                String[] split = strArr[i].split(Token.SEPARATOR);
                if (split != null) {
                    if (split.length == 2) {
                        Date date = new Date();
                        StringBuilder sb = new StringBuilder();
                        sb.append(split[0]);
                        sb.append(" 00:00:00");
                        Date parse = simpleDateFormat.parse(sb.toString());
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(split[1]);
                        sb2.append(" 23:59:59");
                        Date parse2 = simpleDateFormat.parse(sb2.toString());
                        Calendar instance = Calendar.getInstance();
                        instance.setTime(parse2);
                        instance.add(13, random);
                        Date time = instance.getTime();
                        if (date.after(parse) && date.before(time)) {
                            return true;
                        }
                        i++;
                    }
                }
                a.b((String) "RushTimeUtil", (String) "[-] time interval configuration error, please reconfigure it.");
                i++;
            } catch (Exception unused) {
                a.b((String) "RushTimeUtil", (String) "[-] Unexpected error happened while judge whether now is in rush hour.");
            }
        }
        return false;
    }
}
