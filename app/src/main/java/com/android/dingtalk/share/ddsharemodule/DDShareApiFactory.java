package com.android.dingtalk.share.ddsharemodule;

import android.content.Context;

public class DDShareApiFactory {
    public static IDDShareApi createDDShareApi(Context context, String str) {
        return new DDShareApi(context, str);
    }

    public static IDDShareApi createDDShareApi(Context context, String str, boolean z) {
        return new DDShareApi(context, str, z);
    }
}
