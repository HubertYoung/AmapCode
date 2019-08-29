package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config;

public enum Env {
    ONLINE(new OnlineServerAddress()),
    PRE_RELEASE(new PreReleaseServerAddress()),
    DAILY(new DailyServerAddress()),
    NEW_ONLINE(new NewOnlineServerAddress());
    
    private ServerAddress addr;

    private Env(ServerAddress addr2) {
        this.addr = addr2;
    }

    public final ServerAddress getServerAddress() {
        return this.addr;
    }
}
