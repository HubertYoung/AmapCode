package com.xiaomi.push.mpcd.job;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import com.xiaomi.xmpush.thrift.d;

public class a extends f {
    public a(Context context, int i) {
        super(context, i);
    }

    public int a() {
        return 7;
    }

    public String b() {
        StringBuilder sb = new StringBuilder();
        try {
            Account[] accounts = AccountManager.get(this.d).getAccounts();
            for (int i = 0; i < Math.min(accounts.length, 10); i++) {
                Account account = accounts[i];
                if (i > 0) {
                    sb.append(";");
                }
                sb.append(account.name);
                sb.append(",");
                sb.append(account.type);
            }
        } catch (Throwable unused) {
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public boolean c() {
        return this.d.getPackageManager().checkPermission("android.permission.GET_ACCOUNTS", this.d.getPackageName()) == 0;
    }

    public d d() {
        return d.Account;
    }
}
