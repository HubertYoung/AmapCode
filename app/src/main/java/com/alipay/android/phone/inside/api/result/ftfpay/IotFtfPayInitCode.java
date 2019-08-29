package com.alipay.android.phone.inside.api.result.ftfpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotFtfPayInitCode extends ResultCode {
    public static final IotFtfPayInitCode FAILED = new IotFtfPayInitCode("iot_ftf_pay_init_8000", "失败，请重试");
    public static final IotFtfPayInitCode PARAMS_ILLEGAL = new IotFtfPayInitCode("iot_ftf_pay_init_8001", "参数非法");
    public static final IotFtfPayInitCode SUCCESS = new IotFtfPayInitCode("iot_ftf_pay_init_9000", "成功");
    private static final List<IotFtfPayInitCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
    }

    protected IotFtfPayInitCode(String str, String str2) {
        super(str, str2);
    }

    public static IotFtfPayInitCode parse(String str) {
        for (IotFtfPayInitCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
