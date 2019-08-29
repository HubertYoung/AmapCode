package com.autonavi.bundle.account;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

class AccountActivityDelegate {
    static final String a = "AccountActivityDelegate";
    private static AccountActivityDelegate c;
    AccountActivity b;

    public static class AccountPageContainer extends FrameLayout {
        private final AccountActivity mAccountActivity;

        public AccountPageContainer(@NonNull AccountActivity accountActivity) {
            super(accountActivity);
            this.mAccountActivity = accountActivity;
        }

        public void removeAllViews() {
            super.removeAllViews();
            this.mAccountActivity.finish();
        }

        public void removeView(View view) {
            super.removeView(view);
            if (getChildCount() == 0) {
                this.mAccountActivity.finish();
            }
        }

        public void removeViewAt(int i) {
            super.removeViewAt(i);
            if (getChildCount() == 0) {
                this.mAccountActivity.finish();
            }
        }
    }

    private AccountActivityDelegate() {
    }

    public static AccountActivityDelegate a() {
        if (c == null) {
            synchronized (AccountActivityDelegate.class) {
                try {
                    if (c == null) {
                        c = new AccountActivityDelegate();
                    }
                }
            }
        }
        return c;
    }

    public static void a(int i, int i2, Intent intent) {
        a.a.a(i, i2, intent);
        a.a.a = null;
        a.a.a(i, i2, intent);
    }
}
