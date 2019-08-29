package com.taobao.applink.exception;

import com.taobao.applink.c.b;
import com.taobao.applink.util.TBAppLinkUtil;

public class TBAppLinkException extends Exception {
    public TBAppLinkException(a aVar) {
        super(getErrorMsg(aVar));
        b.a().a(TBAppLinkUtil.getCrashUrl(), String.valueOf(aVar.f), null);
    }

    public TBAppLinkException(String str) {
        super(str);
        b.a().a(TBAppLinkUtil.getCrashUrl(), str, null);
    }

    private static String getErrorMsg(a aVar) {
        if (aVar == null) {
            return "AppLinkSDK error!";
        }
        StringBuilder sb = new StringBuilder("AppLinkSDK error! code: ");
        sb.append(aVar.f);
        return sb.toString();
    }
}
