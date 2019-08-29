package com.alipay.android.phone.inside.api.result.ftfpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotFtfPayBindCode extends ResultCode {
    public static final IotFtfPayBindCode FAILED = new IotFtfPayBindCode("iot_ftf_pay_bind_8000", "失败，请重试");
    public static final IotFtfPayBindCode PARAMS_ILLEGAL = new IotFtfPayBindCode("iot_ftf_pay_bind_8001", "参数非法");
    public static final IotFtfPayBindCode SUCCESS = new IotFtfPayBindCode("iot_ftf_pay_bind_9000", "成功");
    private static final List<IotFtfPayBindCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
    }

    protected IotFtfPayBindCode(String str, String str2) {
        super(str, str2);
    }

    public static IotFtfPayBindCode parse(String str) {
        for (IotFtfPayBindCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
