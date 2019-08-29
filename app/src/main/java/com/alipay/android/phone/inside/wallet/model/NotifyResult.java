package com.alipay.android.phone.inside.wallet.model;

import android.os.Bundle;

public class NotifyResult {
    public INotifyChecker notifyChecker;
    public Bundle result;

    public NotifyResult(INotifyChecker iNotifyChecker) {
        this.notifyChecker = iNotifyChecker;
    }

    public boolean illegel(Bundle bundle) {
        if (this.notifyChecker == null) {
            return false;
        }
        return this.notifyChecker.illegel(bundle);
    }

    public Bundle getBundle(String str) {
        if (this.result == null) {
            return null;
        }
        return this.result.getBundle(str);
    }

    public String getString(String str) {
        if (this.result == null) {
            return "";
        }
        return this.result.getString(str);
    }

    public String getString(String str, String str2) {
        if (this.result == null) {
            return str2;
        }
        return this.result.getString(str, str2);
    }
}
