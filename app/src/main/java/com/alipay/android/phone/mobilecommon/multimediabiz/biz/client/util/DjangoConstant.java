package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util;

import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import java.nio.charset.Charset;

public class DjangoConstant {
    public static final String API_URL_FORMAT_HTTP = "http://%1$s/%2$s";
    public static final int APP_ID_DAILY = 78;
    public static final int APP_ID_ONLINE = 35;
    public static final String COOKIE_FORMAT = "DJANGO_UID=%s;DJANGO_ACL=%s";
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    public static int DJANGO_400 = 888;
    public static int DJANGO_CANCEL = 10000;
    public static final int DJANGO_CODE_NET_CURRENT_LIMITED = 429;
    public static int DJANGO_CODE_PARALLEL_UP_ERR = SecExceptionCode.SEC_ERROR_UMID_UNKNOWN_ERR;
    public static int DJANGO_OK = 0;
    public static final String DJANGO_ORIGINAL = "original";
    public static final String DJANGO_PR_QUALITY80 = "_80q";
    public static final String DJANGO_PR_SPRATOR = "_";
    public static final String DJANGO_QUALITY80 = "q80";
    public static final String DJANGO_QUALITY_KEY = "q";
    public static int DJANGO_TIMEOUT = RETCODE.TIME_OUT.value();
    public static final String FILE_PATH = "alipay_files";
    public static final int HTTPS_PORT = 443;
    public static final String HTTPS_SCHEME = "https";
    public static final int HTTP_PORT = 80;
    public static final String HTTP_SCHEME = "http";
    public static final String IMAGE_PATH = "im";
    public static final int PLAT_ANDROID = 2;
    public static final String PROGRESSIVE_KEY = "_1pr";
    public static final String STORE_PATH = "alipay/multimedia/";
    public static final String SUFFIX_AHP = ".ahp";
    public static final String SUFFIX_JPEG = ".jpg";
    public static final String SUFFIX_SRC = ".src";
    public static final String SUFFIX_WEBP = ".webp";
    public static final String TRACE_ID = "traceId";
    public static final int VERSION = 1;
}
