package com.alipay.mobile.common.nbnet.biz.util;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.biz.constants.NBNetConfigureItem;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;

public class NBNetConfigUtil {
    public static final int a() {
        return TransportStrategy.getConnTimeout(NBNetEnvUtils.a());
    }

    public static final int b() {
        return TransportStrategy.getHandshakTimeout();
    }

    public static final int c() {
        return TransportConfigureManager.getInstance().getIntValue(NBNetConfigureItem.COMP_CONNECT_TIME_OUT);
    }

    public static final int d() {
        return u().getIntValue(NBNetConfigureItem.DOWNLOAD_RETRY_LOGIC_ERROR);
    }

    public static final int e() {
        return u().getIntValue(NBNetConfigureItem.DOWNLOAD_VERIFY_ERROR);
    }

    public static final int f() {
        return u().getIntValue(NBNetConfigureItem.DOWNLOAD_RETRY_NETWORK_ERROR);
    }

    public static final int g() {
        return u().getIntValue(NBNetConfigureItem.DOWNLOAD_SINGLE_FLOW_LIMIT);
    }

    public static final int h() {
        return u().getIntValue(NBNetConfigureItem.DOWNLOAD_FUSE_MEASURE_INTERVAL);
    }

    public static final int i() {
        return u().getIntValue(NBNetConfigureItem.DOWNLOAD_FUSE_MEASURE_COUNT);
    }

    public static final int j() {
        return u().getIntValue(NBNetConfigureItem.UPLOAD_MAX_EXECUTION_COUNT);
    }

    public static final int k() {
        return u().getIntValue(NBNetConfigureItem.UPLOAD_NETWORK_EXECUTION_COUNT);
    }

    public static final int l() {
        return u().getIntValue(NBNetConfigureItem.UPLOAD_FILE_EXECUTION_COUNT);
    }

    public static final int m() {
        return u().getIntValue(NBNetConfigureItem.UPLOAD_UNKNOW_EXECUTION_COUNT);
    }

    public static final int n() {
        return u().getIntValue(NBNetConfigureItem.UPLOAD_SERVER_EXECUTE_COUNT);
    }

    public static final int o() {
        return u().getIntValue(NBNetConfigureItem.UPLOAD_PROTOCOL_EXECUTE_COUNT);
    }

    public static final int p() {
        return u().getIntValue(NBNetConfigureItem.UPLOAD_RESUME_COUNT);
    }

    public static final int q() {
        return u().getIntValue(NBNetConfigureItem.UPLOAD_SERVER_PORT);
    }

    public static final int r() {
        return u().getIntValue(NBNetConfigureItem.UPLOAD_DOWNLOAD_PORT);
    }

    public static final boolean s() {
        return MiscUtils.grayscaleUtdid(NBNetEnvUtils.e(), u().getStringValue((ConfigureItem) NBNetConfigureItem.MMUP_BACKEND_SWITCH));
    }

    public static final String t() {
        return u().getStringValue((ConfigureItem) NBNetConfigureItem.MMUP_BACKEND_VALUE);
    }

    public static final String a(Context context, String metaDatakey, String defaultValue) {
        try {
            String resultStr = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString(metaDatakey);
            NBNetLogCat.a((String) "NBNetConfigUtil", "[getStringFromMetaData] metaDatakey=" + metaDatakey + ", resultStr=" + resultStr);
            if (!TextUtils.isEmpty(resultStr)) {
                return resultStr;
            }
        } catch (Throwable e) {
            NBNetLogCat.e("NBNetConfigUtil", "[getStringFromMetaData] metaDatakey=" + metaDatakey + ", Exception: " + e.toString());
        }
        return defaultValue;
    }

    private static TransportConfigureManager u() {
        return TransportConfigureManager.getInstance();
    }
}
