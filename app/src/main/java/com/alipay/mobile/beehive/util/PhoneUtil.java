package com.alipay.mobile.beehive.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.uc.webview.export.WebView;

public class PhoneUtil {
    public static void call(Context context, String telephoneNumber) {
        if (telephoneNumber.contains(";")) {
            telephoneNumber = telephoneNumber.substring(0, telephoneNumber.indexOf(";"));
        }
        Intent intent = new Intent("android.intent.action.CALL");
        intent.setData(Uri.parse(new StringBuilder(WebView.SCHEME_TEL).append(telephoneNumber).toString()));
        context.startActivity(intent);
    }
}
