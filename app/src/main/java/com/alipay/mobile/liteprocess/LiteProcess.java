package com.alipay.mobile.liteprocess;

import android.content.ServiceConnection;
import android.os.Messenger;
import com.alipay.mobile.liteprocess.ipc.IClientService;
import java.util.HashSet;
import java.util.Set;

public class LiteProcess {
    String a;
    int b;
    int c;
    int d;
    String e;
    boolean f;
    Set<String> g = new HashSet();
    Messenger h;
    ServiceConnection i;
    IClientService j;
    boolean k;
    String l;
    Set<String> m = new HashSet();
    boolean n = true;
    String o = "default";
    boolean p;
    String q;

    public String toString() {
        return "LiteProcess{clientId='" + this.a + '\'' + ", lpid=" + this.b + ", pid=" + this.c + ", state=" + this.d + ", appId='" + this.e + '\'' + ", isShow=" + this.f + ", canStop=" + this.k + ", fromAppid=" + this.l + ", toAppids=" + this.m + ", canResetFromActivity=" + this.n + ", appType=" + this.q + '}';
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        this.c = -1;
        this.a = null;
        this.e = null;
        this.d = 0;
        this.f = false;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = true;
        this.l = null;
        this.m.clear();
        this.o = "default";
        this.p = false;
    }

    public int getLpid() {
        return this.b;
    }

    public int getPid() {
        return this.c;
    }

    public int getState() {
        return this.d;
    }

    public String getAppId() {
        return this.e;
    }

    public boolean isShow() {
        return this.f;
    }

    public Messenger getReplyTo() {
        return this.h;
    }

    public String getAppType() {
        return this.q;
    }
}
