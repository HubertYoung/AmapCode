package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class ScanCodeV2 extends ResultCode {
    public static final ScanCodeV2 INNER_EX = new ScanCodeV2("scan_v2_9001", "内部异常");
    public static final ScanCodeV2 PARAMS_ILLEGAL = new ScanCodeV2("scan_v2_9002", "参数异常");
    public static final ScanCodeV2 SUCCESS = new ScanCodeV2("scan_v2_9000", "码值执行成功");
    public static final ScanCodeV2 TIMEOUT_EX = new ScanCodeV2("scan_v2_9004", "调用超时");
    public static final ScanCodeV2 TIME_EX = new ScanCodeV2("scan_v2_9003", "系统时间设置异常");
    private static final List<ScanCodeV2> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(INNER_EX);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(TIME_EX);
        mCodeList.add(TIMEOUT_EX);
    }

    protected ScanCodeV2(String str, String str2) {
        super(str, str2);
    }

    public static ScanCodeV2 parse(String str) {
        for (ScanCodeV2 next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
