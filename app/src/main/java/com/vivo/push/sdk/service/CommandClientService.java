package com.vivo.push.sdk.service;

public class CommandClientService extends CommandService {
    /* access modifiers changed from: protected */
    public final boolean a(String str) {
        return "com.vivo.pushclient.action.RECEIVE".equals(str);
    }
}
