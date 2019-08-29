package com.xiaomi.push.service;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.text.TextUtils;

class q implements OnAccountsUpdateListener {
    final /* synthetic */ p a;

    q(p pVar) {
        this.a = pVar;
    }

    public void onAccountsUpdated(Account[] accountArr) {
        Account account;
        p pVar;
        String str;
        if (accountArr != null && accountArr.length > 0) {
            int length = accountArr.length;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    account = null;
                    break;
                }
                account = accountArr[i];
                if (account != null && TextUtils.equals("com.xiaomi", account.type)) {
                    break;
                }
                i++;
            }
            if (account != null && !TextUtils.isEmpty(account.name)) {
                z = true;
            }
            boolean c = r.a(this.a.a).c();
            if (z && !c) {
                r.a(this.a.a).a(account.name);
                pVar = this.a;
                str = account.name;
            } else if (!z && c) {
                r.a(this.a.a).a();
                pVar = this.a;
                str = "0";
            } else if (z && c && !TextUtils.equals(r.a(this.a.a).b(), account.name)) {
                r.a(this.a.a).a(account.name);
                this.a.a(account.name);
            }
            pVar.a(str);
        }
    }
}
