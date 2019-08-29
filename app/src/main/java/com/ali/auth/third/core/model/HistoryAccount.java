package com.ali.auth.third.core.model;

import android.text.TextUtils;

public class HistoryAccount {
    public String email;
    public String mobile;
    public String nick;
    public String tokenKey;
    public String userId;

    public HistoryAccount() {
    }

    public HistoryAccount(String str, String str2, String str3, String str4, String str5) {
        this.userId = str;
        this.tokenKey = str2;
        this.nick = str3;
        this.mobile = str4;
        this.email = str5;
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[nick=");
        sb.append(this.nick);
        sb.append("mobile=");
        sb.append(this.mobile);
        sb.append(",email=");
        sb.append(this.email);
        sb.append(",key=");
        sb.append(this.tokenKey);
        sb.append(",userId=");
        sb.append(this.userId);
        sb.append("]");
        return sb.toString();
    }

    public void update(HistoryAccount historyAccount) {
        if (this.userId == historyAccount.userId) {
            if (!TextUtils.isEmpty(historyAccount.tokenKey)) {
                this.tokenKey = historyAccount.tokenKey;
            }
            this.nick = historyAccount.nick;
            this.mobile = historyAccount.mobile;
            this.email = historyAccount.email;
        }
    }
}
