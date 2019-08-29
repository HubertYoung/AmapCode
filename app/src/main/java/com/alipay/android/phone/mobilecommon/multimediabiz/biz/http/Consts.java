package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http;

import java.nio.charset.Charset;

public final class Consts {
    public static final Charset ASCII = Charset.forName("US-ASCII");
    public static final String BUSINESS_ID = "bizId";
    public static final int CR = 13;
    public static final int HT = 9;
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final String KEY_TIME_OUT = "task_timeout_second";
    public static final String KEY_UP_TYPE = "uploadType";
    public static final int LF = 10;
    public static final int SP = 32;
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final String VALUE_UP_TYPE_NORMAL = "-1";
    public static final String VALUE_UP_TYPE_QUIC = "2";

    private Consts() {
    }
}
