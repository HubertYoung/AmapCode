package com.alipay.android.phone.inside.security.util;

import android.content.Context;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.initialize.IInitializeComponent.IInitFinishListener;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class SsoLoginUtils {
    private static volatile boolean a = true;

    public static void a(final Context context) {
        if (a) {
            try {
                SecurityGuardManager.getInitializer().registerInitFinishListener(new IInitFinishListener() {
                    public final void onSuccess() {
                        LoggerFactory.f().a((String) "xxxxxx", (String) "so load Success!!!");
                    }

                    public final void onError() {
                        LoggerFactory.f().a((String) "xxxxxx", (String) "so load faild!!!");
                    }
                });
                new Thread(new Runnable() {
                    public final void run() {
                        try {
                            SecurityGuardManager.getInstance(context);
                        } catch (Throwable th) {
                            LoggerFactory.f().a((String) "SsoLoginUtils", th);
                        }
                    }
                }).start();
                a = false;
            } catch (Throwable th) {
                LoggerFactory.f().a((String) "SsoLoginUtils", th);
                a = true;
            }
        }
    }
}
