package com.xiaomi.channel.commonutils.android;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import com.xiaomi.channel.commonutils.logger.b;

public class e {
    public static Account a(Context context) {
        try {
            if (!b(context)) {
                return null;
            }
            Account[] accountsByType = AccountManager.get(context).getAccountsByType("com.xiaomi");
            if (accountsByType == null || accountsByType.length <= 0) {
                return null;
            }
            return accountsByType[0];
        } catch (Exception e) {
            b.d(e.toString());
            return null;
        }
    }

    public static boolean b(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            return packageManager.checkPermission("android.permission.GET_ACCOUNTS", packageName) == 0 || packageManager.checkPermission("android.permission.GET_ACCOUNTS_PRIVILEGED", packageName) == 0;
        } catch (Throwable unused) {
            return false;
        }
    }
}
