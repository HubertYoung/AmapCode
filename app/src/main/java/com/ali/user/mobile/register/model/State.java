package com.ali.user.mobile.register.model;

import android.os.Bundle;
import android.text.TextUtils;
import com.ali.user.mobile.register.Account;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.UnifyRegisterAllResPb;

public class State {
    public static String a = "0";
    public volatile int b;
    public UnifyRegisterAllResPb c;
    public String d;
    public RpcException e;
    public String f;
    public String g;
    public Bundle h;
    private Account i;

    public final Account a() {
        return this.i;
    }

    public final void a(String str, String str2, String str3) {
        if (this.i == null) {
            this.i = new Account(str, str2, str3);
        } else {
            this.i.update(str, str2, str3);
        }
        StateUtils.b();
    }

    public final boolean b() {
        return this.c == null && TextUtils.isEmpty(this.d) && TextUtils.isEmpty(this.g) && this.b == 0 && TextUtils.isEmpty(this.f) && (this.h == null || this.h.isEmpty()) && (this.i == null || this.i.isEmpty());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(Token.SEPARATOR);
        sb.append(this.b);
        sb.append(Token.SEPARATOR);
        sb.append(this.d);
        sb.append(Token.SEPARATOR);
        sb.append(this.g);
        sb.append(Token.SEPARATOR);
        sb.append(this.c == null ? "" : this.c.resultStatus);
        return sb.toString();
    }

    public final synchronized State c() {
        State state;
        state = new State();
        state.b = this.b;
        state.c = this.c;
        state.d = this.d;
        state.f = this.f;
        state.g = this.g;
        state.i = this.i == null ? new Account() : new Account(this.i);
        if (this.h != null) {
            state.h = new Bundle(this.h);
        }
        return state;
    }

    public final void a(Account account) {
        this.i = new Account(account);
    }
}
