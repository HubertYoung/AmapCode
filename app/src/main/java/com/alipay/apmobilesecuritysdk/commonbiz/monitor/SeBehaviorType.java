package com.alipay.apmobilesecuritysdk.commonbiz.monitor;

import com.alipay.mobile.beehive.rpc.action.ActionConstant;

public enum SeBehaviorType {
    UC_SC_ERRORS("SEC_SDK_ERRORS", "err"),
    UC_SC_WARNS("SEC_SDK_WARNS", ActionConstant.EXCEPTION_VIEW_TYPE_WARN),
    UC_UTK_24_ZEROS("SEC_UMID_REPORT", "utk_24_zeros"),
    UC_UTK_UTDID("SEC_UMID_REPORT", "utk_utdid"),
    UC_UTK_NORMAL("SEC_UMID_REPORT", "utk_normal"),
    UC_UTK_ILLEGAL("SEC_UMID_REPORT", "utk_illegal"),
    UC_APDID_LOCAL("SEC_GET_TOKEN", "getToken"),
    UC_EDGE_INIT_OK("EDGE_INIT_REPORT", "init_normal"),
    UC_EDGE_INIT_FAIL("EDGE_INIT_REPORT", "init_abnormal"),
    UC_EDGE_ASK_RISK("EDGE_GET_RISK_RESULT", "get_risk_result"),
    UC_EDGE_POST_UA("EDGE_POST_UA", "post_ua"),
    UC_EDGE_SYNC("EDGE_SYNC_REPORT", "sync"),
    UC_EDGE_INJECT_LIST("EDGE_INJECT_LIST", "get_inject_list"),
    UC_EDGE_LOC_APPS("EDGE_LOC_HOOK_LIST", "hook_loc"),
    UC_RDS_APK_VERIFY("APK_VERIFY_RESULT", "apk_verify");
    
    private String seedID;
    private String useCaseID;

    private SeBehaviorType(String str, String str2) {
        this.useCaseID = str;
        this.seedID = str2;
    }

    public final String getUseCaseID() {
        return this.useCaseID;
    }

    public final String getSeedID() {
        return this.seedID;
    }
}
