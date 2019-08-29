package com.alipay.mobile.beehive.capture.utils;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.io.File;

public class OtherUtils {
    public static void scanMediaFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            Intent scanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            scanIntent.setData(Uri.fromFile(new File(path)));
            LauncherApplicationAgent.getInstance().getApplicationContext().sendBroadcast(scanIntent);
        }
    }

    public static String getAbsPath(String urlStr) {
        if (TextUtils.isEmpty(urlStr)) {
            return "";
        }
        return Uri.parse(urlStr).getPath();
    }
}
