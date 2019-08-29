package com.jiuyan.inimage.b;

import android.content.Context;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: AliPayDownloadFileTool */
public class g {
    private final String a = "DownloadFileTool";
    private Context b;
    private l c;

    public g(Context context) {
        this.b = context;
        this.c = new l(context);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(String str, String str2, String str3, String str4, s sVar) {
        this.c.a(str, str2, str3, sVar);
    }
}
