package com.jiuyan.inimage.http;

import android.content.Context;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inalipay.ilisya.Ilisya;

public class HttpInitializer {
    public HttpInitializer() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static void initialize(Context context) {
        Ilisya.Init("GD{8W3N^53~;2{");
    }
}
