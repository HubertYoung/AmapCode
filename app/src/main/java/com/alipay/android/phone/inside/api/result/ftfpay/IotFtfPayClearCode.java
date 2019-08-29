package com.alipay.android.phone.inside.api.result.ftfpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotFtfPayClearCode extends ResultCode {
    public static final IotFtfPayClearCode FAILED = new IotFtfPayClearCode("iot_ftf_pay_clear_8000", "失败，请重试");
    public static final IotFtfPayClearCode SUCCESS = new IotFtfPayClearCode("iot_ftf_pay_clear_9000", "成功");
    private static final List<IotFtfPayClearCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
    }

    protected IotFtfPayClearCode(String str, String str2) {
        super(str, str2);
    }

    public static IotFtfPayClearCode parse(String str) {
        for (IotFtfPayClearCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
