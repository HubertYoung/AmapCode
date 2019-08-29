package com.alipay.android.phone.inside.wallet.api;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class JumpWalletUtils {
    static final String PACKAGE_NAME_WALLET = "com.eg.android.AlipayGphone";

    public static void jumpWallet(Context context, String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.setPackage("com.eg.android.AlipayGphone");
        intent.putExtra("directly", true);
        context.startActivity(intent);
    }

    public static void jumpWalletNewTask(Context context, String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.setPackage("com.eg.android.AlipayGphone");
        intent.addFlags(268435456);
        context.startActivity(intent);
    }
}
