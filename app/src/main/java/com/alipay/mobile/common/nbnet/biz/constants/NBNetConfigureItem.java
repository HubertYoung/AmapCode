package com.alipay.mobile.common.nbnet.biz.constants;

import com.alibaba.baichuan.android.trade.adapter.alipay.AlibcAlipay;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;

public enum NBNetConfigureItem implements ConfigureItem {
    G2_SO_TIMEOUT(a("g2sto"), "12000"),
    G3_SO_TIMEOUT(a("g3sto"), "12000"),
    WIFI_4G_SO_TIMEOUT(a("w3sto"), AlibcAlipay.PAY_SUCCESS_CODE),
    DOWNLOAD_RETRY_LOGIC_ERROR(a("drle"), "2"),
    DOWNLOAD_VERIFY_ERROR(a("dve"), "3"),
    DOWNLOAD_RETRY_NETWORK_ERROR(a("drne"), "10"),
    DOWNLOAD_SINGLE_FLOW_LIMIT(a("dsfl"), "2097152"),
    DOWNLOAD_RETRY_INTERVAL(a("dri"), "1"),
    DOWNLOAD_FUSE_MEASURE_INTERVAL(a("dfmi"), "1800"),
    DOWNLOAD_FUSE_MEASURE_COUNT(a("dfmc"), "1000"),
    UPLOAD_MAX_EXECUTION_COUNT(a("umec"), "32"),
    UPLOAD_NETWORK_EXECUTION_COUNT(a("unec"), "32"),
    UPLOAD_FILE_EXECUTION_COUNT(a("ufec"), "5"),
    UPLOAD_UNKNOW_EXECUTION_COUNT(a("uuec"), "3"),
    UPLOAD_SERVER_EXECUTE_COUNT(a("usec"), "10"),
    UPLOAD_PROTOCOL_EXECUTE_COUNT(a("upec"), "10"),
    UPLOAD_RESUME_COUNT(a("urc"), "50"),
    UPLOAD_SERVER_PORT(a("usp"), "-1"),
    UPLOAD_DOWNLOAD_PORT(a("udp"), "-1"),
    COMPOSITE_CONNECTION_SWITCH(a("ccs"), "T"),
    COMP_CONNECT_TIME_OUT(a("cpcto"), "20000"),
    MMUP_BACKEND_SWITCH(a("xmbes"), "-1"),
    MMUP_BACKEND_VALUE(a("xmbev"), "django"),
    DL_TOTAL_TIME_OUT(a("dltto"), "300000"),
    UP_TOTAL_TIME_OUT(a("uptto"), "-1"),
    MOCK_UPLOAD_SERVER_LIMITED_SWITCH(a("musls"), "F"),
    MOCK_DOWNLOAD_SERVER_LIMITED_SWITCH(a("mdsls"), "F"),
    LAST_ITEM("$k", "$v");
    
    public static final String RESERVED_PREFIX = "nbn.";
    private String configKey;
    private String configValue;

    private NBNetConfigureItem(String configKey2, String configValue2) {
        this.configValue = configValue2;
        this.configKey = configKey2;
    }

    public final String getConfigName() {
        return this.configKey;
    }

    public final String getStringValue() {
        return this.configValue;
    }

    public final long getLongValue() {
        try {
            return Long.parseLong(this.configValue);
        } catch (Exception e) {
            NBNetLogCat.a((String) "NBNetConfigureItem", (Throwable) e);
            return -1;
        }
    }

    public final int getIntValue() {
        try {
            return Integer.parseInt(this.configValue);
        } catch (Exception e) {
            NBNetLogCat.a((String) "NBNetConfigureItem", (Throwable) e);
            return -1;
        }
    }

    public final void setValue(String value) {
        this.configValue = value;
    }

    public final double getDoubleValue() {
        try {
            return Double.parseDouble(this.configValue);
        } catch (Throwable ex) {
            NBNetLogCat.b((String) "NBNetConfigureItem", ex);
            return -1.0d;
        }
    }

    private static final String a(String configName) {
        return new StringBuilder(RESERVED_PREFIX).append(configName).toString();
    }
}
