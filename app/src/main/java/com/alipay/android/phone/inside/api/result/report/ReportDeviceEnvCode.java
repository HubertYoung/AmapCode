package com.alipay.android.phone.inside.api.result.report;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.alipay.AlibcAlipay;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class ReportDeviceEnvCode extends ResultCode {
    public static final ReportDeviceEnvCode SUCCESS = new ReportDeviceEnvCode(AlibcAlipay.PAY_SUCCESS_CODE, "成功");
    private static final List<ReportDeviceEnvCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
    }

    protected ReportDeviceEnvCode(String str, String str2) {
        super(str, str2);
    }

    public static ReportDeviceEnvCode parse(String str) {
        for (ReportDeviceEnvCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
