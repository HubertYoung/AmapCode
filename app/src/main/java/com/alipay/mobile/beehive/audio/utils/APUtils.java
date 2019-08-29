package com.alipay.mobile.beehive.audio.utils;

import com.alipay.mobile.beehive.video.views.OriVideoPreviewCon;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class APUtils {
    public static String formatDuration(long duration) {
        if (duration < 0) {
            duration = 0;
        }
        long duration2 = (long) Math.round(((float) duration) / 1000.0f);
        if (duration2 <= 0) {
            return OriVideoPreviewCon.ZERO_DURATION;
        }
        long duration3 = duration2 * 1000;
        String pattern = "mm:ss";
        if (duration3 >= 3600000) {
            pattern = "HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date(duration3));
    }
}
