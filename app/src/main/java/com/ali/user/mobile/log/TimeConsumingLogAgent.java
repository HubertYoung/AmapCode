package com.ali.user.mobile.log;

import android.os.SystemClock;
import com.ali.user.mobile.info.AppInfo;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.inside.android.phone.mrpc.core.RpcException;

public class TimeConsumingLogAgent {
    private final Behavior a = new Behavior();
    private long b;
    private long c;

    public TimeConsumingLogAgent(String str, String str2) {
        this.a.a = str;
        this.a.c = str2;
        this.a.a("appId", AppInfo.getInstance().getSdkId());
        this.a.a("appVersion", AppInfo.getInstance().getSdkVersion());
    }

    public TimeConsumingLogAgent(String str, String str2, String str3) {
        AliUserLog.c("aliuser", "TimeConsumingLogAgent > ".concat(String.valueOf(str)));
        this.a.a = str2;
        this.a.c = str3;
        this.a.a("appId", AppInfo.getInstance().getSdkId());
        this.a.a("appVersion", AppInfo.getInstance().getSdkVersion());
    }

    public static void a(TimeConsumingLogAgent timeConsumingLogAgent, RpcException rpcException) {
        timeConsumingLogAgent.d("netException").a((String) "code", String.valueOf(rpcException.getCode())).a((String) "msg", rpcException.getMsg());
    }

    public final TimeConsumingLogAgent a() {
        this.b = SystemClock.elapsedRealtime();
        return this;
    }

    public final TimeConsumingLogAgent b() {
        this.c = SystemClock.elapsedRealtime() - this.b;
        this.a.h = String.valueOf(this.c);
        this.a.a("timespan", String.valueOf(this.c));
        return this;
    }

    public final TimeConsumingLogAgent a(String str) {
        this.a.a("facade", str);
        return this;
    }

    public final TimeConsumingLogAgent b(String str) {
        this.a.a("token", str);
        return this;
    }

    public final TimeConsumingLogAgent c(String str) {
        this.a.g = str;
        return this;
    }

    public final TimeConsumingLogAgent d(String str) {
        this.a.i = str;
        return this;
    }

    public final TimeConsumingLogAgent a(String str, String str2) {
        this.a.a(str, str2);
        return this;
    }

    public final void c() {
        LoggerFactory.d().a(this.a);
    }
}
