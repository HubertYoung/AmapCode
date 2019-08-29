package com.uc.webview.export.cyclone.service;

import android.content.Context;
import com.uc.webview.export.cyclone.Constant;

@Constant
/* compiled from: ProGuard */
public interface UCUnSevenZip extends UCServiceInterface {
    int deccompress(Context context, String str, String str2);

    int deccompress(Context context, String str, String str2, String str3);
}
