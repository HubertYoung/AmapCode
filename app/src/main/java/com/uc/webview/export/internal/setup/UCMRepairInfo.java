package com.uc.webview.export.internal.setup;

import com.uc.webview.export.annotations.Api;

@Api
/* compiled from: ProGuard */
public class UCMRepairInfo {
    public static final int REPAIR_RESULT_FAIL = 2;
    public static final int REPAIR_RESULT_INVALID = -1;
    public static final int REPAIR_RESULT_NONE = 1;
    public static final int REPAIR_RESULT_SUCC = 0;
    public static final int VERIFY_POLICY_MD5 = 1;
    public static final int VERIFY_POLICY_SIZE = 0;
    public String repairDir = "";
    public int repairResult = -1;
    public int verifyPolicy;

    public UCMRepairInfo(int i) {
        this.verifyPolicy = i;
    }
}
