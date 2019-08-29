package com.alipay.mobile.nebulacore.view;

import android.content.Context;
import android.widget.Toast;

public class H5Toast {
    public static void showToast(Context context, String text, int duration) {
        Toast.makeText(context, text, duration).show();
    }
}
