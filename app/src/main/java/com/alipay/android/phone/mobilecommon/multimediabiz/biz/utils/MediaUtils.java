package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.io.File;

public class MediaUtils {
    public static void scanPictureAsync(Context ctx, String picturePath) {
        Intent scanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        scanIntent.setData(Uri.fromFile(new File(picturePath)));
        ctx.sendBroadcast(scanIntent);
    }
}
