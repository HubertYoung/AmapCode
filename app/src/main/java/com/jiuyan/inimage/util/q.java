package com.jiuyan.inimage.util;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

/* compiled from: ULog */
public final class q {
    public static final void a(String... strArr) {
    }

    public static final void a(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("INSDK_LOG ").append(str);
        LoggerFactory.getTraceLogger().warn((String) "INSDK_LOG ", stringBuffer.toString());
    }

    public static final void a(String str, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("INSDK_LOG ").append(str).append(Token.SEPARATOR).append(str2);
        LoggerFactory.getTraceLogger().warn((String) "INSDK_LOG ", stringBuffer.toString());
    }
}
